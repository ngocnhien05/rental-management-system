package model;

public class Room {

    private int roomId;
    private String roomName;
    private double price;
    private String status;

    public Room(int roomId, String roomName, double price, String status) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.price = price;
        this.status = status;
    }

    public Room(String roomName, double price, String status) {
        this.roomName = roomName;
        this.price = price;
        this.status = status;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}