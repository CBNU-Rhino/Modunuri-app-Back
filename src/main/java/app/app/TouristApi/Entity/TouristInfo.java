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

    private String contentId;           // 컨텐츠 ID
    private String title;               // 제목
    private String addr1;               // 주소 1
    private String addr2;               // 주소 2
    private String zipCode;             // 우편번호
    private String tel;                 // 전화번호
    private double mapX;                // 지도 X좌표 (Double)
    private double mapY;                // 지도 Y좌표 (Double)
    private String firstImage;          // 첫 번째 이미지 URL
    private String firstImage2;         // 두 번째 이미지 URL
    private String createdTime;         // 생성 시간
    private String modifiedTime;        // 수정 시간
    private String areaCode;            // 지역 코드
    private String sigunguCode;         // 시군구 코드
    private String contentTypeId;       // 컨텐츠 타입 ID
    private String cat1;                // 대분류
    private String cat2;                // 중분류
    private String cat3;                // 소분류
    private String bookTour;            // 북 투어 여부
    private String mLevel;              // 지도 레벨
    private String cpyrhtDivCd;         // 저작권 구분 코드
}
