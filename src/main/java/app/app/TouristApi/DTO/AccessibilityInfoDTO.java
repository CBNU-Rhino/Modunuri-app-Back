package app.app.TouristApi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccessibilityInfoDTO {

    @JsonProperty("contentid")
    private String contentId; // 관광지 ID

    @JsonProperty("parking")
    private String parking; // 주차 시설 정보

    @JsonProperty("route")
    private String route; // 경로 정보

    @JsonProperty("publictransport")
    private String publicTransport; // 대중교통 접근성 정보

    @JsonProperty("ticketoffice")
    private String ticketOffice; // 매표소 정보

    @JsonProperty("promotion")
    private String promotion; // 홍보 정보

    @JsonProperty("wheelchair")
    private String wheelchair; // 휠체어 사용 가능 여부

    @JsonProperty("exit")
    private String exit; // 출구 정보

    @JsonProperty("elevator")
    private String elevator; // 엘리베이터 유무

    @JsonProperty("restroom")
    private String restroom; // 화장실 정보

    @JsonProperty("auditorium")
    private String auditorium; // 강당/공연장 정보

    @JsonProperty("room")
    private String room; // 객실 정보

    @JsonProperty("handicapetc")
    private String handicapEtc; // 기타 장애인 편의 시설

    @JsonProperty("braileblock")
    private String braileBlock; // 점자 블록 유무

    @JsonProperty("helpdog")
    private String helpDog; // 도우미견 동반 가능 여부

    @JsonProperty("guidehuman")
    private String guideHuman; // 안내 인력 유무

    @JsonProperty("audioguide")
    private String audioGuide; // 오디오 가이드 제공 여부

    @JsonProperty("bigprint")
    private String bigPrint; // 큰 글씨 안내 제공 여부

    @JsonProperty("brailepromotion")
    private String brailePromotion; // 점자 홍보물 제공 여부

    @JsonProperty("guidesystem")
    private String guideSystem; // 안내 시스템 정보

    @JsonProperty("blindhandicapetc")
    private String blindHandicapEtc; // 시각 장애인 관련 기타 정보

    @JsonProperty("signguide")
    private String signGuide; // 수어 안내 제공 여부

    @JsonProperty("videoguide")
    private String videoGuide; // 영상 안내 제공 여부

    @JsonProperty("hearingroom")
    private String hearingRoom; // 청각 장애인을 위한 시설 정보

    @JsonProperty("hearinghandicapetc")
    private String hearingHandicapEtc; // 청각 장애인 관련 기타 정보

    @JsonProperty("stroller")
    private String stroller; // 유모차 대여 가능 여부

    @JsonProperty("lactationroom")
    private String lactationRoom; // 수유실 유무

    @JsonProperty("babysparechair")
    private String babySpareChair; // 아기 의자 비치 여부

    @JsonProperty("infantsfamilyetc")
    private String infantsFamilyEtc; // 유아 동반 가족을 위한 기타 정보

    // 추가적인 필드가 있으면 여기에 추가합니다.
}
