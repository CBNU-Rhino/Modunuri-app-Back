package app.app.TouristApi.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TouristResponseDTO {
    private Response response;

    @Getter
    @Setter
    public static class Response {
        private Header header;
        private Body body;

        @Getter
        @Setter
        public static class Header {
            private String resultCode;
            private String resultMsg;
        }

        @Getter
        @Setter
        public static class Body {
            private Items items;
            private int numOfRows;
            private int pageNo;
            private int totalCount;

            @Getter
            @Setter
            public static class Items {
                private List<Item> item;

                @Getter
                @Setter
                public static class Item {
                    private String addr1;
                    private String addr2;
                    private String areaCode;
                    private String bookTour;
                    private String cat1;
                    private String cat2;
                    private String cat3;
                    private String contentId;
                    private String contentTypeId;
                    private String createdTime;
                    private String firstImage;
                    private String firstImage2;
                    private Double mapX;
                    private Double mapY;
                    private String mLevel;
                    private String modifiedTime;
                    private String sigunguCode;
                    private String tel;
                    private String title;
                    private String zipCode;
                    private String cpyrhtDivCd;
                }
            }
        }
    }
}
