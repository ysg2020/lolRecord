package hello.lolRecord.lr.lu.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LOLUserLoginForm {

    @NotBlank
    private String USER_ID;

    @NotBlank
    private String PWD;
}
