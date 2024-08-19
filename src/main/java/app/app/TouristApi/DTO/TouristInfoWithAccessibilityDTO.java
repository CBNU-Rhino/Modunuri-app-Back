package app.app.TouristApi.DTO;

public class TouristInfoWithAccessibilityDTO {
    private TouristDetailDTO touristDetail;
    private AccessibilityInfoDTO accessibilityInfo;

    // No-argument constructor
    public TouristInfoWithAccessibilityDTO() {}

    // All-argument constructor
    public TouristInfoWithAccessibilityDTO(TouristDetailDTO touristDetail, AccessibilityInfoDTO accessibilityInfo) {
        this.touristDetail = touristDetail;
        this.accessibilityInfo = accessibilityInfo;
    }

    // Getters and Setters
    public TouristDetailDTO getTouristDetail() {
        return touristDetail;
    }

    public void setTouristDetail(TouristDetailDTO touristDetail) {
        this.touristDetail = touristDetail;
    }

    public AccessibilityInfoDTO getAccessibilityInfo() {
        return accessibilityInfo;
    }

    public void setAccessibilityInfo(AccessibilityInfoDTO accessibilityInfo) {
        this.accessibilityInfo = accessibilityInfo;
    }
}
