package hello.lolRecord.lr.sr.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InfoDto {

    public String gameMode;
    public String gameName;
    public String gameStartTimestamp;
    public List<ParticipantDto> participants;
}
