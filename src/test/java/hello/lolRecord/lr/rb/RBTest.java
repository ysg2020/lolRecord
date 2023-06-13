package hello.lolRecord.lr.rb;

import hello.lolRecord.lr.rb.dto.RBInUpDTO;
import hello.lolRecord.lr.rb.dto.RBSelectListDTO;
import hello.lolRecord.lr.rb.dto.RBSelectOneDTO;
import hello.lolRecord.lr.rb.repository.RBRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class RBTest {
    @Autowired
    RBRepository rbRepository;

    int TestBoard_id = 35;

    @Test
    void selectOne(){
        RBSelectOneDTO rbSelectOneDTO = rbRepository.selectOne(TestBoard_id);
        log.info("rbSelectOneDTO : {}",rbSelectOneDTO.getTITLE());

    }

    @Test
    void insert(){
        RBInUpDTO rbInUpDTO = new RBInUpDTO();
        rbInUpDTO.setTITLE("테스트");
        rbInUpDTO.setCONTENTS("test");
        rbInUpDTO.setRPT_NICK("nick");
        rbInUpDTO.setUSER_ID("user");
        rbRepository.insert(rbInUpDTO);

    }

    @Test
    void selectList(){
        List<RBSelectListDTO> rbSelectListDTO = rbRepository.selectList();
        RBSelectListDTO rbSelectListDTO1 = rbSelectListDTO.get(0);
        log.info("rbSelectListDTO title : {}",rbSelectListDTO1.getTITLE());

    }

    @Test
    void update(){
        RBInUpDTO rbInUpDTO = new RBInUpDTO();
        rbInUpDTO.setTITLE("테스트2");
        rbInUpDTO.setCONTENTS("test2");
        rbInUpDTO.setRPT_NICK("nick2");
        rbInUpDTO.setBOARD_ID(TestBoard_id);
        rbRepository.update(rbInUpDTO);

    }

    @Test
    void delete(){
        rbRepository.delete(TestBoard_id);

    }

    @Test
    void updateViewCnt(){
        rbRepository.updateViewCnt(TestBoard_id);

    }
    @Test
    void updateGoodCnt(){
        rbRepository.updateGoodCnt(TestBoard_id);

    }

}
