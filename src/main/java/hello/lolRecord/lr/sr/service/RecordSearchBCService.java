package hello.lolRecord.lr.sr.service;

import java.util.List;
import java.util.Map;

public interface RecordSearchBCService {

    public void summonerSearch(String nickname);
    public Map summonerMatchSearch(String nickname);
    public Map summonerMatchSearch(String nickname,String championName);
    public List getMatchId();
    public List leagueSearch();
    public List matchSearch();
    public List matchSearchChamp(String nickname ,String championName);
    public Map winLoseing();
    public Map top3();
    public List myMatchSearch(String nickname);
    public List mymatchSearchChamp(String nickname,String championName);
}
