package hello.lolRecord.common.db;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class DBConnectionUtil {
    public static Connection getConection() throws SQLException {
        Connection connection = DriverManager.getConnection(ConnectionConst.URL, ConnectionConst.USERNAME, ConnectionConst.PASSWORD);
        return connection;
    }
}
