package app.app.TouristApi.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)  // 이 어노테이션을 추가합니다.
public class TouristInfoDTO {

    @JsonProperty("addr1")
    private String addr1; // 주소 1

    @JsonProperty("addr2")
    private String addr2; // 주소 2

    @JsonProperty("areacode")
    private String areaCode; // 지역 코드

    @JsonProperty("booktour")
    private String bookTour; // 북 투어 여부

    @JsonProperty("cat1")
    private String cat1; // 대분류 코드

    @JsonProperty("cat2")
    private String cat2; // 중분류 코드

    @JsonProperty("cat3")
    private String cat3; // 소분류 코드

    @JsonProperty("contentid")
    private String contentId; // 콘텐츠 ID

    @JsonProperty("contenttypeid")
    private String contentTypeId; // 콘텐츠 타입 ID

    @JsonProperty("createdtime")
    private String createdTime; // 생성 시간

    @JsonProperty("firstimage")
    private String firstImage; // 첫 번째 이미지 URL

    @JsonProperty("firstimage2")
    private String firstImage2; // 두 번째 이미지 URL

    @JsonProperty("mapx")
    private String mapX; // 지도 X좌표

    @JsonProperty("mapy")
    private String mapY; // 지도 Y좌표

    @JsonProperty("mlevel")
    private String mlevel; // 지도 수준

    @JsonProperty("modifiedtime")
    private String modifiedTime; // 수정 시간

    @JsonProperty("sigungucode")
    private String sigunguCode; // 시군구 코드

    @JsonProperty("tel")
    private String tel; // 전화번호

    @JsonProperty("title")
    private String title; // 제목

    @JsonProperty("zipcode")
    private String zipCode; // 우편번호
}
