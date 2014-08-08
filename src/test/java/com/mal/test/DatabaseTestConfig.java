package com.mal.test;

import com.jolbox.bonecp.BoneCPDataSource;
import liquibase.resource.FileSystemResourceAccessor;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.no_ip.mikelue.jpa.test.dbunit.DbUnitAction;
import org.no_ip.mikelue.jpa.test.dbunit.DbUnitBuilder;
import org.no_ip.mikelue.jpa.test.dbunit.annotation.ReflectAnnotationDbUnitContext;
import org.no_ip.mikelue.jpa.test.liquibase.LiquibaseAction;
import org.no_ip.mikelue.jpa.test.liquibase.LiquibaseBuilder;
import org.no_ip.mikelue.jpa.test.liquibase.UpdateSchemaExecutor;
import org.no_ip.mikelue.jpa.test.springframework.AnnotationDataSetBuilder;
import org.no_ip.mikelue.jpa.test.testng.Action;
import org.no_ip.mikelue.jpa.test.testng.ChainedAction;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.*;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import static org.dbunit.database.DatabaseConfig.PROPERTY_DATATYPE_FACTORY;
import static org.dbunit.database.DatabaseConfig.PROPERTY_ESCAPE_PATTERN;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import java.sql.SQLException;

import static com.mal.config.DatabaseConfig.buildBasicDataSourceOfConeCP;
import static org.no_ip.mikelue.jpa.test.springframework.DataSetBuilder.buildWithYaml;

@Configuration
@ComponentScan
@Import(com.mal.config.DatabaseConfig.class)
public class DatabaseTestConfig {
   /*
    * The default dataset file for testing data
     */
    public final static String DEFAULT_TEST_DATASET = "classpath:DefaultTestDataSet.yaml";
    /*
     * The default dataset file for testing data
     */
    public final static String DEFAULT_REMOVE_DATASET = "classpath:DefaultRemoveDataSet.yaml";

    public DatabaseTestConfig() {}

    @Bean(name="dataSource", destroyMethod="close")
    public DataSource buildDataSource(Environment environment) {

        BoneCPDataSource dataSource = buildBasicDataSourceOfConeCP(environment);

        dataSource.setPartitionCount(1);
        dataSource.setMinConnectionsPerPartition(2);
        dataSource.setMaxConnectionsPerPartition(10);

        return dataSource;
    }

    /*
     * This action is used to upgrade schema in testing
     */
    @Bean(name="upgradeSchemaAction")
    public Action buildLiquibaseAction(DataSource dataSource) {
        return new LiquibaseAction(
                LiquibaseBuilder.build(
                       "src/main/liquibase/main.xml",
                       new FileSystemResourceAccessor(),
                        dataSource
                ),
                new UpdateSchemaExecutor()
        );
    }

    /*
     * Builds the factory of data type(DBUnit) to support enum type in MySQL
     * The name prefix with "enum_" is the enum in MySQL
     */
    @Bean
    public MySqlDataTypeFactory buildMySQLDataTypeFactory() {
        return new MySqlDataTypeFactory();
    }

    /*
     * This action is used to refresh data in testing
     */
    @Bean(name="refreshTestDataAction") @Scope(SCOPE_PROTOTYPE)
    public Action buildDbUnitAction(
           DbUnitBuilder dbUnitBuilder,
           @Value(DEFAULT_TEST_DATASET) Resource defaultTestDataSetSource,
           @Value(DEFAULT_REMOVE_DATASET) Resource defaultRemoveDataSetSource
    ) {
        return new ChainedAction(
                /*
                 * Default creation of test data
                 */
                new DbUnitAction(
                        dbUnitBuilder, DatabaseOperation.REFRESH,
                        buildWithYaml(defaultTestDataSetSource)
                ),
                /*
                 * Default removal of test data(built by trigger)
                 */
                new DbUnitAction(
                        dbUnitBuilder, DatabaseOperation.DELETE,
                        buildWithYaml(defaultRemoveDataSetSource)
                )
        );
    }

    @Bean
    public DbUnitBuilder dbUnitBuilder(
            DataSource dataSource, MySqlDataTypeFactory dataTypeFactory
    ) {
        DbUnitBuilder dbUnitBuilder = DbUnitBuilder.build(dataSource, dataTypeFactory);
        dbUnitBuilder.setRunAsTransaction(true);

        return dbUnitBuilder;
    }

    @Bean
    public ReflectAnnotationDbUnitContext buildReflectAnnotationDbUnitContext(
            DbUnitBuilder dbUnitBuilder, BeanFactory beanFactory
    ) {
        AnnotationDataSetBuilder dataSetBuilder = new AnnotationDataSetBuilder();
        dataSetBuilder.setBeanFactory(beanFactory);

        return new ReflectAnnotationDbUnitContext(
                dbUnitBuilder, dataSetBuilder
        );
    }

    @Bean @Lazy
    public GlobalRollbackDataSet buildDatabaseDataSet(
            DataSource dataSource, MySqlDataTypeFactory dataTypeFactory,
            @Value(DEFAULT_TEST_DATASET) Resource defaultTestDataSetSource
    ) throws SQLException, DataSetException {

        IDatabaseConnection dbConn = null;

        try {
            dbConn = new DatabaseDataSourceConnection(dataSource);
            dbConn.getConfig().setProperty(PROPERTY_DATATYPE_FACTORY, dataTypeFactory);
            dbConn.getConfig().setProperty(PROPERTY_ESCAPE_PATTERN, "`?`");

            return new GlobalRollbackDataSet(
                    buildWithYaml(defaultTestDataSetSource)
            );
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
    }

    @Bean
    public ApplicationListener<ContextRefreshedEvent> buildDatabaseTestDataProvider() {
        return new DatabaseTestDataProvider();
    }
}

/*
 * Prepares database after context has been initialized.
 *
 * Upgrade the schema of database
 * Update the data for testing(clean and re-insert)
 * Build the dataset which is used to rollback the status of database built by two previous actions
 */
class DatabaseTestDataProvider implements ApplicationListener<ContextRefreshedEvent>, Ordered {

    @Inject @Named("upgradeSchemaAction")
    private Action upgradeSchemaAction;
    @Inject @Named("refreshTestDataAction")
    private Action refreshTestDataAction;
    @Inject
    private BeanFactory beanFactory;

    public DatabaseTestDataProvider() {}

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        /*
         * Upgrade the schema of database
         */
        upgradeSchemaAction.executeAction();

        /*
         * Update the data for testing(clean and re-insert)
         */
        refreshTestDataAction.executeAction();

        beanFactory.getBean(GlobalRollbackDataSet.class);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
