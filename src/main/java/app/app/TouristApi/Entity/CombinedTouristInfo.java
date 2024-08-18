package app.app.TouristApi.Entity;

public class CombinedTouristInfo {

    private String touristInfo;
    private String accessibleInfo;

    public CombinedTouristInfo(String touristInfo, String accessibleInfo) {
        this.touristInfo = touristInfo;
        this.accessibleInfo = accessibleInfo;
    }

    public String getTouristInfo() {
        return touristInfo;
    }

    public void setTouristInfo(String touristInfo) {
        this.touristInfo = touristInfo;
    }

    public String getAccessibleInfo() {
        return accessibleInfo;
    }

    public void setAccessibleInfo(String accessibleInfo) {
        this.accessibleInfo = accessibleInfo;
    }
}

