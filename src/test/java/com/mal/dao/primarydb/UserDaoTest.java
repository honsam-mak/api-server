package com.mal.dao.primarydb;

import java.util.List;

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
	public void findUser(Long userId, String name) {

		User user = userDao.find(userId);

		Assert.assertNotNull(user);
		Assert.assertEquals(name, user.getUserName());
	}
	
	@Test
    @OpDataSet(dataSetClazz = GetUserData.class, beforeOperation = Insert, afterOperation = Delete)
	public void listUser() {
		
		List<User> users = userDao.listUser();
		
		Assert.assertEquals(3, users.size());
	}

	@DataProvider (name = "UserData")
	private Object[][] getUserId() {
		return new Object[][] {
            { new Long(101), "test"},
            { new Long(3001), "joe"},
            { new Long(3002), "jack"}
		};
	}

    @Named @Lazy
    private static class GetUserData extends ResourceYamlDataSet {
        GetUserData() {
            super("classpath:com/mal/dao/UserDaoTest-find.yaml");
        }
    }
}
