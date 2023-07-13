package hello.lolRecord.lr.rb.controller;

import hello.lolRecord.lr.rb.dto.RBInUpDTO;
import hello.lolRecord.lr.rb.dto.RBSelectOneDTO;
import hello.lolRecord.lr.rb.service.RBService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/LOLRecord/ReportBoard")
@RequiredArgsConstructor
public class RBController {

    private final RBService rbService;

    @GetMapping(value="/List")
    public ModelAndView selectList(ModelAndView mv){
        mv.setViewName("ui/rb/RBmain");
        mv.addObject("result",rbService.selectList());
        return mv;
    }
    @GetMapping(value="/One")
    public String insertForm(){
        return "ui/rb/RBadd";
    }
    @PostMapping(value="/One")
    public ModelAndView insert(@ModelAttribute RBInUpDTO rbInUpDTO,  ModelAndView mv){
        log.info("RBController insert : {}",rbInUpDTO.getUSER_ID());
        mv.addObject("result",rbService.insert(rbInUpDTO));
        //등록 후 재조회
        mv.setViewName("redirect:/LOLRecord/ReportBoard/List");
        return mv;
    }
    @GetMapping(value="/One/{board_id}")
    public ModelAndView selectOne(@PathVariable int board_id, ModelAndView mv){
        log.info("RBController : selectOne");
        mv.setViewName("ui/rb/RBdtl");
        mv.addObject("result",rbService.selectOne(board_id));
        return mv;
    }

    @PostMapping(value="/One/{board_id}")
    public ModelAndView update(@ModelAttribute RBInUpDTO rbInUpDTO, ModelAndView mv){
        log.info("RBController : update");
        log.info("rbInUpDTO : {}",rbInUpDTO.getBOARD_ID());
        rbService.update(rbInUpDTO);
        //수정 후 재조회
        mv.setViewName("redirect:/LOLRecord/ReportBoard/One/{board_id}");
        return mv;
    }

    @PostMapping(value="/remove")
    public ModelAndView delete(@ModelAttribute RBInUpDTO rbInUpDTO, ModelAndView mv){
        log.info("RBController : delete");
        log.info("rbInUpDTO : {}",rbInUpDTO.getBOARD_ID());
        rbService.delete(rbInUpDTO.getBOARD_ID());
        //삭제 후 재조회
        mv.setViewName("redirect:/LOLRecord/ReportBoard/List");
        return mv;
    }

}
