package app.app.Code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 카테고리
 */

@Getter
@RequiredArgsConstructor
public enum Category { //25번 추천코스 - 무장애여행 없음
    A01 ("자연", 12),
    A02 ("인문(문화/예술/역사)", 14),
    A03 ("스포츠", 28),
    A04 ("쇼핑", 38),
    A05 ("음식", 39),
    B02 ("숙박", 32),
    C01 ("추천코스", 25),
    A0101 ("자연관광지", 12),
    A0102 ("관광자원", 12),
    A0201 ("역사관광지", 14),
    A0202 ("휴양관광지", 12),
    A0203 ("체험관광지", 12),
    A0204 ("산업관광지", 12),
    A0205 ("건축/조형물", 14),
    A0206 ("문화시설", 14),
    A0207 ("축제", 15),
    A0208 ("공연/행사", 15),
    A0301 ("레포츠소개", 28),
    A0302 ("육상 레포츠", 28),
    A0303 ("수상 레포츠", 28),
    A0304 ("항공 레포츠", 28),
    A0305 ("복합 레포츠", 28),
    A0401 ("쇼핑", 38),
    A0502 ("음식점", 39),
    B0201 ("숙박시설", 32),
    C0112 ("가족코스", 25),
    C0113 ("나홀로코스", 25),
    C0114 ("힐링코스", 25),
    C0115 ("도보코스", 25),
    C0116 ("캠핑코스", 25),
    C0117 ("맛코스", 25),

    A01010100   ("국립공원", 12),
    A01010200   ("도립공원", 12),
    A01010300   ("군립공원", 12),
    A01010400   ("산", 12),
    A01010500   ("자연생태관광지", 12),
    A01010600   ("자연휴양림", 12),
    A01010700   ("수목원", 12),
    A01010800   ("폭포", 12),
    A01010900   ("계곡", 12),
    A01011000	("약수터", 12),
    A01011100   ("해안절경", 12),
    A01011200   ("해수욕장", 12),
    A01011300   ("섬", 12),
    A01011400   ("항구/포구", 12),
    A01011600   ("등대", 12),
    A01011700   ("호수", 12),
    A01011800   ("강", 12),
    A01011900   ("동굴", 12),

    A01020100	("희귀동식물", 12),
    A01020200	("기암괴석", 12),
    A02010100	("고궁", 14),
    A02010200	("성", 14),
    A02010300	("문", 14),
    A02010400	("고택", 14),
    A02010500	("생가", 14),
    A02010600	("민속마을", 14),
    A02010700	("유적지/사적지", 14),
    A02010800	("사찰", 14),
    A02010900	("종교성지", 14),
    A02011000	("안보관광", 14),
    A02020200	("관광단지", 12),
    A02020300	("온천/욕장/스파", 12),
    A02020400	("이색찜질방", 12),
    A02020500	("헬스투어", 12),
    A02020600	("테마공원", 12),
    A02020700	("공원", 12),
    A02020800	("유람선/잠수함관광", 12),
    A02030100	("농.산.어촌 체험", 12),
    A02030200	("전통체험", 12),
    A02030300	("산사체험", 12),
    A02030400	("이색체험", 12),
    A02030600	("이색거리", 12),
    A02040400	("발전소", 12),
    A02040600	("식음료", 39),
    A02040800	("기타", 12),
    A02040900	("전자-반도체", 12),
    A02041000	("자동차", 12),
    A02050100	("다리/대교", 14),
    A02050200	("기념탑/기념비/전망대", 14),
    A02050300	("분수", 14),
    A02050400	("동상", 14),
    A02050500	("터널", 14),
    A02050600	("유명건물", 14),
    A02060100	("박물관", 14),
    A02060200	("기념관", 14),
    A02060300	("전시관", 14),
    A02060400	("컨벤션센터", 14),
    A02060500	("미술관/화랑", 14),
    A02060600	("공연장", 14),
    A02060700	("문화원", 14),
    A02060800	("외국문화원", 14),
    A02060900	("도서관", 14),
    A02061000	("대형서점", 14),
    A02061100	("문화전수시설", 14),
    A02061200	("영화관", 14),
    A02061300	("어학당", 14),
    A02061400	("학교", 14),
    A02070100	("문화관광축제", 15),
    A02070200	("일반축제", 15),
    A02080100	("전통공연", 15),
    A02080200	("연극", 15),
    A02080300	("뮤지컬", 15),
    A02080400	("오페라", 15),
    A02080500	("전시회", 15),
    A02080600	("박람회", 15),
    A02080800	("무용", 15),
    A02080900	("클래식음악회", 15),
    A02081000	("대중콘서트", 15),
    A02081100	("영화", 15),
    A02081200	("스포츠경기", 15),
    A02081300	("기타행사", 15),
    A03010200	("수상레포츠", 28),
    A03010300	("항공레포츠", 28),
    A03020200 	("수련시설", 28),
    A03020300 	("경기장", 28),
    A03020400 	("인라인(실내 인라인 포함)", 28),
    A03020500 	("자전거하이킹", 28),
    A03020600 	("카트", 28),
    A03020700 	("골프", 28),
    A03020800 	("경마", 28),
    A03020900 	("경륜", 28),
    A03021000 	("카지노", 28),
    A03021100 	("승마", 28),
    A03021200 	("스키/스노보드", 28),
    A03021300 	("스케이트", 28),
    A03021400 	("썰매장", 28),
    A03021500 	("수렵장", 28),
    A03021600 	("사격장", 28),
    A03021700 	("야영장,오토캠핑장", 28),
    A03021800 	("암벽등반", 28),
    A03022000 	("서바이벌게임", 28),
    A03022100 	("ATV", 28),
    A03022200 	("MTB", 28),
    A03022300 	("오프로드", 28),
    A03022400 	("번지점프", 28),
    A03022600 	("스키(보드) 렌탈샵", 28),
    A03022700   ("트랙킹", 28),
    A03030100	("윈드서핑/제트스키", 28),
    A03030200	("카약/카누", 28),
    A03030300	("요트", 28),
    A03030400	("스노쿨링/스킨스쿠버다이빙", 28),
    A03030500	("민물낚시", 28),
    A03030600	("바다낚시", 28),
    A03030700	("수영", 28),
    A03030800	("래프팅", 28),
    A03040100	("스카이다이빙", 28),
    A03040200	("초경량비행", 28),
    A03040300	("헹글라이딩/패러글라이딩", 28),
    A03040400	("열기구", 28),
    A03050100	("복합 레포츠", 28),
    B01020100   ("공항", 12),
    B01020200   ("역", 12),
    B01020400   ("여객선 터미널", 12),
    B01020500   ("휴게소", 12),
    B02010100	("관광호텔", 32),
    B02010500	("콘도미니엄", 32),
    B02010600	("유스호스텔", 32),
    B02010700	("펜션", 32),
    B02010900	("모텔", 32),
    B02011000	("민박", 32),
    B02011100	("게스트하우스", 32),
    B02011200	("홈스테이", 32),
    B02011300	("서비스레지던스", 32),
    B02011600	("한옥", 32),
    A04010100	("5일장", 38),
    A04010200	("상설시장", 38),
    A04010300	("백화점", 38),
    A04010400	("면세점", 38),
    A04010500	("대형마트", 38),
    A04010600	("전문매장/상가", 38),
    A04010700	("공예/공방", 38),
    A04010900	("특산물판매점", 38),
    A04011000	("사후면세점", 38),
    A05020100	("한식", 39),
    A05020200	("서양식", 39),
    A05020300	("일식", 39),
    A05020400	("중식", 39),
    A05020700	("이색음식점", 39),
    A05020900	("카페/전통찻집", 39),
    A05021000	("클럽", 39),
    A99999999   ("기타", 12);

    private final String category;
    private final int contentTypeId;
}
