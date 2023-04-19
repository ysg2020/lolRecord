package hello.lolRecord.lr.lu.service.impl;

import hello.lolRecord.lr.lu.dto.LOLUserJoinForm;
import hello.lolRecord.lr.lu.dto.LOLUserLoginForm;
import hello.lolRecord.lr.lu.repository.LOLUserRepository;
import hello.lolRecord.lr.lu.service.LOLUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class LOLUserServiceImpl implements LOLUserService{

    private final LOLUserRepository Repository;

    @Override
    public String login(LOLUserLoginForm lolUserLoginForm) throws SQLException {
        String pwd = Repository.findPwd(lolUserLoginForm.getUSER_ID());
        if(lolUserLoginForm.getPWD().equals(pwd)){
            return "success";
        } else{
            return "fail";
        }
    }

    @Override
    public String join(LOLUserJoinForm lolUser) throws SQLException {
        Repository.save(lolUser);
        return "success";
    }
}