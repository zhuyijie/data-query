package com.baidu.ssp;

import com.baidu.ssp.tables.Field;
import com.baidu.ssp.tables.Table;
import com.baidu.ssp.tables.Type;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by mojie on 2015/2/10.
 */
public class TableFactory {

    private static final DataSource DATA_SOURCE = getDataSource();

    private static DataSource getDataSource() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=Mysql;TRACE_LEVEL_SYSTEM_OUT=2;DATABASE_TO_UPPER=false");
        ds.setUser("sa");
        ds.setPassword("sa");
        return populateDataSource(ds);
    }

    private static DataSource populateDataSource(DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setSqlScriptEncoding("UTF-8");
        populator.setScripts(new ClassPathResource("init_function.sql"),
                new ClassPathResource("init.sql"));
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            populator.populate(connection);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return dataSource;
    }




    private static Field makeField(String name, String rowExpression, Type type) {
        return new Field(name, rowExpression, type);
    }

    public static final Table defaultTable = defaultTable();

    public static Table defaultTable() {
        return new Table("adposition", DATA_SOURCE,
                Arrays.asList(
                        makeField("userId", "`userid`", Type.LONG),
                        makeField("timeField", "`date`", Type.STRING),
                        makeField("adPositionId", "`adpositionid`", Type.LONG),
                        makeField("request", "`showtimes`", Type.LONG),
                        makeField("view", "`deliveryshow`", Type.LONG),
                        makeField("click", "`clicktimes`", Type.LONG)));
    }
}
