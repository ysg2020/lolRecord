package hello.lolRecord.lr.sr2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipantDto {

    private String puuid;
    private String matchId;
    private String summonerName;
    private String lane;
    private String championName;
    private int kills;
    private int assists;
    private int deaths;
    private boolean win;
    private int totalDamageDealtToChampions;
    private int totalDamageTaken;
    private int goldEarned;
    private int totalMinionsKilled;
    private int neutralMinionsKilled;


}
