package model;

public class Customer {

    private int customerId;
    private String name;
    private String phone;
    private String cccd;

    public Customer(int customerId, String name, String phone, String cccd) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.cccd = cccd;
    }

    public Customer(String name, String phone, String cccd) {
        this.name = name;
        this.phone = phone;
        this.cccd = cccd;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }
}