package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Rental;

public class RentalDAO {
	
	public Rental findById(int id) {
	    for (Rental r : getAll()) {
	        if (r.getRentalId() == id) {
	            return r;
	        }
	    }
	    return null;
	}

    public List<Rental> getAll() {
        List<Rental> list = new ArrayList<>();
        String sql = "SELECT * FROM Rental";

        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rental r = new Rental(
                        rs.getInt("rental_id"),
                        rs.getInt("room_id"),
                        rs.getInt("customer_id"),
                        rs.getDate("date_rent")
                );
                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(Rental r) {
        String sql = "INSERT INTO Rental(room_id, customer_id, date_rent) VALUES (?, ?, ?)";

        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, r.getRoomId());
            ps.setInt(2, r.getCustomerId());
            ps.setDate(3, r.getDateRent());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM Rental WHERE rental_id=?";

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