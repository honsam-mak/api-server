package com.mal.dao.primarydb;

import java.util.List;

import com.mal.orm.User;

import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends AbstractDao<User, Long>{

	public List<User> listUser() {
		
		String query = 
				" SELECT user FROM User user";
		
		return getEntityManager().createQuery(query, User.class)
				.getResultList();
	}
}
