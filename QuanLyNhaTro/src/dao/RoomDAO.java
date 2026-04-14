package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Room;

public class RoomDAO {

    // LẤY DANH SÁCH PHÒNG
    public List<Room> getAll() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM Room";

        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Room r = new Room(
                        rs.getInt("room_id"),
                        rs.getString("room_name"),
                        rs.getDouble("price"),
                        rs.getString("status")
                );
                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // THÊM PHÒNG
    public boolean insert(Room r) {
        String sql = "INSERT INTO Room(room_name, price, status) VALUES (?, ?, ?)";

        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, r.getRoomName());
            ps.setDouble(2, r.getPrice());
            ps.setString(3, r.getStatus());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // CẬP NHẬT PHÒNG
    public boolean update(Room r) {
        String sql = "UPDATE Room SET room_name=?, price=?, status=? WHERE room_id=?";

        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, r.getRoomName());
            ps.setDouble(2, r.getPrice());
            ps.setString(3, r.getStatus());
            ps.setInt(4, r.getRoomId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Room findById(int id) {
        for (Room r : getAll()) {
            if (r.getRoomId() == id) {
                return r;
            }
        }
        return null;
    }

    // XÓA PHÒNG
    public boolean delete(int id) {
        String sql = "DELETE FROM Room WHERE room_id=?";

        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}