package hello.lolRecord.common;

import com.zaxxer.hikari.HikariDataSource;
import hello.lolRecord.common.db.ConnectionConst;
import hello.lolRecord.lr.lu.repository.LOLUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;


@Configuration
public class ManualConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());
    //수동 빈등록
    @Bean
    public RestTemplate restTemplate(){
        log.info("restTemplate 수동 빈등록");
        return new RestTemplate();
    }
    @Bean
    public LOLUserRepository Connection(){
        log.info("LOLUserRepository 수동 빈등록");
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(ConnectionConst.URL);
        dataSource.setUsername(ConnectionConst.USERNAME);
        dataSource.setPassword( ConnectionConst.PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("My Pool");
        return new LOLUserRepository(dataSource);
    }
}
