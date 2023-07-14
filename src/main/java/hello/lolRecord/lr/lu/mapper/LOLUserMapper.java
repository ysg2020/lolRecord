package hello.lolRecord.lr.lu.mapper;

import hello.lolRecord.lr.lu.dto.LOLUserJoinForm;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LOLUserMapper {

    int save(LOLUserJoinForm lolUser);

    String findPwd(String user_id);

    String findNick(String lol_nick);

    LOLUserJoinForm findUser(String user_id);
}
