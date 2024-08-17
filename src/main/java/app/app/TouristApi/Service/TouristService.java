package app.app.TouristApi.Service;

import app.app.TouristApi.DTO.*;
import app.app.TouristApi.Entity.AccessibleInfo;
import app.app.TouristApi.Entity.TouristInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

@Service
public class TouristService {

    private final RestTemplate restTemplate;
    private final Jaxb2Marshaller marshaller;
    private final String serviceKey = "fHhnNwA7fGBGdq%2FTX99FNNLQJh6pa3CQTHUPpKpk%2FyNHVqEzIDueYm2EKXOq7%2BfjY4fS4KpjCEQBoG3oQ0tTaQ%3D%3D";

    public TouristService(RestTemplate restTemplate, Jaxb2Marshaller marshaller) {
        this.restTemplate = restTemplate;
        this.marshaller = marshaller;
    }
    private <T> T getApiResponseFromXml(String url, Class<T> responseType) {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String xmlResponse = response.getBody();

        try (StringReader reader = new StringReader(xmlResponse)) {
            return responseType.cast(marshaller.unmarshal(new StreamSource(reader)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public TouristDetailDTO getTouristAndAccessibleInfo(String contentId, int contentTypeId) {
        // 관광지 정보 API 호출
        String touristInfoUrl = String.format(
                "https://apis.data.go.kr/B551011/KorWithService1/detailCommon1?serviceKey=%s&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&contentId=%s&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&_type=xml&contentTypeId=%d",
                serviceKey, contentId, contentTypeId
        );
        ApiResponse touristResponse = getApiResponseFromXml(touristInfoUrl, ApiResponse.class);

        if (touristResponse != null && touristResponse.getBody() != null) {
            Item touristInfoItem = touristResponse.getBody().getItems().getItem().get(0);

            // Item 데이터를 TouristInfo로 변환
            TouristInfo touristInfo = new TouristInfo();
            touristInfo.setContentId(touristInfoItem.getContentid());
            touristInfo.setTitle(touristInfoItem.getTitle());
            touristInfo.setOverview(touristInfoItem.getOverview());
            // 필요한 다른 필드들도 설정

            // 무장애 정보 API 호출
            String accessibleInfoUrl = String.format(
                    "https://apis.data.go.kr/B551011/KorWithService1/detailWithTour1?serviceKey=%s&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&contentId=%s&_type=xml",
                    serviceKey, contentId
            );
            AccessibleApiResponse accessibleResponse = getApiResponseFromXml(accessibleInfoUrl, AccessibleApiResponse.class);

            if (accessibleResponse != null && accessibleResponse.getBody() != null) {
                AccessibleItem accessibleInfoItem = accessibleResponse.getBody().getItems().getItem().get(0);

                // AccessibleItem 데이터를 AccessibleInfo로 변환
                AccessibleInfo accessibleInfo = new AccessibleInfo();
                accessibleInfo.setContentId(accessibleInfoItem.getContentid());
                accessibleInfo.setWheelchair(accessibleInfoItem.getWheelchair());
                // 필요한 다른 필드들도 설정

                // DTO에 설정
                TouristDetailDTO detailDTO = new TouristDetailDTO();
                detailDTO.setTouristInfo(touristInfo);
                detailDTO.setAccessibleInfo(accessibleInfo);

                return detailDTO;
            }
        }

        return null;
    }


}
