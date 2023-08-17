package hello.lolRecord.lr.sr2.service.impl;

import hello.lolRecord.lr.lu.repository.LOLUserMybatisRepository;
import hello.lolRecord.lr.sr2.dto.*;
import hello.lolRecord.lr.sr2.repository.SRRepository;
import hello.lolRecord.lr.sr2.service.MatchService;
import hello.lolRecord.lr.sr2.service.SRService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final SRRepository srRepository;
    private final LOLUserMybatisRepository lolUserMybatisRepository;

    @Override
    public void addMatch(String nickName,String user_id) {
        log.info("SRServiceImpl : matchSearch");
        //소환사,리그정보 갱신 로직
        //1. 검색하려는 롤 닉네임을 통해 소환사 정보가 저장(존재하는지) 되어있는지 체크
        //1-1. 있는 경우 DB에서 조회
        SummonerDTO Smr = srRepository.getSmr(nickName);
        //1-2. 갱신할 소환사,리그정보들을 가져온다.
        SummonerDTO SmrAPI = srRepository.getSmrAPI(nickName);
        List<LeagueEntryDTO> leagueAPI = srRepository.getLeagueAPI(SmrAPI.getId());
        //1-3. 로그인한 유저의 롤 닉네임이 검색한 닉네임과 같으면 유저 ID값 세팅해준다.
        if(lolUserMybatisRepository.findLOLNick(user_id).equals(nickName)){
            SmrAPI.setUserId(user_id);
        }
        //1-4. DB에 저장 (insert / update)
        if(Smr == null){
            log.info("SRServiceImpl : setSmr_start");
            //소환사,리그 정보 insert
            srRepository.setSmr(SmrAPI);
            srRepository.setLeague(leagueAPI);
        } else {
            //소환사,리그 정보 update
            srRepository.editSmr(SmrAPI);
            srRepository.editLeague(leagueAPI);
        }
        //전적 갱신 로직
        //2. 검색하려는 롤 닉네임의 매치 ID들을 가져온다.
        Smr = srRepository.getSmr(nickName);
        String puu_id = Smr.getPuuid();
        List<String> matchId = srRepository.getMatch(puu_id);
        log.info("SRServiceImpl : matchId > {}",matchId);
        //3. 저장되어 있는 매치 ID가 없는경우
        if (matchId.size() == 0){
            log.info("SRServiceImpl : matchId_NULL ");
            //4. 라이엇 API 요청으로 매치 ID를 가져온다.
            List<String> newMatchId = srRepository.getMatchAPI(puu_id);
            //5. 라이엇 API 요청으로 가져온 매치 ID의 갯수 만큼 반복해서 참여자 정보도 라이엇 API 요청후 응답 값을 저장한다.
            for(int i=0;i<newMatchId.size();i++){
                String newMatchIdOne = newMatchId.get(i);
                srRepository.setMatch(Smr,newMatchIdOne);
                srRepository.setPtip(newMatchIdOne);
            }
        //6. *있는경우 매치 새로고침*
        }else {
            List<String> oldMatchId = srRepository.getMatch(puu_id);
            List<String> newMatchId = srRepository.getMatchAPI(puu_id);
            //7. DB에 저장되어있는 match_id의 첫번째 값과 API 응답 값 match_id의 첫번째 값 비교
            //8. 다를경우
            if(!oldMatchId.get(0).equals(newMatchId.get(0))){
                //9. 같아질때까지 반복해서 ID를 찾음
                for(int i=0;i<newMatchId.size();i++){
                    //10. 같은 경우
                    if(oldMatchId.get(0).equals(newMatchId.get(i))){
                        //11. 같아지기 위해 반복한 횟수만큼 새로운 전적 추가
                        for(int j=0;j<i;j++){
                            //매치 ID
                            srRepository.setMatch(Smr,newMatchId.get(j));
                            //참여자 정보
                            srRepository.setPtip(newMatchId.get(j));
                        }
                        //12. 새로운 전적 추가 후 종료
                        return;
                    }
                    //13. 다를 경우 (끝까지 같아지지 않는 경우) 새로운 전적 저장
                    else if(i == newMatchId.size()-1){
                        for(int k =0;i<newMatchId.size();k++){
                            srRepository.setMatch(Smr,newMatchId.get(k));
                            srRepository.setPtip(newMatchId.get(k));
                        }
                    }
                }

            }
            //13. 같은경우 DB에 저장되어있는 전적이 이미 최신 전적
            log.info("같은경우 이미 최신전적");

        }
    }

    @Override
    public Map getMyMatch(String nickName) {
        Map result = new HashMap();
        //1. 검색하려는 롤 닉네임을 통해 소환사 정보가 저장(존재하는지) 되어있는지 체크
        //1-1. 있는 경우 DB에서 조회
        SummonerDTO Smr = srRepository.getSmr(nickName);
        //1-2. 없는 경우 NULL 리턴 (닉네임은 전적 갱신시 필요해서 리턴)
        if(Smr == null){
            result.put("myPtip",null);
            result.put("myLeague",null);
            result.put("nickName",nickName);
            return result;
        }
        //2. 검색하려는 롤 닉네임의 매치 ID들을 가져온다.
        String puu_id = Smr.getPuuid();
        String smr_id = Smr.getId();

        //1. 해당 닉네임 기준의 PTIP 정보를 가져온다
        List<ParticipantDto> myPtip = srRepository.getMyPtip(puu_id);
        result.put("myPtip",myPtip);

        //2. 해당 닉네임의 리그 정보를 가져온다
        LeagueEntryDTO myLeague = srRepository.getLeague(smr_id);
        result.put("myLeague",myLeague);
        return result;
    }

    @Override
    public Map getMatch(String nickName , int matchNum) {
        Map result = new HashMap();
        //1. 검색하려는 롤 닉네임을 통해 소환사 정보가 저장(존재하는지) 되어있는지 체크
        //1-1. 있는 경우 DB에서 조회
        SummonerDTO Smr = srRepository.getSmr(nickName);
        //2. 검색하려는 롤 닉네임의 매치 ID들을 가져온다.
        String puu_id = Smr.getPuuid();
        //1. 해당 닉네임의 매치 ID를 가져온다
        List<String> matchId = srRepository.getMatch(puu_id);
        //2. 해당 닉네임의 매치 ID 리스트에서 matchNum번째인 매치 ID를 가져와 그 매치 ID의 참여자 PTIP들을 가져온다
        List<ParticipantDto> ptip = srRepository.getPtip(matchId.get(matchNum));
        result.put("result",ptip);
        return result;
    }

    @Override
    public boolean summonerCheck(String nickName) {
        try{
            srRepository.getSmrAPI(nickName);
            return true;
        }
        catch (HttpClientErrorException e){
            log.info("MatchServiceImpl : 존재하지 않는 닉네임");
            return false;
        }
    }

    @Override
    public SummonerDTO summonerUserID(String user_id) {
        return  srRepository.getSmrUserID(user_id);

    }

}
