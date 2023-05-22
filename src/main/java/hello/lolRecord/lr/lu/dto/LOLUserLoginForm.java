package hello.lolRecord.lr.lu.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Slf4j
public class LOLUserLoginForm {

    @NotBlank(message = "아이디 공백")
    private String USER_ID;

    @NotBlank(message = "비밀번호 공백")
    private String PWD;

    public LOLUserLoginForm() {
        log.info("로그인 폼 생성자 실행!!");
    }
}
