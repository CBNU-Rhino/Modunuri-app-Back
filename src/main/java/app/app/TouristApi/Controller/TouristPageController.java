package app.app.TouristApi.Controller;

import app.app.TouristApi.DTO.TouristInfoWithAccessibilityDTO;
import app.app.TouristApi.Service.TouristApiService; // TouristApiService 임포트 추가
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/touristSpot")
public class TouristPageController {

    // TouristApiService를 필드로 선언
    private final TouristApiService touristApiService;

    // 생성자를 통해 TouristApiService를 주입
    @Autowired
    public TouristPageController(TouristApiService touristApiService) {
        this.touristApiService = touristApiService;
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
    // /searchresult.html 페이지로 이동
    @GetMapping("/searchresult.html")
    public String getSearchResultPage(@RequestParam String contentId, Model model) {
        // contentId를 Model에 추가해서 view로 넘깁니다.
        model.addAttribute("contentId", contentId);
        return "touristSpot/searchresult"; // templates/touristSpot/searchresult.html로 이동
    }
}

