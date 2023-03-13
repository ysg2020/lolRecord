package hello.lolRecord.lr.sr.service;

import java.util.Map;

public interface RecordSearchSCService {

    public Map summonerMatchSearch(String nickname);
    public Map summonerMatchSearch(String nickname,String championName);
    public Map summonerMatchSearchDtl(String nickname,int matchNum);
    public Map summonerMatchSearchDtl(String nickname,String championName,int matchNum);

}
