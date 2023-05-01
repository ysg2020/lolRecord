package hello.lolRecord.lr.lu.controller;

import hello.lolRecord.lr.lu.dto.LOLUserJoinForm;
import hello.lolRecord.lr.lu.dto.LOLUserLoginForm;
import hello.lolRecord.lr.lu.service.LOLUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

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

    @GetMapping(value = "/testing")
    public ModelAndView testing(ModelAndView mv){
        log.info("LU 테스트ing");
        mv.addObject("result","테스트!!");
        return mv;
    }

    @GetMapping
    public String main(){
        log.info("LU 메인");
        return "ui/lu/LUmain";
    }
    @GetMapping(value = "/loginForm")
    public String loginForm(@ModelAttribute LOLUserLoginForm lolUserLoginForm){
        log.info("LU login");
        return "ui/lu/LUlogin";
    }
    @GetMapping(value = "/joinForm")
    public String joinForm(@ModelAttribute LOLUserJoinForm lolUserJoinForm){
        log.info("LU join");
        return "ui/lu/LUjoin";
    }
    @ResponseBody
    @PostMapping(value = "/login")
    public ModelAndView login(@ModelAttribute LOLUserLoginForm lolUserLoginForm, ModelAndView mv) throws SQLException {
        log.info("login 실행!");
        log.info("요청 파라미터 : {}", lolUserLoginForm.getUSER_ID());
        String login = lolUserService.login(lolUserLoginForm);
        if(login.equals("success")){
            mv.setViewName("ui/sr/SRmain");
        }else {
            mv.setViewName("ui/lu/LUmain");
        }
        return mv;
    }

    @ResponseBody
    @PostMapping(value = "/join")
    public ModelAndView join(@ModelAttribute LOLUserJoinForm lolUserJoinForm, ModelAndView mv) throws SQLException {
        log.info("login 실행!");
        log.info("요청 파라미터 : {}", lolUserJoinForm.getUSER_ID());
        String login = lolUserService.join(lolUserJoinForm);
        mv.setViewName("ui/lu/LUmain");
        return mv;
    }
}

