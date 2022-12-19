package hello.lolRecord.lr.sr.service;

import hello.lolRecord.common.dto.MatchDto;
import hello.lolRecord.common.dto.SummonerDTO;

import java.util.List;
import java.util.Map;

public interface RecordSearchBCService {

    public void summonerSearch(String nickname);
    public Map summonerMatchSearch(String nickname);
    public List getMatchId();
    public List leagueSearch();
    public List matchSearch();
    public Map winLoseing();
}
