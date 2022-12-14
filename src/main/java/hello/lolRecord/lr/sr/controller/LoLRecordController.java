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


}
