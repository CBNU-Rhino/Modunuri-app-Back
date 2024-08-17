package app.app.TouristApi.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@XmlRootElement(name = "touristItem")
public class TouristInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @XmlElement(name = "contentid")
    private String contentId;         // 컨텐츠 ID

    @XmlElement(name = "contenttypeid")
    private String contentTypeId;     // 컨텐츠 타입 ID

    @XmlElement(name = "title")
    private String title;             // 제목

    @XmlElement(name = "createdtime")
    private String createdTime;       // 생성 시간

    @XmlElement(name = "modifiedtime")
    private String modifiedTime;      // 수정 시간

    @XmlElement(name = "tel")
    private String tel;               // 전화번호

    @XmlElement(name = "homepage")
    private String homepage;          // 홈페이지

    @XmlElement(name = "booktour")
    private String booktour;          // 북 투어 여부

    @XmlElement(name = "firstimage")
    private String firstImage;        // 첫 번째 이미지 URL

    @XmlElement(name = "firstimage2")
    private String firstImage2;       // 두 번째 이미지 URL

    @XmlElement(name = "cpyrhtDivCd")
    private String cpyrhtDivCd;       // 저작권 구분 코드

    @XmlElement(name = "areacode")
    private String areaCode;          // 지역 코드

    @XmlElement(name = "sigungucode")
    private String sigunguCode;       // 시군구 코드

    @XmlElement(name = "cat1")
    private String cat1;              // 대분류

    @XmlElement(name = "cat2")
    private String cat2;              // 중분류

    @XmlElement(name = "cat3")
    private String cat3;              // 소분류

    @XmlElement(name = "addr1")
    private String addr1;             // 주소 1

    @XmlElement(name = "addr2")
    private String addr2;             // 주소 2

    @XmlElement(name = "zipcode")
    private String zipcode;           // 우편번호

    @XmlElement(name = "mapx")
    private double mapx;              // 지도 X좌표

    @XmlElement(name = "mapy")
    private double mapy;              // 지도 Y좌표

    @XmlElement(name = "overview")
    private String overview;          // 개요
}
