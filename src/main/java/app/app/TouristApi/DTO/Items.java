package app.app.TouristApi.DTO;


import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;

public class Items {

    private List<Item> item;

    @XmlElement(name = "item")
    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }
}
