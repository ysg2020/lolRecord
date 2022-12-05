package hello.lolRecord.lr.sr.service.impl;

import hello.lolRecord.lr.sr.service.RecordSearchBCService;
import hello.lolRecord.lr.sr.service.RecordSearchSCService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecordSearchSCServiceImpl implements RecordSearchSCService {

    private final RecordSearchBCService recordSearchBCService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public Map summonerSearch(String nickname) {
        Map result = new HashMap();
        result.put("result",recordSearchBCService.summonerSearch(nickname));
        return result;
    }
    @Override
    public Map summonerInfoSearch() {
        log.info("summonerInfoSearch SC 서비스 실행!");
        Map result = new HashMap();
        result.put("result",recordSearchBCService.summonerInfoSearch());
        return result;
    }
}
