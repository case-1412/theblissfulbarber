package com.example.dao;

import com.example.model.Appointment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDao {

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/assignment2", "root", "");
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }

    // Method to save a new appointment to the database
    public static int save(Appointment appointment) {
        int status = 0;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO appointments (name, date, service, time, barber, addons, price) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, appointment.getName());
            ps.setDate(2, java.sql.Date.valueOf(appointment.getDate()));
            ps.setString(3, appointment.getService());
            ps.setString(4, appointment.getTime());
            ps.setString(5, appointment.getBarber());
            ps.setString(6, appointment.getAddons());
            ps.setBigDecimal(7, appointment.getPrice());
            status = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    // Method to get all appointments from the database
    public static List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM appointments");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(rs.getInt("id"));
                appointment.setName(rs.getString("name"));
                appointment.setDate(rs.getDate("date").toLocalDate());
                appointment.setService(rs.getString("service"));
                appointment.setTime(rs.getString("time"));
                appointment.setBarber(rs.getString("barber"));
                appointment.setAddons(rs.getString("addons"));
                appointment.setPrice(rs.getBigDecimal("price"));
                list.add(appointment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Method to get a specific appointment by ID
    public static Appointment getAppointmentById(int id) {
        Appointment appointment = null;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM appointments WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    appointment = new Appointment();
                    appointment.setId(rs.getInt("id"));
                    appointment.setName(rs.getString("name"));
                    appointment.setDate(rs.getDate("date").toLocalDate());
                    appointment.setService(rs.getString("service"));
                    appointment.setTime(rs.getString("time"));
                    appointment.setBarber(rs.getString("barber"));
                    appointment.setAddons(rs.getString("addons"));
                    appointment.setPrice(rs.getBigDecimal("price"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointment;
    }
   // Method to update an existing appointment
public static int update(Appointment a) {
    int status = 0;
    String sql = "UPDATE appointments SET name=?, date=?, time=?, service=?, barber=?, addons=?, price=? WHERE id=?";

    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, a.getName());
        ps.setDate(2, java.sql.Date.valueOf(a.getDate()));  // Ensure LocalDate is converted to SQL Date
        ps.setString(3, a.getTime());
        ps.setString(4, a.getService());
        ps.setString(5, a.getBarber());
        ps.setString(6, a.getAddons());
        ps.setBigDecimal(7, a.getPrice());
        ps.setInt(8, a.getId());

        status = ps.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace(); // This logs the error to console. You can also log it to file for production use.
    }

    return status;
}


// Method to delete an appointment by ID
public static int delete(int id) {
    int status = 0;
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement("DELETE FROM appointments WHERE id=?")) {
        ps.setInt(1, id);
        status = ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return status;
}





}
