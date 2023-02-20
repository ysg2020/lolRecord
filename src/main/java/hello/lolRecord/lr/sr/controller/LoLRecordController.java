package hello.lolRecord.lr.sr.controller;

import hello.lolRecord.common.dto.SummonerDTO;
import hello.lolRecord.lr.sr.service.RecordSearchSCService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/LOLRecord")
@RequiredArgsConstructor
public class LoLRecordController {


    private final Logger log = LoggerFactory.getLogger(getClass());
    private final RecordSearchSCService recordSearchSCService;

    @GetMapping("/ui/test" )
    public String RequestUiTest(){
        log.info("테스트");
        return "/ui/test";
    }

    @ResponseBody
    @GetMapping("/summonerMatchSearch")
    public Map summonerMatchSearch(@RequestParam String nickname){
        log.info("summonerMatchSearch controller 실행!!");
        Map Elcom = recordSearchSCService.summonerMatchSearch(nickname);
        log.info("summonerMatchSearch controller 실행완료!!={}",Elcom);
        return Elcom;
    }

    @ResponseBody
    @GetMapping("/summonerMatchSearchV2")
    public ModelAndView summonerMatchSearchV2(@RequestParam String nickname ,@RequestParam String championName, ModelAndView mv){
        log.info("summonerMatchSearchV2 controller 실행!!");
//        mv.addAttribute("result",recordSearchSCService.summonerMatchSearch(nickname));
//        mv.setViewName("ui/test"); CommonConfig 와 WebConfigure 사용해서 공통으로 뷰네임 셋팅!
        //참고 링크 : https://junseokdev.tistory.com/20
        mv.addObject("result",recordSearchSCService.summonerMatchSearch(nickname,championName));
        log.info("summonerMatchSearchV2 Result : {}",mv);
        return mv;
    }

}
