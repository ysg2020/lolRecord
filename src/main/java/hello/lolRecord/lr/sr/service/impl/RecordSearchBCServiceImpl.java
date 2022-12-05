package hello.lolRecord.lr.sr.service.impl;

import hello.lolRecord.common.SearchImpl;
import hello.lolRecord.common.Summoner;
import hello.lolRecord.common.SummonerInfo;
import hello.lolRecord.lr.sr.service.RecordSearchBCService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecordSearchBCServiceImpl implements RecordSearchBCService {

    private final SearchImpl searchImpl;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String SummonerUrl = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/";
    private final String SummonerInfoUrl = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/";
    private final String ApiKey = "?api_key=RGAPI-f458fbc9-911f-470a-8796-6b71fb38baa8";
    private String SummonerId = null;
    private String Puuid = null;

    @Override
    public Map summonerSearch(String nickname) {
        Map result = new HashMap();
        RestTemplate restTemplate = new RestTemplate();
        Summoner summoner = restTemplate.getForObject(SummonerUrl + nickname + ApiKey, Summoner.class);
        SummonerId = summoner.getId();
        Puuid = summoner.getPuuid();
        result.put("result",summoner);

        log.info("summoner = {}",summoner);
        log.info("summonerLevel = {}",summoner.getSummonerLevel());
        log.info("summonerAccountId = {}",summoner.getAccountId());
        log.info("summonerName = {}",summoner.getName());
        log.info("result = {}",result);

        return result;
    }

    @Override
    public Map summonerInfoSearch() {
        log.info("summonerInfoSearch BC 서비스 실행!");
        log.info("서머너ID 값은..??={}",SummonerId);
        Map result = new HashMap();
        RestTemplate restTemplate = new RestTemplate();
        //SummonerInfo summonerInfo = restTemplate.getForObject(SummonerInfoUrl + SummonerId + ApiKey, SummonerInfo.class);
        //라이엇api(League-V4)  summonerInfo를 배열로 받아야 했었음
        List<SummonerInfo> summonerInfo = restTemplate.getForObject(SummonerInfoUrl + SummonerId + ApiKey, List.class);
        result.put("result",summonerInfo);

        log.info("summonerInfo = {}",summonerInfo);
        log.info("summonerInfo = {}",summonerInfo.get(0));
        //왜인지는 모르겠으나 info에 있는 get메소드 실행시 에러남 
        //구글링해서 찾아본 결과 list 타입으로 변환하려 할떄 발생하는 버그라고함;;
        log.info("result = {}",result);
        return result;
    }
}
