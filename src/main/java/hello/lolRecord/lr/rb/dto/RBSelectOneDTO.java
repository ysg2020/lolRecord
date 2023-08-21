package hello.lolRecord.lr.rb.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RBSelectOneDTO {

    private int BOARD_ID;
    private String TITLE;
    private String WRITER;
    private String CONTENTS;
    private String WRITE_DATE;
    private String RPT_NICK;
    private int VIEW;
    private int GOOD;
    private String UD;


}
