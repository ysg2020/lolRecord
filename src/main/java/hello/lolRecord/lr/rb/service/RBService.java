package hello.lolRecord.lr.rb.service;

import hello.lolRecord.lr.rb.dto.RBInUpDTO;
import hello.lolRecord.lr.rb.dto.RBSelectListDTO;
import hello.lolRecord.lr.rb.dto.RBSelectOneDTO;

import java.util.List;


public interface RBService {
    public RBSelectOneDTO selectOne(int board_id);
    public int insert(RBInUpDTO inUpDTO);
    public List<RBSelectListDTO> selectList();
    public int update(RBInUpDTO inUpDTO);
    public int delete(int board_id);
    public int updateViewCnt(int board_id);
    public int updateGoodCnt(int board_id);


}
