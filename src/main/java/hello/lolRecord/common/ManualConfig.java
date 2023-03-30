package hello.lolRecord.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ManualConfig {
    //수동 빈등록
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
