package hello.lolRecord.lr.sr.service;

import hello.lolRecord.common.dto.MatchDto;
import hello.lolRecord.common.dto.SummonerDTO;

import java.util.List;

public interface RecordSearchBCService {

    public SummonerDTO summonerSearch(String nickname);
    public List summonerInfoSearch();
    public List getMatchId();
    public List matchSearch();
}
