package app.app.TouristApi.DTO;

import jakarta.xml.bind.annotation.XmlElement;
import java.util.List;

public class AccessibleItems {

    private List<AccessibleItem> item;

    @XmlElement(name = "item")
    public List<AccessibleItem> getItem() {
        return item;
    }

    public void setItem(List<AccessibleItem> item) {
        this.item = item;
    }
}
