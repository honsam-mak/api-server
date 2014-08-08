package com.mal.test;

import com.mal.config.DatabaseConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/*
 * Test base for DAO
 */
@ContextConfiguration(
        classes={DatabaseConfig.class}
)
@TestExecutionListeners(listeners = {TransactionalTestExecutionListener.class})
@Test(suiteName="DaoSuite")
public abstract class AbstractDaoTestBase extends AbstractDatabaseTestBase{

    protected AbstractDaoTestBase() {
        super();
    }

    @PersistenceContext
    private EntityManager entityManager;

    /*
     * Utility method for getting entity manager
     */
    final protected EntityManager getEntityManager() {
        return entityManager;
    }

    final protected void flushAndClearEntityManager() {
        getEntityManager().flush();
        getEntityManager().close();
    }
}
