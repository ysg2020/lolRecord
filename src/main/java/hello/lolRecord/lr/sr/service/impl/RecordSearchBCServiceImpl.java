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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecordSearchBCServiceImpl implements RecordSearchBCService {

    private final RestTemplate restTemplate;
    private final Logger log = LoggerFactory.getLogger(getClass());

    private String SummonerId = null;
    private String Puuid = null;
    private String summonerName = null;

    @Override
    public Map summonerMatchSearch(String nickname) {
        log.info("summonerMatchSearch BC 서비스 실행!");
        Map result = new HashMap();

        //API 요청에 필요한 ID 가져오기
        summonerSearch(nickname);

        //리그정보,매치정보 각각 담아주기
        result.put("summonerSearch",leagueSearch());
        result.put("matchSearch",matchSearch());
        result.put("winLoseing",winLoseing());

        return result;
    }


    /**
     * 내부 함수
     * summonerSearch()
     * 소환사 정보나 경기 정보를 가져오기 위한 ID (API 요청에 필요한 ID)들을 가져온다
     * @return void
     */

    @Override
    public void summonerSearch(String nickname) {
        SummonerDTO summonerDTO = restTemplate.getForObject(ApiCommon.SummonerUrl + nickname + ApiCommon.ApiKey, SummonerDTO.class);
        SummonerId = summonerDTO.getId();
        Puuid = summonerDTO.getPuuid();
        summonerName = summonerDTO.getName();

        log.info("summoner = {}", summonerDTO);
        log.info("summonerLevel = {}", summonerDTO.getSummonerLevel());
        log.info("summonerAccountId = {}", summonerDTO.getAccountId());
        log.info("summonerName = {}", summonerDTO.getName());
    }
    /**
     * 내부 함수
     * getMatchId()
     * 매치 id를 가져온다
     * @return List : 매치ID 리스트
     */
    @Override
    public List getMatchId() {
        log.info("getMatchId BC 서비스 실행!");
        List<String> matchIdList = restTemplate.getForObject(ApiCommon.MatchIdUrl + Puuid +"/ids"+ ApiCommon.ApiKey, List.class);
        return matchIdList;
    }

    /**
     * 내부 함수
     * leagueSearch()
     * 해당 소환사의 리그 정보를 가져온다
     * @return List : 리그정보
     */
    @Override
    public List leagueSearch() {
        log.info("leagueSearch BC 서비스 실행!");
        List<LeagueEntryDTO> leagueEntryDTO = restTemplate.getForObject(ApiCommon.SummonerInfoUrl + SummonerId + ApiCommon.ApiKey, List.class);
        return leagueEntryDTO;
    }

    /**
     * 내부 함수
     * matchSearch()
     * 매치 정보를 가져온다
     * @return List : 매치정보
     */
    @Override
    public List matchSearch() {
        log.info("matchSearch BC 서비스 실행!");
        List matchId = getMatchId();
        List<MatchDto> matchDtoList = new ArrayList<>();
        MatchDto matchDto = null;

        //MatchCnt 만큼 매치정보를 가져오기
        for(int i=0;i<ApiCommon.MatchCnt;i++) {
            matchDto = restTemplate.getForObject(ApiCommon.MatchInfoUrl + matchId.get(i) + ApiCommon.ApiKey, MatchDto.class);
            matchDtoList.add(matchDto);
        }
        return matchDtoList;
    }

    /**
     * 내부 함수
     * winLoseing()
     * 연승,연패 구분 값 및 횟수를 가져온다
     * @return Map : winLoseingCnt , winLose
     */
    @Override
    public Map winLoseing() {
        log.info("winLoseing BC 서비스 실행!");
        List matchId = getMatchId();
        List<Boolean> winLoseingList = new ArrayList<Boolean>(); //1게임의 승패들을 담은 리스트
        MatchDto matchDto = null;
        boolean winLoseing = true; //1게임의 승패
        Map result = new HashMap();

        //matchCnt 만큼 승패를 가져와 리스트에 담아주기
        //총 matchCnt 만큼 반복
        for(int i=0;i<ApiCommon.MatchCnt;i++) {
            matchDto = restTemplate.getForObject(ApiCommon.MatchInfoUrl + matchId.get(i) + ApiCommon.ApiKey, MatchDto.class);
            //검색한 닉네임 기준으로 가져오기
            //1게임에서의 반복 (1게임에 10명이 참여함)
            for(int j=0;j<10;j++){
                String nickname = matchDto.getInfo().getParticipants().get(j).getSummonerName();
                if(nickname.equals(summonerName)) {
                    winLoseing = matchDto.getInfo().getParticipants().get(j).isWin();
                    winLoseingList.add(winLoseing);
                }
            }
        }

        boolean firstWinLose = winLoseingList.get(0); //연승 연패는 첫번째 게임 기준이므로 첫번째 게임승패 담아줌
        String winLoseingFlag = "Y"; //연승 연패 끊어질때 사용하는 변수
        int winLoseingCnt = 0; //연승 연패 횟수

        if(firstWinLose){
            MapdataUtil.setString(result,"winLose","연승");
        } else {
            MapdataUtil.setString(result,"winLose","연패");
        }

        //검색한 닉네임 기준으로 가져온 총 matchCnt 만큼의 경기수의 승패 리스트 : winLoseingList
        for(int k=0;k<ApiCommon.MatchCnt-1;k++){
            if(winLoseingList.get(k) == firstWinLose && winLoseingFlag == "Y"){
                MapdataUtil.setInt(result,"winLoseingCnt",++winLoseingCnt);
            } else {
                winLoseingFlag = "N";
            }
        }
        log.info("현재 {}{} 중입니다",winLoseingCnt,result.get("winLose"));
        return result;
    }
}
