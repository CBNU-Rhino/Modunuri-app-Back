package app.app.TouristApi.DTO;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "item")
public class Item {

    private String contentid;
    private String contenttypeid;
    private String title;
    private String createdtime;
    private String modifiedtime;
    private String tel;
    private String telname;
    private String homepage;
    private String booktour;
    private String firstimage;
    private String firstimage2;
    private String cpyrhtDivCd;
    private String areacode;
    private String sigungucode;
    private String cat1;
    private String cat2;
    private String cat3;
    private String addr1;
    private String addr2;
    private String zipcode;
    private String mapx;
    private String mapy;
    private String mlevel;
    private String overview;

    @XmlElement(name = "contentid")
    public String getContentid() {
        return contentid;
    }

    public void setContentid(String contentid) {
        this.contentid = contentid;
    }

    @XmlElement(name = "contenttypeid")
    public String getContenttypeid() {
        return contenttypeid;
    }

    public void setContenttypeid(String contenttypeid) {
        this.contenttypeid = contenttypeid;
    }

    @XmlElement(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name = "createdtime")
    public String getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    @XmlElement(name = "modifiedtime")
    public String getModifiedtime() {
        return modifiedtime;
    }

    public void setModifiedtime(String modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    @XmlElement(name = "tel")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @XmlElement(name = "telname")
    public String getTelname() {
        return telname;
    }

    public void setTelname(String telname) {
        this.telname = telname;
    }

    @XmlElement(name = "homepage")
    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @XmlElement(name = "booktour")
    public String getBooktour() {
        return booktour;
    }

    public void setBooktour(String booktour) {
        this.booktour = booktour;
    }

    @XmlElement(name = "firstimage")
    public String getFirstimage() {
        return firstimage;
    }

    public void setFirstimage(String firstimage) {
        this.firstimage = firstimage;
    }

    @XmlElement(name = "firstimage2")
    public String getFirstimage2() {
        return firstimage2;
    }

    public void setFirstimage2(String firstimage2) {
        this.firstimage2 = firstimage2;
    }

    @XmlElement(name = "cpyrhtDivCd")
    public String getCpyrhtDivCd() {
        return cpyrhtDivCd;
    }

    public void setCpyrhtDivCd(String cpyrhtDivCd) {
        this.cpyrhtDivCd = cpyrhtDivCd;
    }

    @XmlElement(name = "areacode")
    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    @XmlElement(name = "sigungucode")
    public String getSigungucode() {
        return sigungucode;
    }

    public void setSigungucode(String sigungucode) {
        this.sigungucode = sigungucode;
    }

    @XmlElement(name = "cat1")
    public String getCat1() {
        return cat1;
    }

    public void setCat1(String cat1) {
        this.cat1 = cat1;
    }

    @XmlElement(name = "cat2")
    public String getCat2() {
        return cat2;
    }

    public void setCat2(String cat2) {
        this.cat2 = cat2;
    }

    @XmlElement(name = "cat3")
    public String getCat3() {
        return cat3;
    }

    public void setCat3(String cat3) {
        this.cat3 = cat3;
    }

    @XmlElement(name = "addr1")
    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    @XmlElement(name = "addr2")
    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    @XmlElement(name = "zipcode")
    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @XmlElement(name = "mapx")
    public String getMapx() {
        return mapx;
    }

    public void setMapx(String mapx) {
        this.mapx = mapx;
    }

    @XmlElement(name = "mapy")
    public String getMapy() {
        return mapy;
    }

    public void setMapy(String mapy) {
        this.mapy = mapy;
    }

    @XmlElement(name = "mlevel")
    public String getMlevel() {
        return mlevel;
    }

    public void setMlevel(String mlevel) {
        this.mlevel = mlevel;
    }

    @XmlElement(name = "overview")
    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
