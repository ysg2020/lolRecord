package hello.lolRecord.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

//자동 빈등록
@Component
public class ViewConfig implements HandlerMethodArgumentResolver {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        ModelAndView mv = new ModelAndView();
        HttpServletRequest nativeRequest = (HttpServletRequest) webRequest.getNativeRequest();
        String requestURI = nativeRequest.getRequestURI();
        log.info("requestURI : {}",requestURI);
        //mv.setViewName("jsonView");
        if(requestURI.contains("/LOLRecord/search")){
            log.info("setViewName : /LOLRecord/search");
            mv.setViewName("ui/sr/SRsearch");
        }
        return mv;
    }
}
