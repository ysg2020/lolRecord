package hello.lolRecord.lr.lu.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LOLUserJoinForm {

    @NotBlank
    private String USER_ID;

    @NotBlank
    private String PWD;

    private String BIRTH;
    private String PHONE;
    private String ADDRESS;

    @NotBlank
    private String GENDER;

    @NotBlank
    private String LOL_NICK;

    @NotBlank
    private String MAIN_POSITION;

    private String SUB_POSITION;

}
