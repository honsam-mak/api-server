package com.mal.dao;

import javax.inject.Inject;
import javax.inject.Named;

import com.mal.test.AbstractDaoTestBase;

import org.junit.Assert;
import org.no_ip.mikelue.jpa.test.dbunit.annotation.DataSetOperation;
import org.no_ip.mikelue.jpa.test.dbunit.annotation.OpDataSet;
import org.no_ip.mikelue.jpa.test.springframework.ResourceYamlDataSet;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.mal.config.DatabaseConfig;
import com.mal.dao.primarydb.UserDao;
import com.mal.orm.User;
import com.mal.test.AbstractBasicSpringBase;

import static org.no_ip.mikelue.jpa.test.dbunit.annotation.DataSetOperation.Insert;
import static org.no_ip.mikelue.jpa.test.dbunit.annotation.DataSetOperation.Delete;

public class UserDaoTest extends AbstractDaoTestBase {

	@Inject
	private UserDao userDao;

	@Test (dataProvider = "UserData")
    @OpDataSet(dataSetClazz = GetUserData.class, beforeOperation = Insert, afterOperation = Delete)
	public void findUser(Long userId) {

		User user = userDao.find(userId);

		Assert.assertNotNull(user);
	}

	@DataProvider (name = "UserData")
	private Object[][] getUserId() {
		return new Object[][] {
            { new Long(3001)}
		};
	}

    @Named @Lazy
    private static class GetUserData extends ResourceYamlDataSet {
        GetUserData() {
            super("classpath:com/mal/dao/UserDaoTest-find.yaml");
        }
    }
}
