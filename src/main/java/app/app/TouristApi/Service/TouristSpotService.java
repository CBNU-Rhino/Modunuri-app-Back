package app.app.TouristApi.Service;

import app.app.TouristApi.Entity.AccessibleInfo;
import app.app.TouristApi.Repository.AccessibleInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristSpotService {

    private final AccessibleInfoRepository accessibleInfoRepository;

    @Autowired
    public TouristSpotService(AccessibleInfoRepository accessibleInfoRepository) {
        this.accessibleInfoRepository = accessibleInfoRepository;
    }

    public List<AccessibleInfo> searchTouristSpotsByAccessibility(
            String region, String sigungu, String contentTypeId, List<String> accessibleTypes) {

        // 기본적으로 빈 값을 전달하여 각 필터를 적용합니다.
        String wheelchair = accessibleTypes.contains("wheelchair") ? "Y" : null;
        String elevator = accessibleTypes.contains("elevator") ? "Y" : null;
        String restroom = accessibleTypes.contains("restroom") ? "Y" : null;
        String brailleblock = accessibleTypes.contains("brailleblock") ? "Y" : null;
        String stroller = accessibleTypes.contains("stroller") ? "Y" : null;
        String helpDog = accessibleTypes.contains("helpdog") ? "Y" : null;

        return accessibleInfoRepository.findTouristSpotsByFilters(
                region, sigungu, contentTypeId, wheelchair, elevator, restroom, brailleblock, stroller, helpDog
        );
    }
}
