package com.mal.dao.empdb;

import javax.annotation.PreDestroy;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Qualifier;

public abstract class AbstractDao<T, PK_T> extends AbstractTypedDao<T, PK_T>{

	@Inject
	@Qualifier("entityManagerFactoryEmp") 
	EntityManagerFactory factory;

	EntityManager entityManager;

	@PostConstruct
	public void init() {
    		entityManager = factory.createEntityManager();
	}

	@PreDestroy
	public void cleanup() {
    		entityManager.close();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
}
