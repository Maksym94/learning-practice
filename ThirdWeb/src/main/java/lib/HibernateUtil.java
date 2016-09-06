package lib;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Create the session that was set in Hibernate *.xml files
 *
 */
public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();
	 private static SessionFactory buildSessionFactory() {
	  try {
	   // Create the SessionFactory from hibernate.cfg.xml
		  Configuration configuration = new Configuration();
		  configuration.configure("hibernate.cfg.xml");
	   return configuration.buildSessionFactory(new ServiceRegistryBuilder().applySettings(
	            configuration.getProperties()). buildServiceRegistry());
	  }
	  catch (Throwable ex) {
	   // Make sure you log the exception, as it might be swallowed
	   System.err.println("Initial SessionFactory creation failed." + ex);
	   throw new ExceptionInInitializerError(ex);
	  }
	 }
	 public static SessionFactory getSessionFactory() {
	  return sessionFactory;
	 }
	 
	 public static void closeSession(){
		 sessionFactory.close();
	 }

}