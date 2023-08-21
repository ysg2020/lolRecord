package hello.lolRecord.lr.rb.controller;

import hello.lolRecord.lr.lu.dto.LOLUserJoinForm;
import hello.lolRecord.lr.lu.service.LOLUserService;
import hello.lolRecord.lr.rb.dto.RBInUpDTO;
import hello.lolRecord.lr.rb.dto.RBSelectOneDTO;
import hello.lolRecord.lr.rb.service.RBService;
import hello.lolRecord.lr.sr2.service.SRService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/LOLRecord/ReportBoard")
@RequiredArgsConstructor
public class RBController {

    private final RBService rbService;
    private final SRService srService;

    @GetMapping(value="/List")
    public ModelAndView selectList(ModelAndView mv){
        mv.setViewName("ui/rb/RBmain");
        mv.addObject("result",rbService.selectList());
        return mv;
    }
    @GetMapping(value="/One")
    public ModelAndView insertForm(ModelAndView mv, HttpServletRequest request){
        //로그인한 사용자의 롤 닉네임으로 기본 조회
        HttpSession session = request.getSession(false);
        LOLUserJoinForm loginUser = (LOLUserJoinForm) session.getAttribute("LoginUser");
        mv.setViewName("ui/rb/RBmySearch");
        mv.addObject("matchSearch",srService.myMatchSearchUserID(loginUser.getUSER_NO()));
        return mv;
    }
    @GetMapping("/One/{nickName}/{matchNum}")
    public ModelAndView matchSearchDtl(@PathVariable String nickName,@PathVariable int matchNum,ModelAndView mv){
        mv.setViewName("ui/rb/RBmySearchDtl");
        return mv.addObject("matchSearchDtl",srService.matchSearchDtl(nickName,matchNum));
    }
    @GetMapping(value="/One/rpt/{rptNickname}")
    public ModelAndView insertFormRpt(@PathVariable String rptNickname, ModelAndView mv, HttpServletRequest request){
        //로그인한 사용자의 롤 닉네임을 작성자에 기본값으로 세팅
        HttpSession session = request.getSession(false);
        LOLUserJoinForm loginUser = (LOLUserJoinForm) session.getAttribute("LoginUser");
        mv.setViewName("ui/rb/RBadd");
        mv.addObject("writer",loginUser.getLOL_NICK());
        mv.addObject("rptNickname",rptNickname);
        return mv;
    }
    @PostMapping(value="/One")
    public ModelAndView insert(@ModelAttribute RBInUpDTO rbInUpDTO,  ModelAndView mv,HttpServletRequest request){
        log.info("RBController insert");
        //1. 로그인한 사용자의 고유번호를 가져온다.
        HttpSession session = request.getSession(false);
        LOLUserJoinForm loginUser = (LOLUserJoinForm) session.getAttribute("LoginUser");
        int user_no = loginUser.getUSER_NO();
        //2. 신고글 등록
        int result = rbService.insert(rbInUpDTO,user_no);
        if(result != -1){
            //등록 후 재조회
            mv.setViewName("redirect:/LOLRecord/ReportBoard/List");
        }else {
            mv.addObject("result",-1);
            mv.setViewName("ui/rb/RBadd");
        }
        return mv;
    }
    @GetMapping(value="/One/{board_id}")
    public ModelAndView selectOne(@PathVariable int board_id, ModelAndView mv,HttpServletRequest request){
        log.info("RBController : selectOne");
        mv.setViewName("ui/rb/RBdtl");
        //1. 로그인한 사용자의 롤 닉네임을 가져온다.
        HttpSession session = request.getSession(false);
        LOLUserJoinForm loginUser = (LOLUserJoinForm) session.getAttribute("LoginUser");
        String login_nick = loginUser.getLOL_NICK();
        //2. 신고글 상세 조회
        RBSelectOneDTO rbSelectOneDTO = rbService.selectOne(board_id,login_nick);
        mv.addObject("result", rbSelectOneDTO);
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
