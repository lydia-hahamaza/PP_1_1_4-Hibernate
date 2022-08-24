package jm.task.core.jdbc.util;



import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/db_user";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        Connection connection = null;

        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("Соединение с БД установлено");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = null;
        try {
            Configuration configuration = new Configuration();

            Properties properties = new Properties();
            properties.put(Environment.URL,"jdbc:mysql://localhost:3306/db_user");
            properties.put(Environment.USER,"root");
            properties.put(Environment.PASS,"root");

            properties.put(Environment.SHOW_SQL,true);
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS,"thread");

            configuration.setProperties(properties);

            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }
}
