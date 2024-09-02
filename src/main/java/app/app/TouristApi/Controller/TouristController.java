package app.app.TouristApi.Controller;

import app.app.TouristApi.DTO.TouristInfoDTO;
import app.app.TouristApi.DTO.TouristInfoWithAccessibilityDTO;
import app.app.TouristApi.Service.TouristApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/touristSpot")
public class TouristController {

    private final TouristApiService touristApiService;

    public TouristController(TouristApiService touristApiService) {
        this.touristApiService = touristApiService;
    }

    /*// 기존 코드: 지역코드와 시군구코드를 직접 입력받는 엔드포인트
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
    }*/

    @GetMapping("/tourist-info")
    public String getTouristInfoPage() {
        return "touristSpot/tourist-info"; // templates/touristSpot/tourist-info.html로 맵핑
    }

    // 새로운 코드: 지역명을 입력받아 지역코드와 시군구코드를 매핑해주는 엔드포인트
    @GetMapping("/api/tourist-info")
    public ResponseEntity<List<TouristInfoDTO>> getTouristInfo(
            @RequestParam("region") String region,
            @RequestParam("sigungu") String sigungu,
            @RequestParam("contentTypeId") int contentTypeId) {

        String jsonResponse = touristApiService.getTouristDataByRegionAndSigungu(contentTypeId, region, sigungu);

        if (jsonResponse != null) {
            List<TouristInfoDTO> touristInfoList = parseTouristInfo(jsonResponse);
            return ResponseEntity.ok(touristInfoList);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private List<TouristInfoDTO> parseTouristInfo(String jsonResponse) {
        // JSON 응답을 TouristInfoDTO 리스트로 변환하는 로직 구현
        // 예시로 ObjectMapper를 사용하여 JSON을 Java 객체로 변환합니다.
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");

            List<TouristInfoDTO> touristInfoList = new ArrayList<>();
            for (JsonNode itemNode : itemsNode) {
                TouristInfoDTO info = objectMapper.treeToValue(itemNode, TouristInfoDTO.class);
                touristInfoList.add(info);
            }
            return touristInfoList;

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @GetMapping("/tourist-information")
    public TouristInfoWithAccessibilityDTO getTouristInformation(
            @RequestParam String contentId,
            @RequestParam String contentTypeId) {

        return touristApiService.getTouristInfoWithAccessibility(contentId, contentTypeId);
    }

    @GetMapping("/fetch-tourist-data")
    public String fetchTouristData() {
        touristApiService.fetchAndSaveTouristData();
        return "Tourist data fetching and saving initiated.";
    }

    // 새로운 엔드포인트: 무장애 정보 가져오기 및 저장하기
    @GetMapping("/fetch-accessible-info")
    public String fetchAndSaveAccessibleInfo() {
        touristApiService.fetchAndSaveAccessibilityInfo();
        return "Accessible info fetching and saving initiated.";
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
