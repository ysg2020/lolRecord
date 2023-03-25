package hello.lolRecord.lr.sr.service.impl;

import hello.lolRecord.lr.sr.dto.SearchParam;
import hello.lolRecord.lr.sr.service.RecordSearchBCService;
import hello.lolRecord.lr.sr.service.RecordSearchSCService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecordSearchSCServiceImpl implements RecordSearchSCService {

    private final RecordSearchBCService recordSearchBCService;
    private final Logger log = LoggerFactory.getLogger(getClass());


    @Override
    public Map summonerMatchSearch(String nickname) {
        log.info("summonerMatchSearch SC 서비스 실행!");
        Map result = new HashMap();
        result.put("result",recordSearchBCService.summonerMatchSearch(nickname));
        return result;
    }
    @Override
    public Map summonerMatchSearch(String nickname, String championName) {
        log.info("summonerMatchSearch SC 서비스 실행!");
        Map result = new HashMap();
        result.put("result",recordSearchBCService.summonerMatchSearch(nickname,championName));
        return result;
    }
    @Override
    public Map summonerMatchSearchDtl(String nickname, int matchNum) {
        log.info("summonerMatchSearch SC 서비스 실행!");
        Map result = new HashMap();
        result.put("result",recordSearchBCService.summonerMatchSearchDtl(nickname,matchNum));
        return result;
    }
    @Override
    public Map summonerMatchSearchDtl(String nickname,String championName, int matchNum) {
        log.info("summonerMatchSearch SC 서비스 실행!");
        Map result = new HashMap();
        result.put("result",recordSearchBCService.summonerMatchSearchDtl(nickname,championName,matchNum));
        return result;
    }
    @Override
    public Map summonerMatchSearch(SearchParam searchParam){
        log.info("summonerMatchSearch BC 서비스 실행!");
        Map result = new HashMap();
        //닉네임 + 매치넘버 + 챔피언이름
        if (searchParam.getMatchNum() != null && searchParam.getChampionName() != null){
            log.info("닉네임 + 매치넘버 + 챔피언이름");
            result.put("result",recordSearchBCService.summonerMatchSearchDtl(searchParam.getNickName(), searchParam.getChampionName(), searchParam.getMatchNum()));
        }
        //닉네임 + 매치넘버
        else if(searchParam.getMatchNum() != null){
            log.info("닉네임 + 매치넘버");
            result.put("result",recordSearchBCService.summonerMatchSearchDtl(searchParam.getNickName(),searchParam.getMatchNum()));
        }
        //닉네임 + 챔피언이름
        else if(searchParam.getChampionName() != null){
            log.info("닉네임 + 챔피언이름");
            result.put("result",recordSearchBCService.summonerMatchSearch(searchParam.getNickName(),searchParam.getChampionName()));
        }
        //닉네임
        else{
            log.info("닉네임");
            result.put("result",recordSearchBCService.summonerMatchSearch(searchParam.getNickName()));
        }
        return result;
    }
}
