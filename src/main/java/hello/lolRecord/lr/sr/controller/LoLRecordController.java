package hello.lolRecord.lr.sr.controller;

import hello.lolRecord.common.dto.SummonerDTO;
import hello.lolRecord.lr.sr.service.RecordSearchSCService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/LOLRecord")
@RequiredArgsConstructor
public class LoLRecordController {


    private final Logger log = LoggerFactory.getLogger(getClass());
    private final RecordSearchSCService recordSearchSCService;


    @ResponseBody
    @PostMapping("/test" )
    public String Requesttest(@RequestBody SummonerDTO summonerDTO){
        log.info("이 user에는 http 요청 메세지를 객체로 가져옴");
        log.info("user id 값은?? ={}", summonerDTO.getId());
        log.info("accountId  값은??={}", summonerDTO.getAccountId());
        return "ok";
    }
    @GetMapping("/ui/test" )
    public String RequestUiTest(){
        log.info("테스트");
        return "/ui/test";
    }
    @ResponseBody
    @GetMapping("/summonerSearch")
    public Map summonerSearch(@RequestParam String nickname){
        log.info("summonerSearch controller 실행!!");
        Map Elcom = recordSearchSCService.summonerSearch(nickname);
        log.info("summonerSearch controller 실행완료!!={}",Elcom);
        return Elcom;
    }
    @ResponseBody
    @GetMapping("/summonerInfoSearch")
    public Map summonerInfoSearch(){
        log.info("summonerInfoSearch controller 실행!!");
        Map Elcom = recordSearchSCService.summonerInfoSearch();
        log.info("summonerInfoSearch controller 실행완료!!={}",Elcom);
        return Elcom;
    }
    @ResponseBody
    @GetMapping("/matchSearch")
    public Map matchSearch(){
        log.info("matchSearch controller 실행!!");
        Map Elcom = recordSearchSCService.matchSearch();
        log.info("matchSearch controller 실행완료!!={}",Elcom);
        return Elcom;
    }

}
