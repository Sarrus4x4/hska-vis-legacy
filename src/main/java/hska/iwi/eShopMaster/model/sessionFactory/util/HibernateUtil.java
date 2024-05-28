package hska.iwi.eShopMaster.model.sessionFactory.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

    private static SessionFactory sessionFactory = null;

//	static {
//		try {
//            // Create the SessionFactory from hibernate.cfg.xml
//			Configuration configuration = new Configuration().configure();
//			StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
//					applySettings(configuration.getProperties());
//			sessionFactory = configuration.buildSessionFactory(builder.build());
//			System.out.println("Initial SessionFactory creation");
//		} catch (Throwable ex) {
//			System.out.println("Initial SessionFactory creation failed." + ex);
//			throw new ExceptionInInitializerError(ex);
//		}
//	}


    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            // StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            //         .configure() // configures settings from hibernate.cfg.xml
            //         .build();
            StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

            // Load configuration from hibernate.cfg.xml
            registryBuilder.configure();

            // Override properties with environment variables if they are set
            if (System.getenv("MONOLITH_DB_USER") != null) {
                registryBuilder.applySetting("hibernate.connection.username", System.getenv("MONOLITH_DB_USER"));
            }
            if (System.getenv("MONOLITH_DB_PWD") != null) {
                registryBuilder.applySetting("hibernate.connection.password", System.getenv("MONOLITH_DB_PWD"));
            }
            if (System.getenv("MYSQL_HOST") != null && System.getenv("MONOLITH_DB_NAME") != null) {
                String dbHost = System.getenv("MYSQL_HOST");
                String dbName = System.getenv("MONOLITH_DB_NAME");
                String connectionURL = "jdbc:mysql://" + dbHost + ":3306/" + dbName;
                registryBuilder.applySetting("hibernate.connection.url", connectionURL);
            }

            StandardServiceRegistry registry = registryBuilder.build();
            try {
                sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
                // so destroy it manually.
                StandardServiceRegistryBuilder.destroy(registry);
                throw new RuntimeException(e);
            }
        }
        return sessionFactory;
    }
}
