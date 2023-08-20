package hello.lolRecord.lr.sr2.service;

import hello.lolRecord.lr.sr2.dto.SummonerDTO;

import java.util.Map;

public interface MatchService {
    public void addMatch(String nickName,int user_no);
    public Map getMyMatch(String nickName);
    public Map getMatch(String nickName, int matchNum);
    public boolean summonerCheck(String nickName);
    public SummonerDTO summonerUserID(int user_no);


}
