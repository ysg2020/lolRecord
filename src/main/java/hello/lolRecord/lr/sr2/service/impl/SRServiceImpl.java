package hello.lolRecord.lr.sr2.service.impl;

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
    public Map matchRefresh(String nickName) {
        matchService.addMatch(nickName);
        return matchService.getMyMatch(nickName);
    }

    @Override
    public Map matchSearchDtl(String nickName , int matchNum) {
        return matchService.getMatch(nickName,matchNum);
    }
}
