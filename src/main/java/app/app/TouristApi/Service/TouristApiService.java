package app.app.TouristApi.Service;

import app.app.Code.Area;
import app.app.TouristApi.Repository.TouristInfoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class TouristApiService {

    private static final Logger logger = LoggerFactory.getLogger(TouristApiService.class);

    private final RestTemplate restTemplate;
    private final TouristInfoRepository touristInfoRepository;
    private final ObjectMapper objectMapper;
    private final String serviceKey = "fHhnNwA7fGBGdq%2FTX99FNNLQJh6pa3CQTHUPpKpk%2FyNHVqEzIDueYm2EKXOq7%2BfjY4fS4KpjCEQBoG3oQ0tTaQ%3D%3D"; // 실제 API 키로 변경

    @Autowired
    private RegionMappingRepository regionMappingRepository;

    public TouristApiService(RestTemplate restTemplate, TouristInfoRepository touristInfoRepository, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.touristInfoRepository = touristInfoRepository;
        this.objectMapper = objectMapper;
    }

    public String getTouristData(int contentTypeId, int areaCode, int sigunguCode) {
        try {
            String url = String.format(
                    "https://apis.data.go.kr/B551011/KorWithService1/areaBasedList1" +
                            "?serviceKey=%s&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest" +
                            "&listYN=Y&arrange=C&_type=json&contentTypeId=%d&areaCode=%d&sigunguCode=%d",
                    serviceKey, contentTypeId, areaCode, sigunguCode);

            URI uri = new URI(url);
            String jsonResponse = restTemplate.getForObject(uri, String.class);

            if (jsonResponse != null && jsonResponse.startsWith("<")) {
                logger.error("Received an XML response instead of JSON. Likely an API key issue.");
                logger.error("Response: {}", jsonResponse);
                return null;
            }

            return jsonResponse;

        } catch (URISyntaxException e) {
            logger.error("Invalid URI Syntax", e);
            return null;
        } catch (Exception e) {
            logger.error("Error occurred during API request", e);
            return null;
        }
    }

    @PostConstruct
    public void initRegionMapping() {
        for (Area area : Area.values()) {
            RegionMapping regionMapping = new RegionMapping();

            // 지역 코드에서 A를 제외하고 첫 두 개의 숫자를 지역 코드로, 나머지를 시군구 코드로 사용
            String fullCode = area.name().substring(1);
            String regionCode;
            String sigunguCode;

            if (fullCode.length() >= 2) {
                if (fullCode.length() == 3) {
                    // 지역 코드가 두 자리, 시군구 코드가 한 자리인 경우
                    regionCode = fullCode.substring(0, 2); // 첫 두 자리
                    sigunguCode = fullCode.substring(2);  // 마지막 한 자리
                } else {
                    // 지역 코드가 두 자리, 시군구 코드가 여러 자리인 경우
                    regionCode = fullCode.substring(0, 2); // 첫 두 자리
                    sigunguCode = fullCode.substring(2);  // 나머지
                }
            } else {
                regionCode = fullCode.substring(0, 1); // 첫 번째 숫자
                sigunguCode = fullCode.substring(1);  // 나머지 숫자들
            }

            regionMapping.setAreaCode(regionCode);
            regionMapping.setSigunguCode(sigunguCode);
            regionMapping.setRegionName(getRegionNameByCode(regionCode)); // 지역 이름 매핑
            regionMapping.setX(area.getCoordi(area.name())[0]);
            regionMapping.setY(area.getCoordi(area.name())[1]);

            regionMappingRepository.save(regionMapping);
        }
    }

    // 지역 코드를 사용하여 지역 이름을 결정하는 메서드
    private String getRegionNameByCode(String code) {
        switch (code) {
            case "1":
                return "서울특별시";
            case "2":
                return "인천광역시";
            case "3":
                return "대전광역시";
            case "4":
                return "대구광역시";
            case "5":
                return "광주광역시";
            case "6":
                return "부산광역시";
            case "7":
                return "울산광역시";
            case "31":
                return "경기도";
            case "32":
                return "강원도";
            case "33":
                return "충청북도";
            case "34":
                return "충청남도";
            case "35":
                return "경상북도";
            case "36":
                return "경상남도";
            case "37":
                return "전라북도";
            case "38":
                return "전라남도";
            case "39":
                return "제주도";
            default:
                return "알 수 없음"; // 기본값 설정
        }
    }
}