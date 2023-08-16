package hello.lolRecord.lr.sr2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeagueEntryDTO {

    private String summonerName;
    private String summonerId;
    private String queueType;
    private String tier;
    private String rank;
    private String leaguePoints;
    private String wins;
    private String losses;


}
