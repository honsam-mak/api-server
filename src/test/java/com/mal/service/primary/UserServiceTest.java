package com.mal.service.primary;

import javax.inject.Inject;
import javax.inject.Named;

import junit.framework.Assert;

import org.no_ip.mikelue.jpa.test.dbunit.annotation.OpDataSet;
import org.no_ip.mikelue.jpa.test.springframework.ResourceYamlDataSet;
import org.springframework.context.annotation.Lazy;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.mal.orm.User;
import com.mal.test.AbstractServiceTestBase;

import static org.no_ip.mikelue.jpa.test.dbunit.annotation.DataSetOperation.Insert;
import static org.no_ip.mikelue.jpa.test.dbunit.annotation.DataSetOperation.Delete;

public class UserServiceTest extends AbstractServiceTestBase {

	@Inject
	private UserService userService;
	
	@Test (dataProvider = "UserData")
	@OpDataSet(dataSetClazz = GetUserData.class, beforeOperation = Insert, afterOperation = Delete)
	public void getUser(Long userId, String name) {
		User user = userService.getUser(userId);
		
		Assert.assertEquals(name, user.getUserName());
	}

	@DataProvider (name = "UserData")
	private Object[][] getUserId() {
		return new Object[][] {
            { new Long(3001), "joe"},
            { new Long(3002), "jack"}
		};
	}
	
    @Named @Lazy
    private static class GetUserData extends ResourceYamlDataSet {
        GetUserData() {
            super("classpath:com/mal/service/UserServiceTest-find.yaml");
        }
    }
}
