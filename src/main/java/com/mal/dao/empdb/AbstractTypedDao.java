package com.mal.dao.empdb;

import com.googlecode.gentyref.GenericTypeReflector;
import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractTypedDao<T, PK_T> {

	private final Class<T> entityType;

	protected AbstractTypedDao() {
		entityType = (Class<T>)GenericTypeReflector.erase(
			GenericTypeReflector.getTypeParameter(this.getClass(), AbstractTypedDao.class.getTypeParameters()[0]));
	}

	abstract public EntityManager getEntityManager();

	public T find(PK_T primaryKey) {
		return getEntityManager().find(entityType, primaryKey);
	}

	@Transactional("transactionManagerEmp")
	public void saveNew(T newEntity) {
		getEntityManager().persist(newEntity);
	}

	@Transactional("transactionManagerEmp")
	public void saveExisted(T existedEntity) {
		getEntityManager().merge(existedEntity);
	}

	@Transactional("transactionManagerEmp")
	public void remove(PK_T primaryKey) {
		T entity = find(primaryKey);

		if (entity == null)
			return;

		getEntityManager().remove(entity);
	}
}
