package hello.lolRecord.lr.rb.service.impl;

import hello.lolRecord.lr.lu.service.LOLUserService;
import hello.lolRecord.lr.rb.dto.RBInUpDTO;
import hello.lolRecord.lr.rb.dto.RBSelectListDTO;
import hello.lolRecord.lr.rb.dto.RBSelectOneDTO;
import hello.lolRecord.lr.rb.repository.RBRepository;
import hello.lolRecord.lr.rb.service.RBService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RBServiceImpl implements RBService {

    private final RBRepository rbRepository;
    private final LOLUserService lolUserService;


    public RBSelectOneDTO selectOne(int board_id,String login_nick){
        log.info("RB_serviceImpl : selectOne");
        RBSelectOneDTO rbSelectOneDTO = rbRepository.selectOne(board_id);
        //1. 로그인한 사용자의 롤 닉네임과 해당 신고글의 닉네임(작성자)이 다를 경우
        // >> 신고글 작성자 본인이 아닐 경우에만 조회수 증가 및 수정 삭제 버튼 비활성화
        if(!rbSelectOneDTO.getWRITER().equals(login_nick)){
            rbRepository.updateViewCnt(board_id);
            rbSelectOneDTO.setUD("N");
        }else{
            rbSelectOneDTO.setUD("Y");
        }
        return rbSelectOneDTO;
    }

    public int insert(RBInUpDTO inUpDTO,int user_no){
        log.info("RB_serviceImpl : insert");
        //1. 로그인한 사용자 고유번호를 세팅해준다.
        inUpDTO.setUSER_NO(user_no);
        //2. 신고한 닉네임 존재하는지 체크한다.
        String nickCheck = lolUserService.summonerNickCheck(inUpDTO.getRPT_NICK());
        if(nickCheck != null){
            return rbRepository.insert(inUpDTO);
        }
        return -1;
    }

    public List<RBSelectListDTO> selectList(){
        log.info("RB_serviceImpl : selectList");
        return rbRepository.selectList();
    }

    public int update(RBInUpDTO inUpDTO){
        log.info("RB_serviceImpl : update");
        return rbRepository.update(inUpDTO);
    }

    public int delete(int board_id){
        log.info("RB_serviceImpl : delete");
        return rbRepository.delete(board_id);
    }

    public int updateViewCnt(int board_id){
        log.info("RB_serviceImpl : updateViewCnt");
        return rbRepository.updateViewCnt(board_id);
    }

}
