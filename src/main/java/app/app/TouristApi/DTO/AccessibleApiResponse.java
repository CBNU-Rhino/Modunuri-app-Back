package app.app.TouristApi.DTO;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class AccessibleApiResponse {

    private Header header;
    private AccessibleBody body;

    @XmlElement(name = "header")
    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    @XmlElement(name = "body")
    public AccessibleBody getBody() {
        return body;
    }

    public void setBody(AccessibleBody body) {
        this.body = body;
    }
}
