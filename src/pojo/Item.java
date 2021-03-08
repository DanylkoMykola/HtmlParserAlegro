package pojo;

import java.util.Map;

public class Item {
    private String name;
    private String link;
    private String fullPrice;
    private String discount;
    private String discountPrice;
    private Map<String, String> characteristics;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(String fullPrice) {
        this.fullPrice = fullPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Map<String, String> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(Map<String, String> characteristics) {
        this.characteristics = characteristics;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> pair : characteristics.entrySet()){
            sb.append(pair.getKey());
            sb.append(": ");
            sb.append(pair.getValue());
            sb.append(";");
        }
        return name + ';' +
                link + ';' +
                fullPrice + ';' +
                discount + ';' +
                discountPrice + ';' +
                sb;
    }
}
