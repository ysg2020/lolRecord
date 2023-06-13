package hello.lolRecord.lr.rb.service.impl;

import hello.lolRecord.lr.rb.dto.RBInUpDTO;
import hello.lolRecord.lr.rb.dto.RBSelectListDTO;
import hello.lolRecord.lr.rb.dto.RBSelectOneDTO;
import hello.lolRecord.lr.rb.repository.RBRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RBServiceImpl {

    private final RBRepository rbRepository;


    public RBSelectOneDTO selectOne(int board_id){
        log.info("RB_serviceImpl : selectOne");
        return rbRepository.selectOne(board_id);
    }

    public int insert(RBInUpDTO inUpDTO){
        log.info("RB_serviceImpl : insert");
        return rbRepository.insert(inUpDTO);
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

    public int updateGoodCnt(int board_id){
        log.info("RB_serviceImpl : updateGoodCnt");
        return rbRepository.updateGoodCnt(board_id);
    }
}
