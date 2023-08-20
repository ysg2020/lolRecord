package hello.lolRecord.lr.sr2.service.impl;

import hello.lolRecord.lr.sr2.dto.SummonerDTO;
import hello.lolRecord.lr.sr2.service.MatchService;
import hello.lolRecord.lr.sr2.service.SRService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@Service
@RequiredArgsConstructor
public class SRServiceImpl implements SRService {

    private final MatchService matchService;
    @Override
    public Map matchSearch(String nickName) {
        if(!matchService.summonerCheck(nickName)){
            log.info("SRServiceImpl : 존재하지 않는 닉네임");
            Map result = new HashMap();
            result.put("myPtip",null);
            result.put("myLeague",null);
            result.put("nickName",null);
            return result;
        }
        return matchService.getMyMatch(nickName);
    }

    @Override
    public Map matchRefresh(String nickName,int user_no) {
        matchService.addMatch(nickName,user_no);
        return matchService.getMyMatch(nickName);
    }

    @Override
    public Map matchSearchDtl(String nickName , int matchNum) {
        return matchService.getMatch(nickName,matchNum);
    }

    @Override
    public Map myMatchSearchUserID(int user_no) {
        Map result = new HashMap();
        String nickName;
        //1. 해당 유저 id로 저장되어있는 소환사 정보를 가져온다
        SummonerDTO summoner = matchService.summonerUserID(user_no);
        //2. 없는 경우
        if(summoner == null){
            log.info("해당 유저 id가 세팅 안되어 있음  >> 전적 갱신이 안되어 있음");
            result.put("myPtip",null);
            result.put("myLeague",null);
            result.put("nickName",null);
            return result;
        }

        nickName = summoner.getName();
        return matchService.getMyMatch(nickName);
    }
}
