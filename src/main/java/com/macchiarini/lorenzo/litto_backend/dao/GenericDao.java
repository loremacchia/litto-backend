package com.macchiarini.lorenzo.litto_backend.dao;

import jakarta.inject.Inject;

public class GenericDao {
	@Inject
	SessionFactoryNeo4J sessionFactory;
	
	public <T> T getPreview(Class<T> objClass, String id) {
		return sessionFactory.getSession().load(objClass, id, 0);
	}
	
	public <T> T getOverview(Class<T> objClass, String id) {
		return sessionFactory.getSession().load(objClass, id, 1);
	}
	
	public <T> T get(Class<T> objClass, String id) {
		return sessionFactory.getSession().load(objClass, id);
	}
	
	public <T> T getCustom(Class<T> objClass, String id, int depth) {
		return sessionFactory.getSession().load(objClass, id, depth);
	}

	public <T> void savePreview(T obj) {
		sessionFactory.getSession().save(obj, 0);
	}
	
	public <T> void saveOverview(T obj) {
		sessionFactory.getSession().save(obj, 1);
	}
	
	public <T> void save(T obj) {
		sessionFactory.getSession().save(obj);
	}
}