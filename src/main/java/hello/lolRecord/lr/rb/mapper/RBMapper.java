package hello.lolRecord.lr.rb.mapper;

import hello.lolRecord.lr.rb.dto.RBInUpDTO;
import hello.lolRecord.lr.rb.dto.RBSelectListDTO;
import hello.lolRecord.lr.rb.dto.RBSelectOneDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RBMapper {

    RBSelectOneDTO selectOne(int board_id);

    List<RBSelectListDTO> selectList();

    int insert(RBInUpDTO inUpDTO);

    int update(RBInUpDTO inUpDTO);

    int delete(int board_id);

    int updateViewCnt(int board_id);

    int updateGoodCnt(int board_id);


}
