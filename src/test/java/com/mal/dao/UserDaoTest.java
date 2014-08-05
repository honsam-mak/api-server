package com.mal.dao;

import javax.inject.Inject;

import org.junit.Assert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.mal.config.DatabaseConfig;
import com.mal.orm.User;

@ContextConfiguration(
    classes={DatabaseConfig.class},
    initializers=com.mal.config.AppContextIntializer.class
)
public class UserDaoTest extends AbstractTestNGSpringContextTests {

	@Inject
	private UserDao userDao;

	@Test (dataProvider = "UserData")
	public void findUser(Long userId) {

		User user = userDao.find(userId);

		Assert.assertNotNull(user);
	}

	@DataProvider (name = "UserData")
	private Object[][] getUserId() {
		return new Object[][] {
			{ new Long(2)},
			{ new Long(3)}
		};
	}
}
