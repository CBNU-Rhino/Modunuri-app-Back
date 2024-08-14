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
public class AccessibleInfo { // 무장애 정보 엔티티

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contentId;         // 컨텐츠 ID (TouristInfo와 연결)
    private String parking;           // 주차 가능 여부
    private String route;             // 경로 정보
    private String publicTransport;   // 대중교통 정보
    private String ticketOffice;      // 매표소 정보
    private String promotion;         // 홍보물 정보
    private String wheelchair;        // 휠체어 정보
    private String exit;              // 출구 정보
    private String elevator;          // 엘리베이터 정보
    private String restroom;          // 화장실 정보
    private String auditorium;        // 강당 정보
    private String room;              // 객실 정보
    private String handicapEtc;       // 기타 장애 관련 정보
    private String brailleBlock;      // 점자블록 정보
    private String helpDog;           // 도우미견 가능 여부
    private String guideHuman;        // 안내인 정보
    private String audioGuide;        // 오디오 가이드 정보
    private String bigPrint;          // 큰 글씨 안내문 정보
    private String braillePromotion;  // 점자 홍보물 정보
    private String guideSystem;       // 안내 시스템 정보
    private String blindHandicapEtc;  // 시각장애 관련 기타 정보
    private String signGuide;         // 수화 안내 정보
    private String videoGuide;        // 비디오 안내 정보
    private String hearingRoom;       // 청각장애 객실 정보
    private String hearingHandicapEtc;// 청각장애 관련 기타 정보
    private String stroller;          // 유모차 대여 정보
    private String lactationRoom;     // 수유실 정보
    private String babySpareChair;    // 아기 여분 의자 정보
    private String infantsFamilyEtc;  // 유아 동반 가족 관련 기타 정보
}
