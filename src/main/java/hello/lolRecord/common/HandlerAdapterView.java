package hello.lolRecord.common;

import hello.lolRecord.common.resolver.ModelAndViewArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.annotation.MapMethodProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class HandlerAdapterView extends RequestMappingHandlerAdapter {

    public int index;
    public int paramIndex = -1;

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        List<HandlerMethodArgumentResolver> resolverList = new ArrayList(this.getArgumentResolvers());
        for(int i=0;i<resolverList.size();i++){
            HandlerMethodArgumentResolver resolver = resolverList.get(i);
            if(resolver instanceof MapMethodProcessor){
                index = i;
                log.info("index 의 값은? {}",index);
            }else if(resolver instanceof ModelAndViewArgumentResolver){
                paramIndex = i;
                log.info("paramIndex 의 값은? {}",paramIndex);
            }
            log.info("resolver 의 값은? {}",resolver);
        }
        if(paramIndex != -1) {
            log.info("삭제후 추가!");
            HandlerMethodArgumentResolver paramResolver = resolverList.remove(paramIndex);
            resolverList.add(index, paramResolver);
        }

        this.setArgumentResolvers(resolverList);

    }

}
