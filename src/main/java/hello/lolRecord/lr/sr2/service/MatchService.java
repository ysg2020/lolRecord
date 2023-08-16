package hello.lolRecord.lr.sr2.service;

import java.util.Map;

public interface MatchService {
    public void addMatch(String nickName);
    public Map getMyMatch(String nickName);
    public Map getMatch(String nickName, int matchNum);
    public boolean summonerCheck(String nickName);

}
