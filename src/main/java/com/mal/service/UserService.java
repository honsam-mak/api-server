package com.mal.service;

import com.mal.dao.UserDao;
import com.mal.orm.User;

import java.sql.Timestamp;
import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Inject
	private UserDao userDao;

	public String getUser(long id) {

		User user = userDao.find(id);

		return "User found. " + user.getCreatedTime();
	}

	public String post() {
		User user = new User();
		user.setUserName("test");
    	Date date = new Date();
    	user.setCreatedTime(new Timestamp(date.getTime()));

		userDao.saveNew(user);

		return "Just get it done! " + user.getCreatedTime();
	}

	public String update(long id) {
		User user = userDao.find(id);
		user.setUserName("test1");

		userDao.saveExisted(user);

		return "Just updated! " + user.getUserName();
	}

	public String delete(long id) {
		userDao.remove(id);

		return "Just removed " + id;
	}
}
