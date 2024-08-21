package app.app.TouristApi.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AccessibleInfo { // 무장애 정보 엔티티

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("contentid")
    private String contentId;         // 컨텐츠 ID (TouristInfo와 연결)

    @JsonProperty("parking")
    private String parking;           // 주차 가능 여부

    @JsonProperty("route")
    private String route;             // 경로 정보

    @JsonProperty("publictransport")
    private String publicTransport;   // 대중교통 정보

    @JsonProperty("ticketoffice")
    private String ticketOffice;      // 매표소 정보

    @JsonProperty("promotion")
    private String promotion;         // 홍보물 정보

    @JsonProperty("wheelchair")
    private String wheelchair;        // 휠체어 정보

    @JsonProperty("exitInfo")
    private String exitInfo;  // 수정된 필드 이름

    @JsonProperty("elevator")
    private String elevator;          // 엘리베이터 정보

    @JsonProperty("restroom")
    private String restroom;          // 화장실 정보

    @JsonProperty("auditorium")
    private String auditorium;        // 강당 정보

    @JsonProperty("room")
    private String room;              // 객실 정보

    @JsonProperty("handicapetc")
    private String handicapEtc;       // 기타 장애 관련 정보

    @JsonProperty("braileblock")
    private String brailleBlock;      // 점자블록 정보

    @JsonProperty("helpdog")
    private String helpDog;           // 도우미견 가능 여부

    @JsonProperty("guidehuman")
    private String guideHuman;        // 안내인 정보

    @JsonProperty("audioguide")
    private String audioGuide;        // 오디오 가이드 정보

    @JsonProperty("bigprint")
    private String bigPrint;          // 큰 글씨 안내문 정보

    @JsonProperty("brailepromotion")
    private String braillePromotion;  // 점자 홍보물 정보

    @JsonProperty("guidesystem")
    private String guideSystem;       // 안내 시스템 정보

    @JsonProperty("blindhandicapetc")
    private String blindHandicapEtc;  // 시각장애 관련 기타 정보

    @JsonProperty("signguide")
    private String signGuide;         // 수화 안내 정보

    @JsonProperty("videoguide")
    private String videoGuide;        // 비디오 안내 정보

    @JsonProperty("hearingroom")
    private String hearingRoom;       // 청각장애 객실 정보

    @JsonProperty("hearinghandicapetc")
    private String hearingHandicapEtc;// 청각장애 관련 기타 정보

    @JsonProperty("stroller")
    private String stroller;          // 유모차 대여 정보

    @JsonProperty("lactationroom")
    private String lactationRoom;     // 수유실 정보

    @JsonProperty("babysparechair")
    private String babySpareChair;    // 아기 여분 의자 정보

    @JsonProperty("infantsfamilyetc")
    private String infantsFamilyEtc;  // 유아 동반 가족 관련 기타 정보
}
