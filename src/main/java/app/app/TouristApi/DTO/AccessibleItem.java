package app.app.TouristApi.DTO;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "item")
public class AccessibleItem {

    private String contentid;
    private String parking;
    private String route;
    private String publictransport;
    private String ticketoffice;
    private String promotion;
    private String wheelchair;
    private String exit;
    private String elevator;
    private String restroom;
    private String auditorium;
    private String room;
    private String handicapetc;
    private String braileblock;
    private String helpdog;
    private String guidehuman;
    private String audioguide;
    private String bigprint;
    private String brailepromotion;
    private String guidesystem;
    private String blindhandicapetc;
    private String signguide;
    private String videoguide;
    private String hearingroom;
    private String hearinghandicapetc;
    private String stroller;
    private String lactationroom;
    private String babysparechair;
    private String infantsfamilyetc;

    @XmlElement(name = "contentid")
    public String getContentid() {
        return contentid;
    }

    public void setContentid(String contentid) {
        this.contentid = contentid;
    }

    @XmlElement(name = "parking")
    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    @XmlElement(name = "route")
    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    @XmlElement(name = "publictransport")
    public String getPublictransport() {
        return publictransport;
    }

    public void setPublictransport(String publictransport) {
        this.publictransport = publictransport;
    }

    @XmlElement(name = "ticketoffice")
    public String getTicketoffice() {
        return ticketoffice;
    }

    public void setTicketoffice(String ticketoffice) {
        this.ticketoffice = ticketoffice;
    }

    @XmlElement(name = "promotion")
    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    @XmlElement(name = "wheelchair")
    public String getWheelchair() {
        return wheelchair;
    }

    public void setWheelchair(String wheelchair) {
        this.wheelchair = wheelchair;
    }

    @XmlElement(name = "exit")
    public String getExit() {
        return exit;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }

    @XmlElement(name = "elevator")
    public String getElevator() {
        return elevator;
    }

    public void setElevator(String elevator) {
        this.elevator = elevator;
    }

    @XmlElement(name = "restroom")
    public String getRestroom() {
        return restroom;
    }

    public void setRestroom(String restroom) {
        this.restroom = restroom;
    }

    @XmlElement(name = "auditorium")
    public String getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(String auditorium) {
        this.auditorium = auditorium;
    }

    @XmlElement(name = "room")
    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @XmlElement(name = "handicapetc")
    public String getHandicapetc() {
        return handicapetc;
    }

    public void setHandicapetc(String handicapetc) {
        this.handicapetc = handicapetc;
    }

    @XmlElement(name = "braileblock")
    public String getBraileblock() {
        return braileblock;
    }

    public void setBraileblock(String braileblock) {
        this.braileblock = braileblock;
    }

    @XmlElement(name = "helpdog")
    public String getHelpdog() {
        return helpdog;
    }

    public void setHelpdog(String helpdog) {
        this.helpdog = helpdog;
    }

    @XmlElement(name = "guidehuman")
    public String getGuidehuman() {
        return guidehuman;
    }

    public void setGuidehuman(String guidehuman) {
        this.guidehuman = guidehuman;
    }

    @XmlElement(name = "audioguide")
    public String getAudioguide() {
        return audioguide;
    }

    public void setAudioguide(String audioguide) {
        this.audioguide = audioguide;
    }

    @XmlElement(name = "bigprint")
    public String getBigprint() {
        return bigprint;
    }

    public void setBigprint(String bigprint) {
        this.bigprint = bigprint;
    }

    @XmlElement(name = "brailepromotion")
    public String getBrailepromotion() {
        return brailepromotion;
    }

    public void setBrailepromotion(String brailepromotion) {
        this.brailepromotion = brailepromotion;
    }

    @XmlElement(name = "guidesystem")
    public String getGuidesystem() {
        return guidesystem;
    }

    public void setGuidesystem(String guidesystem) {
        this.guidesystem = guidesystem;
    }

    @XmlElement(name = "blindhandicapetc")
    public String getBlindhandicapetc() {
        return blindhandicapetc;
    }

    public void setBlindhandicapetc(String blindhandicapetc) {
        this.blindhandicapetc = blindhandicapetc;
    }

    @XmlElement(name = "signguide")
    public String getSignguide() {
        return signguide;
    }

    public void setSignguide(String signguide) {
        this.signguide = signguide;
    }

    @XmlElement(name = "videoguide")
    public String getVideoguide() {
        return videoguide;
    }

    public void setVideoguide(String videoguide) {
        this.videoguide = videoguide;
    }

    @XmlElement(name = "hearingroom")
    public String getHearingroom() {
        return hearingroom;
    }

    public void setHearingroom(String hearingroom) {
        this.hearingroom = hearingroom;
    }

    @XmlElement(name = "hearinghandicapetc")
    public String getHearinghandicapetc() {
        return hearinghandicapetc;
    }

    public void setHearinghandicapetc(String hearinghandicapetc) {
        this.hearinghandicapetc = hearinghandicapetc;
    }

    @XmlElement(name = "stroller")
    public String getStroller() {
        return stroller;
    }

    public void setStroller(String stroller) {
        this.stroller = stroller;
    }

    @XmlElement(name = "lactationroom")
    public String getLactationroom() {
        return lactationroom;
    }

    public void setLactationroom(String lactationroom) {
        this.lactationroom = lactationroom;
    }

    @XmlElement(name = "babysparechair")
    public String getBabysparechair() {
        return babysparechair;
    }

    public void setBabysparechair(String babysparechair) {
        this.babysparechair = babysparechair;
    }

    @XmlElement(name = "infantsfamilyetc")
    public String getInfantsfamilyetc() {
        return infantsfamilyetc;
    }

    public void setInfantsfamilyetc(String infantsfamilyetc) {
        this.infantsfamilyetc = infantsfamilyetc;
    }
}
