package hello.lolRecord.common.resolver;

import hello.lolRecord.common.annotation.Login;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class AnnotationLoginResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("Annotation Login supportsParameter 실행!!");
        log.info("Annotation Login parameter 타입 : {}",parameter.getParameterType());
        log.info("Annotation Login parameter boolean : {}",Object.class.isAssignableFrom(parameter.getParameterType())
                && parameter.hasParameterAnnotation(Login.class));
        return Object.class.isAssignableFrom(parameter.getParameterType())
                && parameter.hasParameterAnnotation(Login.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("AnnotationLoginResolver resolveArgument 실행!!");
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        if(session == null){
            log.info("AnnotationLoginResolver session null!!");
            return null;
        }
        log.info("AnnotationLoginResolver sessoin : {} ",session.getAttribute("LoginUser"));
        return session.getAttribute("LoginUser");
    }
}
