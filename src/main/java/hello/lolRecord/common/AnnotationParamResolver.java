package hello.lolRecord.common;

import hello.lolRecord.lr.lu.dto.LOLUserLoginForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class AnnotationParamResolver implements HandlerMethodArgumentResolver{


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("AnnotationParam supportsParameter 실행!!");
        log.info("AnnotationParam parameter 타입 : {}",parameter.getParameterType());
        log.info("AnnotationParam parameter boolean : {}",Object.class.isAssignableFrom(parameter.getParameterType())
                && parameter.hasParameterAnnotation(Param.class));
        return Object.class.isAssignableFrom(parameter.getParameterType())
                && parameter.hasParameterAnnotation(Param.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("AnnotationParam resolveArgument 실행!!");
        HttpServletRequest nativeRequest = (HttpServletRequest) webRequest.getNativeRequest();
        LOLUserLoginForm lolUserLoginForm = new LOLUserLoginForm();
        lolUserLoginForm.setUSER_ID(nativeRequest.getParameter("USER_ID"));
        lolUserLoginForm.setPWD(nativeRequest.getParameter("PWD"));
        return lolUserLoginForm;
    }
}
