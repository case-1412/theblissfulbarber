package com.example.dao;

import com.example.model.Appointment;
import com.example.util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDao {

    /*----------------------------------------------------------
     *  Connection helper
     *---------------------------------------------------------*/
    private static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Use com.mysql.cj.jdbc.Driver for MySQL 8+
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sad",
                    "root",
                    "");
        } catch (Exception e) {
            throw new RuntimeException("DB connection error", e);
        }
    }

    /*----------------------------------------------------------
     *  CREATE  (insert)
     *---------------------------------------------------------*/
    public static int save(Appointment appointment) {
        String sql = "INSERT INTO appointments " +
                     "(name, phone, date, service, time, barber, addons, price, status) " +  // added status column
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            System.out.println("TEST3");
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            System.out.println("TEST1");

            ps.setString(1, appointment.getName());
            ps.setString(2, appointment.getPhone());
            System.out.println("TEST2");
            ps.setDate  (3, Date.valueOf(appointment.getDate()));
            ps.setString(4, appointment.getService());
            ps.setString(5, appointment.getTime());
            ps.setString(6, appointment.getBarber());
            ps.setString(7, appointment.getAddons());
            ps.setBigDecimal(8, appointment.getPrice());
            ps.setString(9, appointment.getStatus());  // set status here

            return ps.executeUpdate(); // 1 = success
        } catch (Exception e) {
            System.out.println("TEST4");
            e.printStackTrace();
            return 0;
        }
    }

    /*----------------------------------------------------------
     *  READ  (all)
     *---------------------------------------------------------*/
    public static List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments ORDER BY date, time";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Appointment appt = new Appointment();
                appt.setId     (rs.getInt   ("id"));
                appt.setName   (rs.getString("name"));
                appt.setPhone  (rs.getString("phone"));
                appt.setDate   (rs.getDate  ("date").toLocalDate());
                appt.setService(rs.getString("service"));
                appt.setTime   (rs.getString("time"));
                appt.setBarber (rs.getString("barber"));
                appt.setAddons (rs.getString("addons"));
                appt.setPrice  (rs.getBigDecimal("price"));
                appt.setStatus (rs.getString("status"));  // added status here
                list.add(appt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /*----------------------------------------------------------
     *  READ  (by ID)
     *---------------------------------------------------------*/
    public static Appointment getAppointmentById(int id) {
        String sql = "SELECT * FROM appointments WHERE id = ?";
        Appointment appt = null;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    appt = new Appointment();
                    appt.setId     (rs.getInt   ("id"));
                    appt.setName   (rs.getString("name"));
                    appt.setPhone  (rs.getString("phone"));
                    appt.setDate   (rs.getDate  ("date").toLocalDate());
                    appt.setService(rs.getString("service"));
                    appt.setTime   (rs.getString("time"));
                    appt.setBarber (rs.getString("barber"));
                    appt.setAddons (rs.getString("addons"));
                    appt.setPrice  (rs.getBigDecimal("price"));
                    appt.setStatus (rs.getString("status"));  // added status here
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appt;
    }

    /*----------------------------------------------------------
     *  UPDATE
     *---------------------------------------------------------*/
    public static int update(Appointment a) {
        String sql = "UPDATE appointments SET " +
                     "name=?, phone=?, date=?, time=?, service=?, barber=?, addons=?, price=?, status=? " +  // added status here
                     "WHERE id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, a.getName());
            ps.setString(2, a.getPhone());
            ps.setDate  (3, Date.valueOf(a.getDate()));
            ps.setString(4, a.getTime());
            ps.setString(5, a.getService());
            ps.setString(6, a.getBarber());
            ps.setString(7, a.getAddons());
            ps.setBigDecimal(8, a.getPrice());
            ps.setString(9, a.getStatus());  // set status here
            ps.setInt(10, a.getId());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /*----------------------------------------------------------
     *  UPDATE STATUS ONLY
     *---------------------------------------------------------*/
    public static boolean updateStatus(int id, String status) {
        String sql = "UPDATE appointments SET status = ? WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);

            int updatedRows = ps.executeUpdate();
            return updatedRows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*----------------------------------------------------------
     *  DELETE (Optional)
     *---------------------------------------------------------*/
    public static int delete(int id) {
        String sql = "DELETE FROM appointments WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /*----------------------------------------------------------
     *  CHECK FOR CONFLICT
     *---------------------------------------------------------*/
    public static boolean hasConflict(LocalDate date, LocalTime time, String barber) {
        boolean conflict = false;

        try (Connection conn = getConnection()) {
            String sql = "SELECT time FROM appointments WHERE date = ? AND barber = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(date));
            stmt.setString(2, barber);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LocalTime existingTime = LocalTime.parse(rs.getString("time"));
                long diff = Math.abs(java.time.Duration.between(existingTime, time).toMinutes());
                if (diff < 30) {
                    conflict = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conflict;
    }

    public static boolean hasConflictExcludingId(LocalDate date, LocalTime time, String barber, int excludeId) {
        boolean conflict = false;

        try (Connection conn = getConnection()) {
            String sql = "SELECT time FROM appointments WHERE date = ? AND barber = ? AND id != ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(date));
            stmt.setString(2, barber);
            stmt.setInt(3, excludeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LocalTime existingTime = LocalTime.parse(rs.getString("time"));
                long diff = Math.abs(java.time.Duration.between(existingTime, time).toMinutes());
                if (diff < 30) {
                    conflict = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conflict;
    }

    public static boolean hasConflictWithDuration(LocalDate date, LocalTime newStart, LocalTime newEnd, String barber) {
        boolean conflict = false;

        try (Connection conn = getConnection()) {
            String sql = "SELECT time, addons FROM appointments WHERE date = ? AND barber = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(date));
            stmt.setString(2, barber);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LocalTime existingStart = LocalTime.parse(rs.getString("time"));
                String addonStr = rs.getString("addons");
                int addonCount = (addonStr == null || addonStr.isBlank()) ? 0 : addonStr.split(",").length;
                LocalTime existingEnd = existingStart.plusMinutes(30 + addonCount * 30);

                // Check for overlap
                if (!newEnd.isBefore(existingStart) && !newStart.isAfter(existingEnd)) {
                    conflict = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conflict;
    }
    
    //Count phone number
    public static int countAppointmentsByPhone(String phone) {
    int count = 0;
    try (Connection conn = DBUtil.getConnection();
         PreparedStatement stmt = conn.prepareStatement(
             "SELECT COUNT(*) FROM appointments WHERE phone = ?")) {

        stmt.setString(1, phone);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            count = rs.getInt(1);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return count;
}

}
