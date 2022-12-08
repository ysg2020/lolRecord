package hello.lolRecord.lr.sr.service.impl;

import hello.lolRecord.common.*;
import hello.lolRecord.common.dto.MatchDto;
import hello.lolRecord.common.dto.SummonerDTO;
import hello.lolRecord.common.dto.LeagueEntryDTO;
import hello.lolRecord.lr.sr.service.RecordSearchBCService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordSearchBCServiceImpl implements RecordSearchBCService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private String SummonerId = null;
    private String Puuid = null;
    @Override
    public SummonerDTO summonerSearch(String nickname) {
        RestTemplate restTemplate = new RestTemplate();
        SummonerDTO summonerDTO = restTemplate.getForObject(ApiCommon.SummonerUrl + nickname + ApiCommon.ApiKey, SummonerDTO.class);
        SummonerId = summonerDTO.getId();
        Puuid = summonerDTO.getPuuid();

        log.info("summoner = {}", summonerDTO);
        log.info("summonerLevel = {}", summonerDTO.getSummonerLevel());
        log.info("summonerAccountId = {}", summonerDTO.getAccountId());
        log.info("summonerName = {}", summonerDTO.getName());

        return summonerDTO;
    }

    @Override
    public List summonerInfoSearch() {
        log.info("summonerInfoSearch BC 서비스 실행!");
        log.info("서머너ID 값은..??={}",SummonerId);
        RestTemplate restTemplate = new RestTemplate();
        //SummonerInfo summonerInfo = restTemplate.getForObject(SummonerInfoUrl + SummonerId + ApiKey, SummonerInfo.class);
        //라이엇api(League-V4)  summonerInfo를 배열로 받아야 했었음
        List<LeagueEntryDTO> leagueEntryDTO = restTemplate.getForObject(ApiCommon.SummonerInfoUrl + SummonerId + ApiCommon.ApiKey, List.class);

        log.info("summonerInfo = {}", leagueEntryDTO);
        log.info("summonerInfo = {}", leagueEntryDTO.get(0));
        //왜인지는 모르겠으나 info에 있는 get메소드 실행시 에러남 
        //구글링해서 찾아본 결과 list 타입으로 변환하려 할떄 발생하는 버그라고함;;
        return leagueEntryDTO;
    }

    @Override
    public List matchSearch() {
        log.info("matchSearch BC 서비스 실행!");
        List matchId = getMatchId();
        RestTemplate restTemplate = new RestTemplate();
        List<MatchDto> matchDtoList = new ArrayList<>();
        MatchDto matchDto = null;

        //20개의 매치정보를 가져오기
        for(int i=0;i<20;i++) {
            matchDto = restTemplate.getForObject(ApiCommon.MatchInfoUrl + matchId.get(i) + ApiCommon.ApiKey, MatchDto.class);
            matchDtoList.add(matchDto);
        }
        return matchDtoList;
    }



    /**
     * 내부 함수
     * @return 매치ID 리스트
     */
    @Override
    public List getMatchId() {
        log.info("getMatchId BC 서비스 실행!");
        RestTemplate restTemplate = new RestTemplate();
        List<String> matchIdList = restTemplate.getForObject(ApiCommon.MatchIdUrl + Puuid +"/ids"+ ApiCommon.ApiKey, List.class);
        return matchIdList;
    }

}
