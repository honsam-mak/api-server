package com.mal.test;

import java.lang.reflect.Method;

import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.no_ip.mikelue.jpa.test.dbunit.DbUnitBuilder;
import org.no_ip.mikelue.jpa.test.dbunit.annotation.ReflectAnnotationDbUnitContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Test base for database.
 *
 * This base class would upgrade schema and update default data for testing before the test suite.
 */
@ContextConfiguration(
        classes = {DatabaseTestConfig.class}
)
public abstract class AbstractDatabaseTestBase extends AbstractBasicSpringBase {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    protected AbstractDatabaseTestBase() {
        super();
    }

    @Override @BeforeMethod(dependsOnMethods = "processDataSetBeforeMethod")
    protected void springTestContextBeforeTestMethod(Method method) throws Exception {
        super.springTestContextBeforeTestMethod(method);
    }

    @BeforeMethod(firstTimeOnly = true)
    protected void processDataSetBeforeMethod(Method method) throws Exception {
        try {
            applicationContext.getBean(ReflectAnnotationDbUnitContext.class).beforeOperation(method);
        } catch (Exception e) {
            logger.error("@BeforeMethod exception: {}", e.getClass());
            throw e;
        }
    }

    @AfterMethod(lastTimeOnly=true, dependsOnMethods = "springTestContextAfterTestMethod")
    protected void processDataSetAfterMethod(Method method) throws Exception {
        try {
            applicationContext.getBean(ReflectAnnotationDbUnitContext.class).afterOperation(method);
        } catch (Exception e) {
            logger.error("@AfterMethod exception: {}", e.getClass());
            throw e;
        }
    }

    /*
     *  Execute the operation on YamlDataSet.
     */
    protected void operateDataSet(IDataSet dataset, DatabaseOperation dbOperation) {
        DbUnitBuilder builder = applicationContext.getBean(DbUnitBuilder.class);

        builder.runDatabaseOperation(dataset, dbOperation);
    }
}
