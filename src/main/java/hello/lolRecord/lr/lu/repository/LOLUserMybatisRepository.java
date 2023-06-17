package hello.lolRecord.lr.lu.repository;

import hello.lolRecord.lr.lu.dto.LOLUserJoinForm;
import hello.lolRecord.lr.lu.mapper.LOLUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LOLUserMybatisRepository {
    private final LOLUserMapper lolUserMapper;

    public int save(LOLUserJoinForm lolUser) throws SQLException {
        log.info("Mybatis save!!");
        lolUserMapper.save(lolUser);
        return 1;
    }

    public String findPwd(String user_id){
        log.info("Mybatis findPwd!!");
        return lolUserMapper.findPwd(user_id);
    }

    public String findNick(String nickname){
        log.info("Mybatis findNick");
        return lolUserMapper.findNick(nickname);

    }
}
