package app.app.TouristApi.DTO;

import app.app.TouristApi.Entity.AccessibleInfo;
import app.app.TouristApi.Entity.TouristInfo;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TouristDetailDTO {
    private TouristInfo touristInfo;
    private AccessibleInfo accessibleInfo;

    // Getters and Setters
}