package hello.lolRecord.lr.sr2.repository;

import hello.lolRecord.common.ApiCommon;
import hello.lolRecord.lr.sr2.dto.*;
import hello.lolRecord.lr.sr2.mapper.SRMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SRRepository {
    @Value("${riot-api-key}")
    private String ApiKey;
    private final SRMapper srMapper;
    private final RestTemplate restTemplate;

    public SummonerDTO getSmr(String nickName){
        return srMapper.getSmr(nickName);
    }
    public SummonerDTO getSmrUserID(int user_no){
        return srMapper.getSmrUserID(user_no);
    }
    public SummonerDTO getSmrAPI(String nickName) throws HttpClientErrorException {
        return restTemplate.getForObject(ApiCommon.SummonerUrl + nickName + ApiKey, SummonerDTO.class);
    }
    public void setSmr(SummonerDTO summonerDTO){
        srMapper.setSmr(summonerDTO);
    }

    public void editSmr(SummonerDTO summonerDTO){
        srMapper.editSmr(summonerDTO);
    }
    //MATCH 테이블 select
    public List<String> getMatch(String puu_id){
        return srMapper.getMatch(puu_id);
    }
    //MATCH API 요청
    public List<String> getMatchAPI(String puu_id){
        return restTemplate.getForObject(ApiCommon.MatchIdUrl + puu_id +"/ids"+ ApiKey, List.class);
    }
    //MATCH 테이블 insert
    public void setMatch(SummonerDTO summonerDTO,String match_id){
        summonerDTO.setMatchId(match_id);
        srMapper.setMatch(summonerDTO);
    }

    //MATCH 테이블 delete
    public void removeMatch(String puu_id){
        srMapper.removeMatch(puu_id);
    }

    //PTIP 테이블 select
    public List<ParticipantDto> getPtip(String match_id){
        return srMapper.getPtip(match_id);
    }
    public List<ParticipantDto> getMyPtip(String puu_id){
        return srMapper.getMyPtip(puu_id);
    }

    //PTIP 테이블 insert
    public void setPtip(String match_id){
        MatchDto matchDto = restTemplate.getForObject(ApiCommon.MatchInfoUrl + match_id + ApiKey, MatchDto.class);
        int ptipSize = matchDto.getInfo().getParticipants().size();
        for(int i=0;i<ptipSize;i++){
            ParticipantDto participantDto = matchDto.getInfo().getParticipants().get(i);
            //매치 id는 따로 셋팅
            participantDto.setMatchId(match_id);
            srMapper.setPtip(participantDto);
        }
    }

    //PTIP 테이블 delete
    public void removePtip(String match_id){
        srMapper.removePtip(match_id);
    }

    //LEAGUE 테이블 select
    public LeagueEntryDTO getLeague(String smr_id){
        return srMapper.getLeague(smr_id);
    }

    //LEAGUE API 요청
    public List<LeagueEntryDTO> getLeagueAPI(String smr_id){
        List<LeagueEntryDTO> leagueEntryDTO  =  restTemplate.getForObject(ApiCommon.SummonerInfoUrl + smr_id + ApiKey, List.class);
        return leagueEntryDTO;
    }
    //LEAGUE 테이블 insert
    public void setLeague(List<LeagueEntryDTO> leagueInfo){
        srMapper.setLeague(leagueInfo);

    }
    //LEAGUE 테이블 update
    public void editLeague(List<LeagueEntryDTO> leagueInfo){
        srMapper.editLeague(leagueInfo);

    }

}
