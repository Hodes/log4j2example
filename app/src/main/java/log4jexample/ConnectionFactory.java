package log4jexample;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class ConnectionFactory {

    private static interface Singleton {

        final ConnectionFactory INSTANCE = new ConnectionFactory();
    }

    private GenericObjectPool<PoolableConnection> connectionPool = null;
    private DataSource dataSource = null;

    private ConnectionFactory() {
        Properties properties = new Properties();
        properties.setProperty("user", "user");
        properties.setProperty("password", "1234"); // or get properties from some configuration file
        
        DriverManagerConnectionFactory cf = new DriverManagerConnectionFactory(
                "jdbc:mysql://localhost:3306/db",
                properties);

        // Creates a PoolableConnectionFactory that will wrap the
        // connection object created by the ConnectionFactory to add
        // object pooling functionality.
        PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, null);
        pcf.setValidationQuery("SELECT 1");

        // Creates an instance of GenericObjectPool that holds our
        // pool of connections object.
        GenericObjectPoolConfig<PoolableConnection> config = new GenericObjectPoolConfig<>();
        config.setTestOnBorrow(true);
        config.setMaxTotal(10);
        connectionPool = new GenericObjectPool<>(pcf, config);
        pcf.setPool(connectionPool);
        this.dataSource = new PoolingDataSource<>(connectionPool);
    }

    public static Connection getDatabaseConnection() throws SQLException {
        return Singleton.INSTANCE.dataSource.getConnection();
    }
}
