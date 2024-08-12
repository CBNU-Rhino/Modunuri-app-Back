package app.app.TouristApi.Service;

import app.app.Code.Area;
import app.app.TouristApi.DTO.TouristResponseDTO;
import app.app.TouristApi.Entity.TouristInfo;
import app.app.TouristApi.Repository.TouristInfoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
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
    private final String serviceKey = "fHhnNwA7fGBGdq%2FTX99FNNLQJh6pa3CQTHUPpKpk%2FyNHVqEzIDueYm2EKXOq7%2BfjY4fS4KpjCEQBoG3oQ0tTaQ%3D%3D"; // 실제 API 키로 변경

    public TouristApiService(RestTemplate restTemplate, TouristInfoRepository touristInfoRepository, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.touristInfoRepository = touristInfoRepository;
        this.objectMapper = objectMapper;
    }

    //
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

    /**
     * 지역명으로 Area 객체를 찾는 메서드
     * @param regionName 지역명
     * @return 지역에 해당하는 Area 객체
     */
    public Area getAreaByRegionName(String regionName) {
        for (Area area : Area.values()) {
            if (area.getRegionName().equalsIgnoreCase(regionName)) {
                return area;
            }
        }
        return null; // 지역명을 찾지 못한 경우
    }

    /**
     * 지역명으로 관광지 정보를 가져오는 메서드
     * @param regionName 지역명
     * @param contentTypeId 콘텐츠 타입 ID
     * @return 관광지 정보 JSON 문자열
     */
    public String getTouristDataByRegionName(String regionName, int contentTypeId) {
        try {
            Area area = getAreaByRegionName(regionName);
            if (area == null) {
                logger.error("Invalid region name: " + regionName);
                return null;
            }

            int areaCode = area.getRegionCode();
            int sigunguCode = Integer.parseInt(area.getSigunguCode());

            // 이미 areaCode와 sigunguCode가 있으므로, 이를 사용해 관광지 데이터를 가져옴
            return getTouristData(contentTypeId, areaCode, sigunguCode);

        } catch (Exception e) {
            logger.error("Error fetching tourist data by region name", e);
            return null;
        }
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
