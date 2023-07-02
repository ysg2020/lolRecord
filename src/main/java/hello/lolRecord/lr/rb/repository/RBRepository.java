package hello.lolRecord.lr.rb.repository;

import hello.lolRecord.lr.rb.dto.RBInUpDTO;
import hello.lolRecord.lr.rb.dto.RBSelectListDTO;
import hello.lolRecord.lr.rb.dto.RBSelectOneDTO;
import hello.lolRecord.lr.rb.mapper.RBMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RBRepository {

    private final RBMapper rbMapper;

    public RBSelectOneDTO selectOne(int board_id){
        log.info("RB_repository : selectOne");
        return rbMapper.selectOne(board_id);
    }

    public int insert(RBInUpDTO inUpDTO){
        log.info("RB_repository : insert");
        return rbMapper.insert(inUpDTO);
    }

    public List<RBSelectListDTO> selectList(){
        log.info("RB_repository : selectList");
        return rbMapper.selectList();
    }

    public int update(RBInUpDTO inUpDTO){
        log.info("RB_repository : update");
        return rbMapper.update(inUpDTO);
    }

    public int delete(int board_id){
        log.info("RB_repository : delete");
        return rbMapper.delete(board_id);
    }

    public int updateViewCnt(int board_id){
        log.info("RB_repository : updateViewCnt");
        return rbMapper.updateViewCnt(board_id);
    }

    public int updateGoodCnt(int board_id){
        log.info("RB_repository : updateGoodCnt");
        return rbMapper.updateGoodCnt(board_id);
    }
}
