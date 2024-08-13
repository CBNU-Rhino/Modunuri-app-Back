package app.app.TouristApi.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TouristRequestDTO {
    private int contentTypeId;
    private String regionName;   // 한글로 입력 받는 지역명
    private String sigunguName;  // 한글로 입력 받는 시군구명
}
