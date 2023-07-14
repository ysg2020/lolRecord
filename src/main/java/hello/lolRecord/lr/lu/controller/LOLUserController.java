package hello.lolRecord.lr.lu.controller;

import hello.lolRecord.common.annotation.Login;
import hello.lolRecord.lr.lu.dto.LOLUserJoinForm;
import hello.lolRecord.lr.lu.dto.LOLUserLoginForm;
import hello.lolRecord.lr.lu.service.LOLUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.Map;

@Controller
@RequestMapping("/LOLRecord/lolUser")
@RequiredArgsConstructor
public class LOLUserController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final LOLUserService lolUserService;
    @GetMapping(value = "/test")
    public String test(){
        log.info("LU 테스트!");
        return "ui/test";
    }

    @ResponseBody
    @GetMapping(value = "/testing")
    public ModelAndView testing(ModelAndView mv){
        log.info("LU 테스트ing");
        mv.addObject("result","테스트!!");
        return mv;
    }

    @GetMapping
    public ModelAndView main(@Login LOLUserJoinForm LoginUser ,ModelAndView mv){
        log.info("LU 메인");
        log.info("Login : {}",LoginUser);
        mv.setViewName("ui/lu/LUmain");
        if(LoginUser == null){
            log.info("세션이 없습니다!!");
            mv.addObject("login","false");
            return mv;
        }
        log.info("세션이 있습니다!!");
        mv.addObject("login","true");
        return mv;
    }
    @GetMapping(value = "/loginForm")
    public ModelAndView loginForm(@ModelAttribute LOLUserLoginForm lolUserLoginForm, ModelAndView mv){
        log.info("LU loginForm");
        mv.setViewName("ui/lu/LUlogin");
        return mv;

    }
    @GetMapping(value = "/joinForm")
    public String joinForm(){
        log.info("LU join");
        return "ui/lu/LUjoin";
    }
    @ResponseBody
    @PostMapping(value = "/login")
    public ModelAndView login(@Valid @ModelAttribute LOLUserLoginForm lolUserLoginForm ,BindingResult bindingResult, ModelAndView mv , HttpServletRequest request) throws SQLException {
        log.info("login 실행!");
        log.info("요청 파라미터 : {}", lolUserLoginForm.getUSER_ID());
        log.info("ModelAndView getViewName = {}",mv.getViewName());
        if(bindingResult.hasErrors()){
            log.info("아이디 , 비밀번호 공백!!");
            mv.setViewName("ui/lu/LUlogin");
            return mv;
        }

        LOLUserJoinForm loginUserInfo = lolUserService.login(lolUserLoginForm);

        if(loginUserInfo != null){
            log.info("로그인 성공!!");
            HttpSession session = request.getSession();
            session.setAttribute("LoginUser" , loginUserInfo);
            mv.setViewName("redirect:/LOLRecord/lolUser");
        }else {
            log.info("로그인 실패!!");
            bindingResult.reject("LoginFail","로그인 실패!! 아이디와 비밀번호를 확인해주세요!!");
            mv.setViewName("ui/lu/LUlogin");
            return mv;
        }
        return mv;
    }
    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        log.info("로그아웃!!");
        HttpSession session = request.getSession(false);
        if (session != null) {
            log.info("세션 비우기!!");
            session.invalidate();
        }
        return "redirect:/LOLRecord/lolUser";
    }
    @ResponseBody
    @PostMapping(value = "/join")
    public ModelAndView join(@ModelAttribute LOLUserJoinForm lolUserJoinForm, ModelAndView mv) throws SQLException {
        log.info("LOLUserController : join");
        Map joinResult = lolUserService.join(lolUserJoinForm);
        log.info("joinResult : {}",joinResult);
        mv.setViewName("ui/lu/LUjoin");
        mv.addObject("joinResult",joinResult);
        return mv;
    }
}

