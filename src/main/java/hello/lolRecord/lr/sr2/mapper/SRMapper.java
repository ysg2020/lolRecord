package hello.lolRecord.lr.sr2.mapper;

import hello.lolRecord.lr.sr2.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SRMapper {

    SummonerDTO getSmr(String nickName);
    SummonerDTO getSmrUserID(int user_no);
    void setSmr(SummonerDTO summonerDTO);
    void editSmr(SummonerDTO summonerDTO);
    List<String> getMatch(String puu_id);
    void setMatch(SummonerDTO summonerDTO);
    void removeMatch(String puu_id);
    List<ParticipantDto> getPtip(String match_id);
    List<ParticipantDto> getMyPtip(String puu_id);
    void setPtip(ParticipantDto ptipInfo);
    void removePtip(String match_id);
    LeagueEntryDTO getLeague(String smr_id);
    void setLeague(List<LeagueEntryDTO> leagueInfo);
    void editLeague(List<LeagueEntryDTO> leagueInfo);




}
