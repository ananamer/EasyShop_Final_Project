package easyshop.finalproject.Data_Center;

import java.util.ArrayList;

public class Category {
    private int imgResource;
    private String name;
    private int itemsNum;
    private int background;
    private ArrayList<Item> items;

    public Category(int imgResource, String name, int itemsNum, int background) {
        this.imgResource = imgResource;
        this.name = name;
        this.itemsNum = itemsNum;
        this.background = background;
        this.items = new ArrayList<>();
    }

    public void setItemsNum(int itemsNum) {
        this.itemsNum = itemsNum;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getImgResource() {
        return imgResource;
    }

    public void setImgResource(int imgResource) {
        this.imgResource = imgResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getItemsNum() {
        return itemsNum;
    }

    public void setItemsNum(Integer itemsNum) {
        this.itemsNum = itemsNum;
    }



}
