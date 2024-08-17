package app.app.TouristApi.DTO;

import jakarta.xml.bind.annotation.XmlElement;


public class Header {

    private String resultCode;
    private String resultMsg;

    @XmlElement(name = "resultCode")
    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    @XmlElement(name = "resultMsg")
    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
