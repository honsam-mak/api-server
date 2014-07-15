package com.mal.dao;

import javax.annotation.PreDestroy;
import javax.annotation.PostConstruct;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public abstract class AbstractDao<T, PK_T> extends AbstractTypedDao<T, PK_T>{

	@Inject
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
