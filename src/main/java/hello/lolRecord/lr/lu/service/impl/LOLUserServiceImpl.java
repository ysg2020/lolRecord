package hello.lolRecord.lr.lu.service.impl;

import hello.lolRecord.lr.lu.dto.LOLUserJoinForm;
import hello.lolRecord.lr.lu.dto.LOLUserLoginForm;
import hello.lolRecord.lr.lu.mapper.LOLUserMapper;
import hello.lolRecord.lr.lu.repository.LOLUserMybatisRepository;
import hello.lolRecord.lr.lu.repository.LOLUserRepository;
import hello.lolRecord.lr.lu.service.LOLUserService;
import hello.lolRecord.lr.sr.service.RecordSearchBCService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.SQLException;
@Slf4j
@Service
@RequiredArgsConstructor
public class LOLUserServiceImpl implements LOLUserService{

    //private final LOLUserRepository Repository;
    private final LOLUserMybatisRepository lolUserMybatisRepository;
    private final RecordSearchBCService recordSearchBCService;

    @Override
    public String login(LOLUserLoginForm lolUserLoginForm) throws SQLException {
        String pwd = lolUserMybatisRepository.findPwd(lolUserLoginForm.getUSER_ID());
        if(lolUserLoginForm.getPWD().equals(pwd)){
            return "success";
        } else{
            return "fail";
        }
    }

    @Override
    public String join(LOLUserJoinForm lolUser) throws SQLException {
        log.info("LOLUserServiceImpl : join");
        String nickCheck = summonerNickCheck(lolUser.getLOL_NICK());
        String nick = lolUserMybatisRepository.findNick(lolUser.getLOL_NICK());
        log.info("nickCheck : {}",nickCheck);
        log.info("nick : {}",nick);
        //롤 닉네임이 존재하고 DB에 닉네임이 없을 경우(이미 등록된 닉네임이 아닐경우)
        if(nickCheck != null && nick == null) {
            lolUserMybatisRepository.save(lolUser);
            return "success";
        }
        return "fail";
    }

    @Override
    public String summonerNickCheck(String paramNick) {
        try{
            return recordSearchBCService.summonerSearch(paramNick);
        }catch (HttpClientErrorException e){
            return null;
        }

    }
}
