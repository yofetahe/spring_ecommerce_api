package com.yhabtu.ecommerce.configuration;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.yhabtu.ecommerce.model.BillingShippingAddress;
import com.yhabtu.ecommerce.model.Category;
import com.yhabtu.ecommerce.model.Category_Type;
import com.yhabtu.ecommerce.model.Color;
import com.yhabtu.ecommerce.model.CreditCardInformation;
import com.yhabtu.ecommerce.model.Item;
import com.yhabtu.ecommerce.model.Item_Size_Color;
import com.yhabtu.ecommerce.model.OrderDetails;
import com.yhabtu.ecommerce.model.Orders;
import com.yhabtu.ecommerce.model.Size;
import com.yhabtu.ecommerce.model.Type;
import com.yhabtu.ecommerce.model.User;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	
    public static SessionFactory getSessionFactory() {
    	
        if (sessionFactory == null) {
        	
            try {
                Configuration configuration = new Configuration();
                
                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                
                settings.put(Environment.URL, "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net:3306/ad_5c0e5a13e1d4b50");
                settings.put(Environment.USER, "b527096be777a6");
                settings.put(Environment.PASS, "1daa2b56");
                
//                settings.put(Environment.URL, "jdbc:mysql://localhost:3309/react_ecommerce?useSSL=false");
//                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");                
//                settings.put(Environment.USER, "root");
//                settings.put(Environment.PASS, "P@55w0rd@yamget");
                
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "update");
                configuration.setProperties(settings);
                
                configuration.addAnnotatedClass(Category_Type.class);
                configuration.addAnnotatedClass(Category.class);
                configuration.addAnnotatedClass(Color.class);
                configuration.addAnnotatedClass(Item_Size_Color.class);
                configuration.addAnnotatedClass(Item.class);
                configuration.addAnnotatedClass(Orders.class);
                configuration.addAnnotatedClass(Size.class);
                configuration.addAnnotatedClass(Type.class);
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Orders.class);
                configuration.addAnnotatedClass(OrderDetails.class);
                configuration.addAnnotatedClass(BillingShippingAddress.class);
                configuration.addAnnotatedClass(CreditCardInformation.class);
                
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
                
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
	
//	private static StandardServiceRegistry standardServiceRegistry;
//	private static SessionFactory sessionFactory;
// 
//	static {
//		// Creating StandardServiceRegistryBuilder 
//		StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
//		
//		// Hibernate settings which is equivalent to hibernate.cfg.xml's properties
//		Map<String, String> dbSettings = new HashMap<>();
//		dbSettings.put(Environment.URL, "jdbc:mysql://localhost:3309/react_ecommerce?useSSL=false");
//		dbSettings.put(Environment.USER, "root");
//		dbSettings.put(Environment.PASS, "P@55w0rd@yamget");
//		dbSettings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
//		dbSettings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
// 
//		// Apply database settings
//		registryBuilder.applySettings(dbSettings);
//		// Creating registry
//		standardServiceRegistry = registryBuilder.build();
//		// Creating MetadataSources
//		MetadataSources sources = new MetadataSources(standardServiceRegistry);
//		// Creating Metadata
//		Metadata metadata = sources.getMetadataBuilder().build();
//		// Creating SessionFactory
//		sessionFactory = metadata.getSessionFactoryBuilder().build();
//	}
//	//Utility method to return SessionFactory
//	public static SessionFactory getSessionFactory() {
//		return sessionFactory;
//	}
}
