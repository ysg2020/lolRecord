package hello.lolRecord.lr.lu.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.lolRecord.common.db.ConnectionConst;
import hello.lolRecord.lr.lu.dto.LOLUserJoinForm;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class LOLUserRepositoryTest {

    @Test
    void save() throws SQLException {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(ConnectionConst.URL);
        dataSource.setUsername(ConnectionConst.USERNAME);
        dataSource.setPassword( ConnectionConst.PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("My Pool");
        LOLUserRepository lolUserRepository = new LOLUserRepository(dataSource);
        //LOLUser lolUser = new LOLUser("testId","testPwd","20230413","010-1234-1234","seoul","M","faker","jug","mid");
        //lolUserRepository.save(lolUser);
        LOLUserJoinForm lolUser = new LOLUserJoinForm();
        lolUser.setUSER_ID("s");//이런식으로 테스트하면될거같음.
    }
    @Test
    void findById() throws SQLException{
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(ConnectionConst.URL);
        dataSource.setUsername(ConnectionConst.USERNAME);
        dataSource.setPassword(ConnectionConst.PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("My Pool");
        LOLUserRepository lolUserRepository = new LOLUserRepository(dataSource);
        lolUserRepository.findPwd("testId");
    }


}