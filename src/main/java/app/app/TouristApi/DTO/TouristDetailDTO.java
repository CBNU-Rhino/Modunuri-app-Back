package app.app.TouristApi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TouristDetailDTO {

    @JsonProperty("contentid")
    private String contentId;

    @JsonProperty("contenttypeid")
    private String contentTypeId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("createdtime")
    private String createdTime;

    @JsonProperty("modifiedtime")
    private String modifiedTime;

    @JsonProperty("tel")
    private String tel;

    @JsonProperty("telname")
    private String telName;

    @JsonProperty("homepage")
    private String homepage;

    @JsonProperty("booktour")
    private String bookTour;

    @JsonProperty("firstimage")
    private String firstImage;

    @JsonProperty("firstimage2")
    private String firstImage2;

    @JsonProperty("cpyrhtDivCd")
    private String copyrightDivisionCode;

    @JsonProperty("areacode")
    private String areaCode;

    @JsonProperty("sigungucode")
    private String sigunguCode;

    @JsonProperty("cat1")
    private String category1;

    @JsonProperty("cat2")
    private String category2;

    @JsonProperty("cat3")
    private String category3;

    @JsonProperty("addr1")
    private String address1;

    @JsonProperty("addr2")
    private String address2;

    @JsonProperty("zipcode")
    private String zipCode;

    @JsonProperty("mapx")
    private String mapX;

    @JsonProperty("mapy")
    private String mapY;

    @JsonProperty("mlevel")
    private String mapLevel;
}
