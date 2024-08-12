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

    private String contentId;
    private String title;
    private String addr1;
    private String addr2;
    private String zipcode;
    private String tel;
    private String mapx;
    private String mapy;
    private String firstImage;
    private String firstImage2;
    private String createdTime;
    private String modifiedTime;
    private String areaCode;
    private String sigunguCode;
    private String contentTypeId;
    private String cat1;
    private String cat2;
    private String cat3;
    private String booktour;
    private String mlevel;
    private String cpyrhtDivCd;
}