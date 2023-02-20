package hello.lolRecord.lr.sr.service;

import java.util.Map;

public interface RecordSearchSCService {

    public Map summonerMatchSearch(String nickname);
    public Map summonerMatchSearch(String nickname,String championName);

}
