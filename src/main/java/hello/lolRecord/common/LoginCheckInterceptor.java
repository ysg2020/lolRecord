package hello.lolRecord.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        log.info("인증 체크 인터셉터 실행 {}",requestURI);

        HttpSession session = request.getSession();
        if(session == null || session.getAttribute("LoginUser") == null){
            log.info("미인증 사용자 요청");
            response.sendRedirect("/LOLRecord/lolUser/loginForm");
            return false;
        }
        return true;
    }
}
