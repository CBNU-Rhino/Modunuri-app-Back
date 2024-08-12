package app.app.TouristApi.Service;

import app.app.TouristApi.DTO.TouristResponseDTO;
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
import java.util.List;

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
        this.serviceKey = "2Gg03XhptQP5XMHb41ax7xhXwo/6CYGp32tip9JD8HXBj067jjfvAXLLmXbN0H2EqGFoM2NXoUdCVCL6mb7I2Q==";
    }

    //
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
    public void saveTouristInfo(String apiUrl) {
        // API 호출 및 JSON 응답을 TouristResponseDTO로 매핑
        TouristResponseDTO responseDTO = restTemplate.getForObject(apiUrl, TouristResponseDTO.class);

        if (responseDTO != null && responseDTO.getResponse().getBody() != null) {
            List<TouristResponseDTO.Response.Body.Items.Item> items = responseDTO.getResponse().getBody().getItems().getItem();

            for (TouristResponseDTO.Response.Body.Items.Item item : items) {
                TouristInfo touristInfo = new TouristInfo();
                touristInfo.setAddr1(item.getAddr1());
                touristInfo.setAddr2(item.getAddr2());
                touristInfo.setAreaCode(item.getAreaCode());
                touristInfo.setBookTour(item.getBookTour());
                touristInfo.setCat1(item.getCat1());
                touristInfo.setCat2(item.getCat2());
                touristInfo.setCat3(item.getCat3());
                touristInfo.setContentId(item.getContentId());
                touristInfo.setContentTypeId(item.getContentTypeId());
                touristInfo.setCreatedTime(item.getCreatedTime());
                touristInfo.setFirstImage(item.getFirstImage());
                touristInfo.setFirstImage2(item.getFirstImage2());
                touristInfo.setMapX(item.getMapX());
                touristInfo.setMapY(item.getMapY());
                touristInfo.setMLevel(item.getMLevel());
                touristInfo.setModifiedTime(item.getModifiedTime());
                touristInfo.setSigunguCode(item.getSigunguCode());
                touristInfo.setTel(item.getTel());
                touristInfo.setTitle(item.getTitle());
                touristInfo.setZipCode(item.getZipCode());

                // TouristInfo 엔티티를 데이터베이스에 저장
                touristInfoRepository.save(touristInfo);
            }
        } else {
            logger.error("Failed to retrieve data from API");
        }
    }
}
