package easyshop.finalproject.Data_Center;

import java.util.HashMap;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private int numItemsInCart = 0;
    private String imageUrl;
    private HashMap<String, Item> myCart;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.numItemsInCart = 0;
        this.myCart = new HashMap<>();
    }

    public int getNumItemsInCart() {
        return numItemsInCart;
    }

    public void setNumItemsInCart(int numItemsInCart) {
        this.numItemsInCart = numItemsInCart;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public HashMap<String, Item> getMyCart() {
        return myCart;
    }

    public void setMyCart(HashMap<String, Item> myCart) {
        this.myCart = myCart;
    }
}
