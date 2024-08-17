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
@XmlRootElement(name = "accessibleItem")
public class AccessibleInfo { // 무장애 정보 엔티티

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @XmlElement(name = "contentid")
    private String contentId;         // 컨텐츠 ID (TouristInfo와 연결)

    @XmlElement(name = "parking")
    private String parking;           // 주차 가능 여부

    @XmlElement(name = "route")
    private String route;             // 경로 정보

    @XmlElement(name = "publictransport")
    private String publicTransport;   // 대중교통 정보

    @XmlElement(name = "ticketoffice")
    private String ticketOffice;      // 매표소 정보

    @XmlElement(name = "promotion")
    private String promotion;         // 홍보물 정보

    @XmlElement(name = "wheelchair")
    private String wheelchair;        // 휠체어 정보

    @XmlElement(name = "exitInfo")
    private String exitInfo;  // 수정된 필드 이름

    @XmlElement(name = "elevator")
    private String elevator;          // 엘리베이터 정보

    @XmlElement(name = "restroom")
    private String restroom;          // 화장실 정보

    @XmlElement(name = "auditorium")
    private String auditorium;        // 강당 정보

    @XmlElement(name = "room")
    private String room;              // 객실 정보

    @XmlElement(name = "handicapetc")
    private String handicapEtc;       // 기타 장애 관련 정보

    @XmlElement(name = "braileblock")
    private String brailleBlock;      // 점자블록 정보

    @XmlElement(name = "helpdog")
    private String helpDog;           // 도우미견 가능 여부

    @XmlElement(name = "guidehuman")
    private String guideHuman;        // 안내인 정보

    @XmlElement(name = "audioguide")
    private String audioGuide;        // 오디오 가이드 정보

    @XmlElement(name = "bigprint")
    private String bigPrint;          // 큰 글씨 안내문 정보

    @XmlElement(name = "brailepromotion")
    private String braillePromotion;  // 점자 홍보물 정보

    @XmlElement(name = "guidesystem")
    private String guideSystem;       // 안내 시스템 정보

    @XmlElement(name = "blindhandicapetc")
    private String blindHandicapEtc;  // 시각장애 관련 기타 정보

    @XmlElement(name = "signguide")
    private String signGuide;         // 수화 안내 정보

    @XmlElement(name = "videoguide")
    private String videoGuide;        // 비디오 안내 정보

    @XmlElement(name = "hearingroom")
    private String hearingRoom;       // 청각장애 객실 정보

    @XmlElement(name = "hearinghandicapetc")
    private String hearingHandicapEtc;// 청각장애 관련 기타 정보

    @XmlElement(name = "stroller")
    private String stroller;          // 유모차 대여 정보

    @XmlElement(name = "lactationroom")
    private String lactationRoom;     // 수유실 정보

    @XmlElement(name = "babysparechair")
    private String babySpareChair;    // 아기 여분 의자 정보

    @XmlElement(name = "infantsfamilyetc")
    private String infantsFamilyEtc;  // 유아 동반 가족 관련 기타 정보

    @XmlElement(name = "overview")
    private String overview; // 개요 정보
}
