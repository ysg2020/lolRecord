package hello.lolRecord.common;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
@RequiredArgsConstructor
public class WebConfigure implements WebMvcConfigurer {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ViewConfig config;
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        log.info("WebConfigure : CommonConfig 추가");
        resolvers.add(config);
    }

/*    @Bean
    public HandlerMethodArgumentResolver CommonConfig() {
        log.info("WebConfigure : CommonConfig 생성자 실행");
        return new CommonConfig();
    }*/
}
