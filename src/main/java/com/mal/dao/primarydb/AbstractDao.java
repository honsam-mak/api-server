package com.mal.dao.primarydb;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractDao<T, PK_T> extends AbstractTypedDao<T, PK_T>{

	@PersistenceContext(unitName = "entityManagerFactory")
	EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}
}
