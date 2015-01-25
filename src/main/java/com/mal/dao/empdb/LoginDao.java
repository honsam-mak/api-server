package com.mal.dao.empdb;

import org.springframework.stereotype.Repository;

import com.mal.orm.Login;

@Repository
public class LoginDao extends AbstractDao<Login, Integer> {

	public Login findEmployee(String name, String password) {
		
		String query = 
				" SELECT login FROM Login login" +
				" WHERE login.name = :name AND login.password = :password";
		
		return getEntityManager().createQuery(query, Login.class)
				.setParameter("name", name)
				.setParameter("password", password)
				.getSingleResult();
	}
}
