package app.app.TouristApi.Controller;

import app.app.TouristApi.DTO.TouristInfoWithAccessibilityDTO;
import app.app.TouristApi.Entity.AccessibleInfo;
import app.app.TouristApi.Service.TouristApiService;
import app.app.TouristApi.Service.TouristSpotService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/touristSpot")
public class TouristPageController {

    private final TouristApiService touristApiService;
    private final TouristSpotService touristSpotService;  // TouristSpotService 추가

    @Autowired
    public TouristPageController(TouristApiService touristApiService, TouristSpotService touristSpotService) {
        this.touristApiService = touristApiService;
        this.touristSpotService = touristSpotService;  // TouristSpotService 주입
    }

    @GetMapping("/area-search")
    public String getAreaSearchPage() {
        return "touristSpot/Area_Search"; // templates/touristSpot/Area_Search.html로 이동
    }

    @GetMapping("/tourist-information")
    public String getTouristInformation(
            @RequestParam String contentId,
            @RequestParam String contentTypeId,
            Model model) {

        TouristInfoWithAccessibilityDTO touristInfo = touristApiService.getTouristInfoWithAccessibility(contentId, contentTypeId);

        // 데이터를 모델에 추가하여 view로 넘깁니다.
        model.addAttribute("touristInfo", touristInfo);
        return "touristSpot/searchresult"; // searchresult.html로 이동
    }

    @GetMapping("/searchresult.html")
    public String getSearchResultPage(@RequestParam String contentId, Model model) {
        model.addAttribute("contentId", contentId);
        return "touristSpot/searchresult"; // templates/touristSpot/searchresult.html로 이동
    }


    // 무장애 검색 페이지 렌더링
    @GetMapping("/search-by-accessibility")
    public String getBarrierFreeSearchPage() {
        return "touristSpot/BarrierFreeSearch"; // BarrierFreeSearch.html을 렌더링
    }

    // 무장애 조건에 따른 관광지 검색 (AJAX 요청용 API)
    @GetMapping("/search-by-accessibility-api")
    @ResponseBody
    public List<AccessibleInfo> searchByAccessibility(
            @RequestParam(required = false) String region, // 지역 선택
            @RequestParam(required = false) String sigungu, // 시/구/군 선택
            @RequestParam(required = false) String contentTypeId, // 관광지 유형 선택
            @RequestParam(required = false) String accessibleTypes // 무장애 유형 필터링
    ) {
        List<String> accessibleTypeList = new ArrayList<>();

        // accessibleTypes가 null이 아니면 리스트로 변환
        if (accessibleTypes != null && !accessibleTypes.isEmpty()) {
            accessibleTypeList = Arrays.asList(accessibleTypes.split(","));
        }

        // TouristSpotService를 통해 필터링된 결과를 반환
        return touristSpotService.searchTouristSpotsByAccessibility(region, sigungu, contentTypeId, accessibleTypeList);
    }

}
