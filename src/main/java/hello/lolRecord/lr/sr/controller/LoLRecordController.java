package hello.lolRecord.lr.sr.controller;

import hello.lolRecord.lr.sr.service.RecordSearchSCService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/LOLRecord/search")
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
    @GetMapping(value = {"/summonerMatchSearchV2/{nickname}/{championName}","/summonerMatchSearchV2/{nickname}"})
    public ModelAndView summonerMatchSearchV2(@PathVariable String nickname ,@PathVariable(required = false) String championName, ModelAndView mv){
        log.info("summonerMatchSearchV2 controller 실행!!");
//        mv.addAttribute("result",recordSearchSCService.summonerMatchSearch(nickname));
//        mv.setViewName("ui/test"); CommonConfig 와 WebConfigure 사용해서 공통으로 뷰네임 셋팅!
        //참고 링크 : https://junseokdev.tistory.com/20
        if(championName == null){
            log.info("summonerMatchSearchV2 championName X");
            mv.addObject("result",recordSearchSCService.summonerMatchSearch(nickname));
        }else {
            log.info("summonerMatchSearchV2 championName O");
            mv.addObject("result", recordSearchSCService.summonerMatchSearch(nickname, championName));
        }
        log.info("summonerMatchSearchV2 Result : {}",mv);
        return mv;
    }
    @ResponseBody
    @GetMapping(value = {"/summonerMatchSearchV2dtl/{nickname}/{matchNum}/{championName}","/summonerMatchSearchV2dtl/{nickname}/{matchNum}"})
    public ModelAndView summonerMatchSearchV2_Dtl(@PathVariable String nickname ,@PathVariable int matchNum,@PathVariable(required = false) String championName, ModelAndView mv){
        log.info("summonerMatchSearchV2dtl controller 실행!!");
//        mv.addAttribute("result",recordSearchSCService.summonerMatchSearch(nickname));
//        mv.setViewName("ui/test"); CommonConfig 와 WebConfigure 사용해서 공통으로 뷰네임 셋팅!
        //참고 링크 : https://junseokdev.tistory.com/20
        if(championName == null){
            log.info("summonerMatchSearchV2dtl championName X");
            mv.addObject("result",recordSearchSCService.summonerMatchSearchDtl(nickname,matchNum));
        }else {
            log.info("summonerMatchSearchV2dtl championName O");
            mv.addObject("result", recordSearchSCService.summonerMatchSearchDtl(nickname, championName,matchNum));
        }
        log.info("summonerMatchSearchV2dtl Result : {}",mv);
        return mv;
    }


}
