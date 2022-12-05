package hello.lolRecord.lr.sr.controller;

import hello.lolRecord.common.Summoner;
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
    public String Requesttest(@RequestBody Summoner summoner){
        log.info("이 user에는 http 요청 메세지를 객체로 가져옴");
        log.info("user id 값은?? ={}", summoner.getId());
        log.info("accountId  값은??={}", summoner.getAccountId());
        return "ok";
    }
    @ResponseBody
    @GetMapping("/summonerSearch/{nickname}")
    public String summonerSearch(@PathVariable String nickname){
        log.info("summonerSearch 실행!!");
        Map Elcom = recordSearchSCService.summonerSearch(nickname);
        log.info("summonerSearch 실행완료!!={}",Elcom);
        return "ok";
    }
    @ResponseBody
    @GetMapping("/summonerInfoSearch")
    public String summonerInfoSearch(){
        log.info("summonerInfoSearch 실행!!");
        Map Elcom = recordSearchSCService.summonerInfoSearch();
        log.info("summonerInfoSearch 실행완료!!={}",Elcom);
        return "ok";
    }

}
