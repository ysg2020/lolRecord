package hello.lolRecord.lr.lu.service;

import hello.lolRecord.lr.lu.dto.LOLUserJoinForm;
import hello.lolRecord.lr.lu.dto.LOLUserLoginForm;

import java.sql.SQLException;
import java.util.Map;

public interface LOLUserService {
    public LOLUserJoinForm login(LOLUserLoginForm lolUser) throws SQLException;
    public Map join(LOLUserJoinForm lolUser) throws SQLException;
    public String summonerNickCheck(String nickname);
}
