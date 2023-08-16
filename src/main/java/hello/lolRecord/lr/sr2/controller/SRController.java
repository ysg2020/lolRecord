package hello.lolRecord.lr.sr2.controller;

import hello.lolRecord.lr.sr2.service.SRService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/LOLRecord/search2")
@RequiredArgsConstructor
public class SRController {

    private final SRService srService;

    @GetMapping
    public String main(){
        return "ui/sr2/SRmain";
    }
    @GetMapping("/summoners/{nickName}")
    public ModelAndView matchSearch(@PathVariable String nickName,ModelAndView mv){
        mv.setViewName("ui/sr2/SRmySearch");
        return mv.addObject("matchSearch",srService.matchSearch(nickName));
    }
    @GetMapping("/summoners/re/{nickName}")
    public ModelAndView matchRefresh(@PathVariable String nickName,ModelAndView mv){
        mv.setViewName("ui/sr2/SRmySearch");
        return mv.addObject("matchSearch",srService.matchRefresh(nickName));
    }

    @GetMapping("/summoners/{nickName}/{matchNum}")
    public ModelAndView matchSearchDtl(@PathVariable String nickName,@PathVariable int matchNum,ModelAndView mv){
        mv.setViewName("ui/sr2/SRmySearchDtl");
        return mv.addObject("matchSearchDtl",srService.matchSearchDtl(nickName,matchNum));
    }
}
