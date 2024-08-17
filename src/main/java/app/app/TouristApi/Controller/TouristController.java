package app.app.TouristApi.Controller;

import app.app.TouristApi.DTO.TouristDetailDTO;
import app.app.TouristApi.DTO.TouristRequestDTO;
import app.app.TouristApi.Service.TouristApiService;
import app.app.TouristApi.Service.TouristService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TouristController {

    private final TouristApiService touristApiService;
    private final TouristService touristService;

    public TouristController(TouristApiService touristApiService, TouristService touristService) {
        this.touristApiService = touristApiService;
        this.touristService = touristService;
    }

    // 기존 코드: 지역코드와 시군구코드를 직접 입력받는 엔드포인트
    @PostMapping("/tourist-info")
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
    @PostMapping("/tourist-info-by-name")
    public ResponseEntity<String> getTouristInfoByRegionAndSigungu(@RequestBody TouristRequestDTO touristRequest) {
        String touristData = touristApiService.getTouristDataByRegionAndSigungu(
                touristRequest.getContentTypeId(),
                touristRequest.getRegionName(),
                touristRequest.getSigunguName()
        );

        if (touristData != null) {
            return ResponseEntity.ok(touristData);
        } else {
            return ResponseEntity.status(500).body("Failed to retrieve tourist information.");
        }
    }

    @GetMapping("/tourist-info/{contentId}/details")
    public ResponseEntity<TouristDetailDTO> getTouristAndAccessibleInfo(
            @PathVariable String contentId,
            @RequestParam int contentTypeId) {

        TouristDetailDTO details = touristService.getTouristAndAccessibleInfo(contentId, contentTypeId);
        return ResponseEntity.ok(details);
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
