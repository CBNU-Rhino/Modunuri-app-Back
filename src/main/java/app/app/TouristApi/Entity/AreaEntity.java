package app.app.TouristApi.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "area")
@Getter
@Setter
public class AreaEntity {

    @Id
    private String code;

    private int regionCode;
    private String sigunguCode;
    private String regionName;
    private double x;
    private double y;

    public AreaEntity() {}

    public AreaEntity(String code, int regionCode, String sigunguCode, String regionName, double x, double y) {
        this.code = code;
        this.regionCode = regionCode;
        this.sigunguCode = sigunguCode;
        this.regionName = regionName;
        this.x = x;
        this.y = y;
    }

    // Getter, Setter 메서드 추가
    // ...
}
