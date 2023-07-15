package easyshop.finalproject.Data_Center;

public class Item {

    private String name;
    private int price;
    private String type;
    private String description;
    private String itemUrl;
    private int quantity;

    public Item() {
    }

    public Item(String name, int price, String type, String description, String itemUrl, int quantity) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.description = description;
        this.itemUrl = itemUrl;
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
