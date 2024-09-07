package app.app.TouristApi.Controller;

import app.app.TouristApi.DTO.TouristInfoDTO;
import app.app.TouristApi.Entity.AccessibleInfo;
import app.app.TouristApi.Service.TouristApiService;
import app.app.TouristApi.Service.TouristSpotService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TouristAccessibleController {
    private final TouristApiService touristApiService;
    private final TouristSpotService touristSpotService;

    public TouristAccessibleController(TouristApiService touristApiService, TouristSpotService touristSpotService) {
        this.touristApiService = touristApiService;
        this.touristSpotService = touristSpotService;
    }

    // 무장애 관광지 필터링 API 엔드포인트
    @GetMapping("/tourist-accessible-info")
    public ResponseEntity<List<TouristInfoDTO>> getTouristAccessibleInfo(
            @RequestParam("region") String region,  // region 등 제거를 원하면 이 부분도 제거 가능
            @RequestParam("sigungu") String sigungu,  // 필요 없으면 제거 가능
            @RequestParam("contentTypeId") int contentTypeId,  // 필요 없으면 제거 가능
            @RequestParam(value = "accessibleType", required = false) String accessibleType) {

        // 1. 외부 API로부터 관광지 목록 가져오기
        String jsonResponse = touristApiService.getTouristDataByRegionAndSigungu(contentTypeId, region, sigungu);

        if (jsonResponse != null) {
            // 2. 관광지 목록에서 contentId 추출
            List<TouristInfoDTO> touristInfoList = parseTouristInfo(jsonResponse);
            List<String> contentIds = touristInfoList.stream()
                    .map(TouristInfoDTO::getContentId)
                    .collect(Collectors.toList());

            // 3. DB에서 무장애 정보가 있는 관광지 필터링 (region/sigungu 대신 contentId 기반)
            List<String> accessibleTypeList = accessibleType != null ? Arrays.asList(accessibleType.split(",")) : new ArrayList<>();
            List<AccessibleInfo> accessibleInfoList = touristSpotService.searchTouristSpotsByAccessibility(contentIds, accessibleTypeList);
            // **디버깅 코드 추가**
            System.out.println("Accessible Types: " + accessibleTypeList);
            System.out.println("Content IDs: " + contentIds);
            // 4. 필터링된 관광지 목록 반환
            List<TouristInfoDTO> filteredTouristInfo = touristInfoList.stream()
                    .filter(touristInfo -> accessibleInfoList.stream()
                            .anyMatch(accessibleInfo -> accessibleInfo.getContentId().equals(touristInfo.getContentId())))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(filteredTouristInfo);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 관광지 정보를 JSON 응답에서 DTO 리스트로 변환하는 헬퍼 메서드
    private List<TouristInfoDTO> parseTouristInfo(String jsonResponse) {
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
            return new ArrayList<>();
        }
    }
}
