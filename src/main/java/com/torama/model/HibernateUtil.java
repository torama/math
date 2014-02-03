package com.torama.model;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();
	
	@SuppressWarnings("deprecation")
	private static SessionFactory buildSessionFactory() {
		try {
			return new Configuration()
				.configure().buildSessionFactory();
		} catch (Throwable exception) {
			System.err.println("SessionFactory could not be created. " + exception);
			throw new ExceptionInInitializerError(exception);
		}
	}

	public static SessionFactory getSessionfactory() {
		return sessionFactory;
	}
}
