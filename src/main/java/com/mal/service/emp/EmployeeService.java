package com.mal.service.emp;

import com.mal.dao.empdb.LoginDao;
import com.mal.dao.primarydb.UserDao;
import com.mal.orm.Login;
import com.mal.orm.User;

import java.sql.Timestamp;
import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	@Inject
	private LoginDao loginDao;

	public Login getEmployee(int id) {

		Login user = loginDao.find(id);

        return user;
	}

	public Login post(String name, String password) {
		Login user = new Login();
		user.setUserName(name);
		user.setPassword(password);
    	Date date = new Date();
    	user.setCreatedTime(new Timestamp(date.getTime()));

		loginDao.saveNew(user);

        return user;
	}
}
