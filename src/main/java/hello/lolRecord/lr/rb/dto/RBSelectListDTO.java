package hello.lolRecord.lr.rb.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RBSelectListDTO {

    private int ROW_NUM;
    private int TOT_NUM;
    private int BOARD_ID;
    private String TITLE;
    private String WRITER;
    private String WRITE_DATE;
    private int VIEW;
    private int GOOD;


}
