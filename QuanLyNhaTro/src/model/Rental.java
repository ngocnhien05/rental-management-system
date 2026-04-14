package model;

import java.sql.Date;

public class Rental {

    private int rentalId;
    private int roomId;
    private int customerId;
    private Date dateRent;

    public Rental(int rentalId, int roomId, int customerId, Date dateRent) {
        this.rentalId = rentalId;
        this.roomId = roomId;
        this.customerId = customerId;
        this.dateRent = dateRent;
    }

    public Rental(int roomId, int customerId, Date dateRent) {
        this.roomId = roomId;
        this.customerId = customerId;
        this.dateRent = dateRent;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getDateRent() {
        return dateRent;
    }

    public void setDateRent(Date dateRent) {
        this.dateRent = dateRent;
    }
}