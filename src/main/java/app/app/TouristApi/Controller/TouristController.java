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
}
