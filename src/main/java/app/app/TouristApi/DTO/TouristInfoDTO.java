package app.app.TouristApi.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TouristInfoDTO {
    private String addr1; // 주소 1
    private String addr2; // 주소 2
    private String areaCode; // 지역 코드
    private String bookTour; // 북 투어 여부
    private String cat1; // 대분류 코드
    private String cat2; // 중분류 코드
    private String cat3; // 소분류 코드
    private String contentId; // 콘텐츠 ID
    private String contentTypeId; // 콘텐츠 타입 ID
    private String createdTime; // 생성 시간
    private String firstImage; // 첫 번째 이미지 URL
    private String firstImage2; // 두 번째 이미지 URL
    private String mapX; // 지도 X좌표
    private String mapY; // 지도 Y좌표
    private String mlevel; // 지도 수준
    private String modifiedTime; // 수정 시간
    private String sigunguCode; // 시군구 코드
    private String tel; // 전화번호
    private String title; // 제목
    private String zipCode; // 우편번호


}
