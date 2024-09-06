package app.app.TouristApi.Service;

import app.app.TouristApi.Entity.AccessibleInfo;
import app.app.TouristApi.Repository.AccessibleInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TouristSpotService {

    private final AccessibleInfoRepository accessibleInfoRepository;

    @Autowired
    public TouristSpotService(AccessibleInfoRepository accessibleInfoRepository) {
        this.accessibleInfoRepository = accessibleInfoRepository;
    }



    public List<AccessibleInfo> searchTouristSpotsByAccessibility(List<String> contentIdList, List<String> accessibleTypeList) {
        if (accessibleTypeList == null || accessibleTypeList.isEmpty()) {
            // accessibleTypeList가 비어 있으면 무장애 필터 없이 검색 수행
            return accessibleInfoRepository.findByContentIdIn(contentIdList);
        }
        return accessibleInfoRepository.findByContentIdInAndAccessibleTypes(contentIdList, accessibleTypeList);
    }

}
