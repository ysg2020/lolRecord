package hello.lolRecord.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipantDto {

    private String summonerName;
    private String lane;
    private int kills;
    private int assists;
    private int deaths;
    private boolean win;


}
