package com.macchiarini.lorenzo.litto_backend.ogm.daoOGM;

import org.neo4j.ogm.config.ClasspathConfigurationSource;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

/**
 * @author lorem
 *
 */

public class SessionFactoryNeo4J {

	private static ClasspathConfigurationSource configurationSource = new ClasspathConfigurationSource("ogm.properties");
	private static Configuration configuration = new Configuration.Builder(configurationSource).build();
	private static SessionFactory sessionFactory = new SessionFactory(configuration, "com.macchiarini.lorenzo.litto_backend.ogm.modelOGM");
	private static SessionFactoryNeo4J factory = new SessionFactoryNeo4J();

	static SessionFactoryNeo4J getInstance() {
		return factory;
	}

	private SessionFactoryNeo4J() {
	}

	Session getSession() {
		return sessionFactory.openSession();
	}

}
