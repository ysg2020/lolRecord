package hello.lolRecord.lr.sr.service;

import hello.lolRecord.lr.sr.dto.MatchDto;
import hello.lolRecord.lr.sr.dto.SearchParam;

import java.util.List;
import java.util.Map;

public interface RecordSearchBCService {

    public String summonerSearch(String nickname);
    public Map summonerMatchSearch(String nickname);
    public Map summonerMatchSearch(String nickname,String championName);
    public Map summonerMatchSearchDtl(String nickname,int matchNum);
    public Map summonerMatchSearchDtl(String nickname,String championName,int matchNum);
    public List getMatchId();
    public List leagueSearch();
    public List matchSearch();
    public List matchSearchChamp(String nickname ,String championName);
    public Map matchSearchChampDtl(String nickname,String championName,int matchNum);
    public Map winLoseing();
    public Map top3(int matchNum);
    public List myMatchSearch(String nickname);
    public List mymatchSearchChamp(String nickname,String championName);
    public Map matchSearchDtl(String nickname,int matchNum);
    public List playChamp();
}
