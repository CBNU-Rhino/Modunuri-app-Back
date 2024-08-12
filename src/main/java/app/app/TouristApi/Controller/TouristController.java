package app.app.TouristApi.Controller;

import app.app.TouristApi.Service.TouristApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TouristController {

    private final TouristApiService touristApiService;

    public TouristController(TouristApiService touristApiService) {
        this.touristApiService = touristApiService;
    }

    // 기존 코드: 지역코드와 시군구코드를 직접 입력받는 엔드포인트
    @PostMapping("/api/tourist-info")
    public ResponseEntity<String> getTouristInfo(@RequestBody TouristRequest touristRequest) {
        String touristData = touristApiService.getTouristData(
                touristRequest.getContentTypeId(),
                touristRequest.getAreaCode(),
                touristRequest.getSigunguCode()
        );

        if (touristData != null) {
            return ResponseEntity.ok(touristData);
        } else {
            return ResponseEntity.status(500).body("Failed to retrieve tourist information.");
        }
    }

    // 새로운 코드: 지역명을 입력받아 지역코드와 시군구코드를 매핑해주는 엔드포인트
    @PostMapping("/api/tourist-info-by-name")
    public ResponseEntity<String> getTouristInfoByName(@RequestBody TouristRequestByName touristRequest) {
        String touristData = touristApiService.getTouristDataByRegionName(
                touristRequest.getRegionName(),
                touristRequest.getContentTypeId()
        );

        if (touristData != null) {
            return ResponseEntity.ok(touristData);
        } else {
            return ResponseEntity.status(500).body("Failed to retrieve tourist information.");
        }
    }

    // TouristRequest 클래스 정의
    public static class TouristRequest {
        private int contentTypeId;
        private int areaCode;
        private int sigunguCode;

        public int getContentTypeId() {
            return contentTypeId;
        }

        public void setContentTypeId(int contentTypeId) {
            this.contentTypeId = contentTypeId;
        }

        public int getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(int areaCode) {
            this.areaCode = areaCode;
        }

        public int getSigunguCode() {
            return sigunguCode;
        }

        public void setSigunguCode(int sigunguCode) {
            this.sigunguCode = sigunguCode;
        }
    }

    // TouristRequestByName 클래스 정의
    public static class TouristRequestByName {
        private int contentTypeId;
        private String regionName;  // 지역명 필드 추가

        public int getContentTypeId() {
            return contentTypeId;
        }

        public void setContentTypeId(int contentTypeId) {
            this.contentTypeId = contentTypeId;
        }

        public String getRegionName() {
            return regionName;
        }

        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }
    }
}
