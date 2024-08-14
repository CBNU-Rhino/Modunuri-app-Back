package app.app.TouristApi.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TouristInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contentId;         // 컨텐츠 ID
    private String contentTypeId;     // 컨텐츠 타입 ID
    private String title;             // 제목
    private String createdTime;       // 생성 시간
    private String modifiedTime;      // 수정 시간
    private String tel;               // 전화번호
    private String homepage;          // 홈페이지
    private String booktour;          // 북 투어 여부
    private String firstImage;        // 첫 번째 이미지 URL
    private String firstImage2;       // 두 번째 이미지 URL
    private String cpyrhtDivCd;       // 저작권 구분 코드
    private String areaCode;          // 지역 코드
    private String sigunguCode;       // 시군구 코드
    private String cat1;              // 대분류
    private String cat2;              // 중분류
    private String cat3;              // 소분류
    private String addr1;             // 주소 1
    private String addr2;             // 주소 2
    private String zipcode;           // 우편번호
    private double mapx;              // 지도 X좌표
    private double mapy;              // 지도 Y좌표
    private String overview;          // 개요
}
