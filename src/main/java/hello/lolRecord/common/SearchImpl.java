package hello.lolRecord.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.lolRecord.lr.sr.service.RecordSearchBCService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class SearchImpl implements Search {


    public void basicSearch(){


    }
}
