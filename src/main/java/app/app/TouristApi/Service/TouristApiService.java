package app.app.TouristApi.Service;

import app.app.TouristApi.Repository.TouristInfoRepository;
import app.app.TouristApi.TouristInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

@Service
public class TouristApiService {

    private static final Logger logger = LoggerFactory.getLogger(TouristApiService.class);

    private final RestTemplate restTemplate;
    private final TouristInfoRepository touristInfoRepository;
    private final ObjectMapper objectMapper;
    private final String serviceKey;

    public TouristApiService(RestTemplate restTemplate, TouristInfoRepository touristInfoRepository, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.touristInfoRepository = touristInfoRepository;
        this.objectMapper = objectMapper;
        // 서비스 키를 수동으로 인코딩
        this.serviceKey = "fHhnNwA7fGBGdq%2FTX99FNNLQJh6pa3CQTHUPpKpk%2FyNHVqEzIDueYm2EKXOq7%2BfjY4fS4KpjCEQBoG3oQ0tTaQ%3D%3D";
    }

    public String getTouristData(int contentTypeId, int areaCode, int sigunguCode) {
        URI uri = UriComponentsBuilder.fromUriString("https://apis.data.go.kr/B551011/KorWithService1/areaBasedList1")
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", 10)
                .queryParam("pageNo", 1)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "AppTest")
                .queryParam("listYN", "Y")
                .queryParam("arrange", "C")
                .queryParam("_type", "json")
                .queryParam("contentTypeId", contentTypeId)
                .queryParam("areaCode", areaCode)
                .queryParam("sigunguCode", sigunguCode)
                .build()
                .toUri();

        return restTemplate.getForObject(uri, String.class);
    }
}
