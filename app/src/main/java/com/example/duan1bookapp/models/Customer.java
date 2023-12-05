package com.example.duan1bookapp.models;

import java.io.Serializable;
public class Customer  implements Serializable {

    public int id;
    private String customerName;
    private String customerPassword;
    private String customereMail;
    private String customerbirthDate;

    private int localimageAvartar;
    private String avatar_url;
    private boolean enabled_CS ;


    private Coin coin;
    private Address address;

    private Order order;

    public Customer(String customerName, int localimageAvartar) {
        this.customerName = customerName;
        this.localimageAvartar = localimageAvartar;
    }

    public Customer(String customerName, String customerPassword, String customereMail, String customerbirthDate, Coin coin , boolean enabled_CS) {
        this.customerName = customerName;
        this.customerPassword = customerPassword;
        this.customereMail = customereMail;
        this.customerbirthDate = customerbirthDate;
        this.coin = coin;
        this.enabled_CS = enabled_CS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getCustomereMail() {
        return customereMail;
    }

    public void setCustomereMail(String customereMail) {
        this.customereMail = customereMail;
    }

    public String getCustomerbirthDate() {
        return customerbirthDate;
    }

    public boolean isEnabled_CS() {
        return enabled_CS;
    }

    public void setEnabled_CS(boolean enabled_CS) {
        this.enabled_CS = enabled_CS;
    }

    public void setCustomerbirthDate(String customerbirthDate) {
        this.customerbirthDate = customerbirthDate;
    }

    public Coin getCoin() {
        return coin;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }


    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    public Customer(String customerPassword, String customereMail) {
        this.customerPassword = customerPassword;
        this.customereMail = customereMail;
    }


    public Customer(String customerName, String customerPassword, String customereMail, Coin coin) {
        this.customerName = customerName;
        this.customerPassword = customerPassword;
        this.customereMail = customereMail;
        this.coin = coin;
    }

    public Customer(String customerName, String customerPassword, String customereMail, String customerbirthDate) {
        this.customerName = customerName;
        this.customerPassword = customerPassword;
        this.customereMail = customereMail;
        this.customerbirthDate = customerbirthDate;

    }


    public Customer( Address address,int id, String customerName, String customerPassword, String customereMail, String customerbirthDate) {
        this.id = id;
        this.customerName = customerName;
        this.customerPassword = customerPassword;
        this.customereMail = customereMail;
        this.customerbirthDate = customerbirthDate;
        this.address = address;
    }

    public Customer(String customerName, String customerPassword, String customereMail, String customerbirthDate,Coin coin) {
        this.customerName = customerName;
        this.customerPassword = customerPassword;
        this.customereMail = customereMail;
        this.customerbirthDate = customerbirthDate;
        this.coin = coin;
    }


    public Customer(String customerName, String customerPassword, String customereMail, String customerbirthDate, Coin coin, Address address) {
        this.customerName = customerName;
        this.customerPassword = customerPassword;
        this.customereMail = customereMail;
        this.customerbirthDate = customerbirthDate;
        this.coin = coin;
        this.address = address;
    }

    public Customer(int id, String customerName, String customerPassword, String customereMail, String customerbirthDate, Coin coin, Address address) {
        this.id = id;
        this.customerName = customerName;
        this.customerPassword = customerPassword;
        this.customereMail = customereMail;
        this.customerbirthDate = customerbirthDate;
        this.coin = coin;
        this.address = address;
    }

    public int getLocalimageAvartar() {
        return localimageAvartar;
    }

    public void setLocalimageAvartar(int localimageAvartar) {
        this.localimageAvartar = localimageAvartar;
    }

    public Customer(int id, String customerName, String customerPassword, String customereMail, String customerbirthDate, int localimageAvartar, String avatar_url, boolean enabled_CS, Coin coin, Address address, Order order) {
        this.id = id;
        this.customerName = customerName;
        this.customerPassword = customerPassword;
        this.customereMail = customereMail;
        this.customerbirthDate = customerbirthDate;
        this.localimageAvartar = localimageAvartar;
        this.avatar_url = avatar_url;
        this.enabled_CS = enabled_CS;
        this.coin = coin;
        this.address = address;
        this.order = order;
    }

    public Customer() {
    }
}
