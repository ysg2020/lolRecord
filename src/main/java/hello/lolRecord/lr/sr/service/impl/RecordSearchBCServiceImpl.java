package hello.lolRecord.lr.sr.service.impl;

import hello.lolRecord.common.*;
import hello.lolRecord.lr.sr.dto.*;
import hello.lolRecord.lr.sr.service.RecordSearchBCService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RecordSearchBCServiceImpl implements RecordSearchBCService {

    @Value("${riot-api-key}")
    private String ApiKey;
    private final RestTemplate restTemplate;
    private final Logger log = LoggerFactory.getLogger(getClass());

    private String SummonerId = null;
    private String Puuid = null;
    private String summonerName = null;
    private List<LeagueEntryDTO> leagueEntryDTO = new ArrayList<>();
    private List<String> matchIdList = new ArrayList<>();
    private List<MatchDto> matchDtoList = new ArrayList<>();
    private List<ParticipantDto> myMatchDtoList = new ArrayList<>();
    private Map winLoseing = null;
    private List playChampList = new ArrayList<>();

    @Override
    public Map summonerMatchSearch(String nickname) {
        log.info("summonerMatchSearch BC 서비스 실행!");
        log.info("해당 닉네임 최초 전적 조회!");
        Map result = new HashMap();

        //최초 조회시 필수 실행 메소드
        summonerSearch(nickname);
        getMatchId();
        matchSearch();
        
        //최초 조회후 전역변수에 저장
        winLoseing = winLoseing();
        myMatchDtoList = myMatchSearch(nickname);
        playChampList = playChamp();
        
        //리그정보,매치정보 각각 담아주기
        result.put("summonerSearch",leagueSearch());
        result.put("matchSearchDtl",null);
        result.put("myMatchSearch",myMatchDtoList);
        result.put("winLoseing",winLoseing);
        result.put("playChamp",playChampList);
        result.put("championName",null);
        return result;
    }
    @Override
    public Map summonerMatchSearch(String nickname , String championName) {
        log.info("summonerMatchSearch(championName)  BC 서비스 실행!");
        Map result = new HashMap();
        
        //리그정보,매치정보 각각 담아주기
        result.put("summonerSearch",leagueEntryDTO);
        result.put("matchSearchDtl",null);
        result.put("myMatchSearch",mymatchSearchChamp(nickname,championName));
        result.put("winLoseing",winLoseing);
        result.put("playChamp",playChampList);
        result.put("championName",championName);

        return result;
    }
    @Override
    public Map summonerMatchSearchDtl(String nickname ,int matchNum) {
        log.info("summonerMatchSearchDtl  BC 서비스 실행!");
        Map result = new HashMap();

        //리그정보,매치정보 각각 담아주기
        result.put("summonerSearch",leagueEntryDTO);
        result.put("myMatchSearch",myMatchDtoList);
        result.put("matchSearchDtl",matchSearchDtl(nickname,matchNum));
        result.put("winLoseing",winLoseing);
        result.put("top3",top3(matchNum));
        result.put("playChamp",playChampList);
        result.put("championName",null);

        return result;
    }
    @Override
    public Map summonerMatchSearchDtl(String nickname ,String championName,int matchNum) {
        log.info("summonerMatchSearchDtl  BC 서비스 실행!");
        Map result = new HashMap();
        Map rResult = new HashMap();
        //리그정보,매치정보 각각 담아주기
        result.put("summonerSearch",leagueEntryDTO);
        result.put("myMatchSearch",mymatchSearchChamp(nickname,championName));
        result.put("matchSearchDtl",matchSearchChampDtl(nickname,championName,matchNum));
        result.put("winLoseing",winLoseing);
        result.put("top3",top3(matchNum));
        result.put("playChamp",playChamp());
        result.put("championName",championName);

        return result;
    }
    /**
     * 내부 함수
     * matchSearch()
     * match Num번쨰 매치 정보를 가져온다
     * @return List : 매치정보
     */
    @Override
    public Map matchSearchDtl(String nickname,int matchNum) {
        log.info("matchSearchDtl BC 서비스 실행!");
        //List matchId = getMatchId();
        Map result = new HashMap();
        //MatchDto matchDtoDtl = restTemplate.getForObject(ApiCommon.MatchInfoUrl + matchIdList.get(matchNum) + ApiCommon.ApiKey, MatchDto.class);
        MatchDto matchDtoDtl = matchDtoList.get(matchNum);
        result.put("matchDtoDtl", matchDtoDtl);
        return result;
    }

    /**
     * 내부 함수
     * matchSearch()
     * matchChamp Num번쨰 매치 정보를 가져온다
     * @return List : 매치정보
     */
    @Override
    public Map matchSearchChampDtl(String nickname,String championName,int matchNum) {
        log.info("matchSearchChampDtl BC 서비스 실행!");
        //List matchId = getMatchId();
        Map result = new HashMap();
        //MatchDto matchDtoDtl = restTemplate.getForObject(ApiCommon.MatchInfoUrl + matchIdList.get(matchNum) + ApiCommon.ApiKey, MatchDto.class);
        MatchDto matchDto = (MatchDto) matchSearchChamp(nickname, championName).get(matchNum);
        result.put("matchDtoDtl",matchDto);
        return result;
    }

    /**
     * 내부 함수
     * summonerSearch()
     * 소환사 정보나 경기 정보를 가져오기 위한 ID (API 요청에 필요한 ID)들을 가져온다
     * @return void
     */

    @Override
    public String summonerSearch(String nickname) throws HttpClientErrorException {
        log.info("summonerSearch BC 서비스 실행! (API 요청)");
        SummonerDTO summonerDTO = restTemplate.getForObject(ApiCommon.SummonerUrl + nickname + ApiKey, SummonerDTO.class);
        SummonerId = summonerDTO.getId();
        Puuid = summonerDTO.getPuuid();
        summonerName = summonerDTO.getName();

        log.info("summoner = {}", summonerDTO);
        log.info("summonerLevel = {}", summonerDTO.getSummonerLevel());
        log.info("summonerAccountId = {}", summonerDTO.getAccountId());
        log.info("summonerName = {}", summonerDTO.getName());

        return summonerName;
    }
    /**
     * 내부 함수
     * getMatchId()
     * 매치 id를 가져온다
     * @return List : 매치ID 리스트
     */
    @Override
    public List getMatchId() {
        log.info("getMatchId BC 서비스 실행! (API 요청)");
        matchIdList = restTemplate.getForObject(ApiCommon.MatchIdUrl + Puuid +"/ids"+ ApiKey, List.class);
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
        log.info("leagueSearch BC 서비스 실행! (API 요청)");
        leagueEntryDTO = restTemplate.getForObject(ApiCommon.SummonerInfoUrl + SummonerId + ApiKey, List.class);
        return leagueEntryDTO;
    }

    /**
     * 내부 함수
     * matchSearch()
     * 모든 참여자 매치 정보를 가져온다
     * @return List : 매치정보
     */
    @Override
    public List matchSearch() {
        log.info("matchSearch BC 서비스 실행! (API 요청)");
        //List matchId = getMatchId();
        matchDtoList = new ArrayList<>();
        MatchDto matchDto = null;

        //MatchCnt 만큼 매치정보를 가져오기
        for(int i=0;i<ApiCommon.MatchCnt;i++) {
            matchDto = restTemplate.getForObject(ApiCommon.MatchInfoUrl + matchIdList.get(i) + ApiKey, MatchDto.class);
            matchDtoList.add(matchDto);
        }
        return matchDtoList;
    }
    /**
     * 내부 함수
     * myMatchSearch (nickname)
     * 검색한 참여자만의 매치 정보를 가져온다
     * <logic>
     *     for 경기 수
     *       for 1경기안에 검색하려는 챔피언과 닉네임 찾기
     *       if 찾아서 있으면 List에 담아줌
     * </logic>
     * @return List : 매치정보
     */
    @Override
    public List myMatchSearch(String nickname) {
        log.info("myMatchSearch BC 서비스 실행!");
        //List matchId = getMatchId();
        List<ParticipantDto> participantDtoList = new ArrayList<>();
        MatchDto matchDto = null;
        //MatchCnt 만큼 매치정보를 가져오기
        for (int i = 0; i < ApiCommon.MatchCnt; i++) {
            //matchDto = restTemplate.getForObject(ApiCommon.MatchInfoUrl + matchIdList.get(i) + ApiCommon.ApiKey, MatchDto.class);
            matchDto = matchDtoList.get(i);
            for (int j = 0; j < 10; j++) {
                if (matchDto.getInfo().getParticipants().get(j).getSummonerName().equals(nickname)) {
                    participantDtoList.add(matchDto.getInfo().getParticipants().get(j));
                }
            }
        }
        return participantDtoList;
    }
    /**
     * 내부 함수
     * mymatchSearchChamp(nickname,championName)
     * 검색한 참여자의 챔피언별 매치 정보만을 가져온다
     * <logic>
     *     for 경기 수
     *       for 1경기안에 검색하려는 챔피언과 닉네임 찾기
     *       if 찾아서 있으면 List에 담아줌
     * </logic>
     * @return List : 매치정보
     */
    @Override
    public List mymatchSearchChamp(String nickname ,String championName) {
        log.info("mymatchSearchChamp(championName) BC 서비스 실행!");
        //List matchId = getMatchId();
        List<ParticipantDto> participantDtoList = new ArrayList<>();
        MatchDto matchDto = null;

        //MatchCnt 만큼 매치정보를 가져오기
        for (int i = 0; i < ApiCommon.MatchCnt; i++) {
            //matchDto = restTemplate.getForObject(ApiCommon.MatchInfoUrl + matchIdList.get(i) + ApiCommon.ApiKey, MatchDto.class);
            matchDto = matchDtoList.get(i);
            for (int j = 0; j < 10; j++) {
                if (matchDto.getInfo().getParticipants().get(j).getChampionName().equals(championName)
                        && matchDto.getInfo().getParticipants().get(j).getSummonerName().equals(nickname)) {
                    participantDtoList.add(matchDto.getInfo().getParticipants().get(j));
                }
            }
        }
        return participantDtoList;
    }
    /**
     * 내부 함수
     * matchSearchChamp(nickname,championName)
     * 검색한 참여자의 챔피언별 모든 매치 정보를 가져온다
     * <logic>
     *     for 경기 수
     *       for 1경기안에 검색하려는 챔피언과 닉네임 찾기
     *       if 찾아서 있으면 List에 담아줌
     * </logic>
     * @return List : 매치정보
     */
    @Override
    public List matchSearchChamp(String nickname ,String championName) {
        log.info("matchSearchChamp(championName) BC 서비스 실행!");
        //List matchId = getMatchId();
        List<MatchDto> matchDtoChampList = new ArrayList<>();
        MatchDto matchDto = null;

        //MatchCnt 만큼 매치정보를 가져오기

        for (int i = 0; i < ApiCommon.MatchCnt; i++) {
            //matchDto = restTemplate.getForObject(ApiCommon.MatchInfoUrl + matchIdList.get(i) + ApiCommon.ApiKey, MatchDto.class);
            matchDto = matchDtoList.get(i);
            for (int j = 0; j < 10; j++) {
                if (matchDto.getInfo().getParticipants().get(j).getChampionName().equals(championName)
                        && matchDto.getInfo().getParticipants().get(j).getSummonerName().equals(nickname)) {
                    matchDtoChampList.add(matchDto);
                }
            }
        }

        return matchDtoChampList;
    }
    /**
     * 내부 함수
     * winLoseing()
     * <logic>
     *     총 게임수 반복
     *     for{
     *          검색한 닉네임기준으로 가져오기 위한 반복
     *          for{
     *              검색한 닉네임의 승패 리스트 생성
     *          }
     *     }
     *
     *     ...
     *
     *     for{
     *        만든 승패 리스트로  첫번째 승패 여부와 비교 후 cnt 리턴
     *     }
     * </logic>
     * 연승,연패 구분 값 및 횟수를 가져온다
     * @return Map : winLoseingCnt , winLose
     */
    @Override
    public Map winLoseing() {
        log.info("winLoseing BC 서비스 실행!");
        //전체 매치 정보
        //List<MatchDto> matchDtoList = matchSearch();
        List<ParticipantDto> participants;

        //검색한 닉네임 기준으로 matchCnt 만큼의 승패 리스트
        List<Boolean> winLoseingList = new ArrayList<Boolean>();

        Map result = new HashMap();

        //matchCnt 만큼 반복해 승패를 가져와 리스트에 담아주기
        for (int i = 0; i < ApiCommon.MatchCnt; i++) {
            participants = matchDtoList.get(i).getInfo().getParticipants();
            //검색한 닉네임 기준으로 가져오기 위한 반복 (1게임에 10명이 참여함)
            for (int j = 0; j < 10; j++) {
                String nickname = participants.get(j).getSummonerName();
                if (nickname.equals(summonerName)) {
                    winLoseingList.add(participants.get(j).isWin());
                }
            }
        }
        //연승 연패는 첫번째 게임 기준이므로 첫번째 게임승패 담아줌
        boolean firstWinLose = winLoseingList.get(0);

        //연승 연패 끊어질때 사용하는 변수
        String winLoseingFlag = "Y";

        //연승 연패 횟수
        int winLoseingCnt = 0;

        if (firstWinLose) {
            MapdataUtil.setString(result, "winLose", "연승");
        } else {
            MapdataUtil.setString(result, "winLose", "연패");
        }

        //검색한 닉네임 기준으로 가져온 총 matchCnt 만큼의 경기수의 승패 리스트 : winLoseingList
        for (int k = 0; k < ApiCommon.MatchCnt - 1; k++) {
            if (winLoseingList.get(k) == firstWinLose && winLoseingFlag == "Y") {
                MapdataUtil.setInt(result, "winLoseingCnt", ++winLoseingCnt);
            } else {
                winLoseingFlag = "N";
            }
        }
        log.info("현재 {}{} 중입니다", winLoseingCnt, result.get("winLose"));
        return result;
    }

    /**
     * 준 데미지 TOP3를 가져온다.
     * <logic>
     *     총 게임 수 반복
     *     for{
     *         1게임마다 변수 초기화
     *         ...
     *         for{
     *              1명마다 변수 초기화
     *              데미지와 그 데미지에 해당하는 닉네임만 뽑아 담은 리스트 생성
     *         }
     *
     *         만든 리스트를 데미지 기준으로 오름차순 정렬
     *         오름차순 정렬된 리스트의 맨 마지막이 1위
     *         Map result의 value값으로 Map bizOutput을 1게임마다 각각 담아줌
     *     }
     *     result 리턴
     * </logic>
     * @return : Map
     *          ex)
     *          "matchDamegeTOP3::1": {
     *                 "summoner2ndDamege": "qqqbwwedqwed",
     *                 "Damege1st": 37853,
     *                 "Damege3rd": 26986,
     *                 "summoner3rdDamege": "징징이는징징징징",
     *                 "summoner1stDamege": "T1 Gumayusi",
     *                 "Damege2nd": 36912
     *             }
     */
    @Override
    public Map top3(int matchNum) {
        log.info("top3 BC 서비스 실행!");
        //전체 매치 정보
        //List<MatchDto> matchDtoList = matchSearch();
        List<ParticipantDto> participants;

        //전체 매치 정보에서 데미지와 그데미지에 해당하는 닉네임만 뽑아 담은 리스트
        List<Top3Dto> damegeList;

        Map bizOutput;
        Top3Dto top3Dto;


        //1게임마다 초기화
        damegeList = new ArrayList<Top3Dto>();
        bizOutput = new HashMap();

        //매치(참여자들) 정보
        if(matchDtoList.size() != 0) {
            participants = matchDtoList.get(matchNum).getInfo().getParticipants();

            //1게임에 10명 참여
            for (int j = 0; j < 10; j++) {
                top3Dto = new Top3Dto();
                top3Dto.setDamege(participants.get(j).getTotalDamageDealtToChampions());
                top3Dto.setSummonerName(participants.get(j).getSummonerName());
                damegeList.add(top3Dto);

            }
        }

        //데미지 기준으로 오름차순 정렬
        damegeList.sort(Comparator.comparing((Top3Dto top3DtoSort) -> (Integer) top3DtoSort.getDamege()));
        //1위
        MapdataUtil.setInt(bizOutput,"Damege1st",damegeList.get(9).getDamege());
        MapdataUtil.setString(bizOutput,"summoner1stDamege",damegeList.get(9).getSummonerName());
        //2위
        MapdataUtil.setInt(bizOutput,"Damege2nd",damegeList.get(8).getDamege());
        MapdataUtil.setString(bizOutput,"summoner2ndDamege",damegeList.get(8).getSummonerName());
        //3위
        MapdataUtil.setInt(bizOutput,"Damege3rd",damegeList.get(7).getDamege());
        MapdataUtil.setString(bizOutput,"summoner3rdDamege",damegeList.get(7).getSummonerName());

        return bizOutput;
    }

    /**
     * 내부 함수
     * playChamp()
     * 검색한 참여자가 플레이한 챔피언 목록을 가져온다.
     * @return List : 플레이한 챔피언리스트
     */
    @Override
    public List playChamp() {
        LinkedHashSet playChamp = new LinkedHashSet();
        playChamp.add("-전체-");
        for(int i=0;i<ApiCommon.MatchCnt;i++){
            playChamp.add(myMatchDtoList.get(i).getChampionName());
        }
        List listPlayChamp = List.of(playChamp.toArray());
        return listPlayChamp;
    }
}
