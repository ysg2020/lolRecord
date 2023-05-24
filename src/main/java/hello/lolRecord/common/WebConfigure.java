package hello.lolRecord.common;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
@RequiredArgsConstructor
public class WebConfigure implements WebMvcConfigurer , WebMvcRegistrations {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ModelAndViewArgumentResolver config;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/LOLRecord/lolUser","/LOLRecord/lolUser/loginForm","/LOLRecord/lolUser/joinForm"
                                        ,"/LOLRecord/lolUser/login","/LOLRecord/lolUser/logout");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        log.info("WebConfigure : addArgumentResolvers 실행!!");
        resolvers.add(new AnnotationLoginResolver());
        resolvers.add(new AnnotationParamResolver());
        resolvers.add(config);


    }
    //리졸버 순서 변경 코드
  /*  @Override
    public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter(){
        log.info("getRequestMappingHandlerAdapter 오버라이딩!!");
        return new HandlerAdapterView();
    }*/

/*    @Bean
    public HandlerMethodArgumentResolver CommonConfig() {
        log.info("WebConfigure : CommonConfig 생성자 실행");
        return new CommonConfig();
    }*/
}
