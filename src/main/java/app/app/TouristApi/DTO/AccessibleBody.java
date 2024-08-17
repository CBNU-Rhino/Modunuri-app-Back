package app.app.TouristApi.DTO;

import jakarta.xml.bind.annotation.XmlElement;

public class AccessibleBody {

    private AccessibleItems items;

    @XmlElement(name = "items")
    public AccessibleItems getItems() {
        return items;
    }

    public void setItems(AccessibleItems items) {
        this.items = items;
    }
}

