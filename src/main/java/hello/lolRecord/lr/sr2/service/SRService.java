package hello.lolRecord.lr.sr2.service;

import hello.lolRecord.lr.sr2.dto.GamePtipInfoDto;
import hello.lolRecord.lr.sr2.dto.MatchDto;

import java.util.List;
import java.util.Map;

public interface SRService {

    public Map matchSearch(String nickName);
    public Map matchRefresh(String nickName);
    public Map matchSearchDtl(String nickName, int matchNum);


}