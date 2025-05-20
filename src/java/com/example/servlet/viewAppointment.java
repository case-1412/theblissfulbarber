package com.example.servlet;

import com.example.model.Appointment;
import com.example.dao.AppointmentDao;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/viewAppointments")
public class viewAppointment extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head><title>Appointments</title>");

            // DataTables and jQuery
            out.println("<link rel='stylesheet' href='https://cdn.datatables.net/1.13.5/css/jquery.dataTables.min.css'>");
            out.println("<script src='https://code.jquery.com/jquery-3.6.0.min.js'></script>");
            out.println("<script src='https://cdn.datatables.net/1.13.5/js/jquery.dataTables.min.js'></script>");

            // Styling
            out.println("<style>");
            out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f6f8; margin: 0; }");
            out.println("header { background-color: #2c3e50; color: white; padding: 20px; text-align: center; font-size: 26px; font-weight: bold; }");
            out.println("main { padding: 30px; max-width: 1200px; margin: auto; }");
            out.println("h2 { text-align: center; color: #333; margin-bottom: 20px; }");
            out.println("table { width: 100%; border-collapse: collapse; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
            out.println("th, td { padding: 12px 15px; text-align: center; border: 1px solid #ddd; }");
            out.println("th { background-color: #3498db; color: white; }");
            out.println("tr:nth-child(even) { background-color: #f9f9f9; }");
            out.println("tr:hover { background-color: #f1f1f1; }");
            out.println("a { text-decoration: none; color: #2980b9; font-weight: bold; }");
            out.println("a:hover { color: #1c5980; }");
            out.println(".buttons-container { text-align: center; margin-top: 30px; }");
            out.println("button { background-color: #3498db; color: white; border: none; padding: 10px 20px; font-size: 16px; border-radius: 5px; cursor: pointer; margin: 5px; }");
            out.println("button:hover { background-color: #2980b9; }");
            out.println("</style>");

            out.println("</head><body>");

            // Header
            out.println("<header>Appointment Management</header>");
            out.println("<main>");
            out.println("<h2>All Appointments</h2>");

            // Table
            out.println("<table id='appointmentTable'>");
            out.println("<thead><tr>");
            out.println("<th>ID</th><th>Name</th><th>Phone</th><th>Date</th><th>Time</th>");
            out.println("<th>Service</th><th>Barber</th><th>Add-ons</th><th>Price (RM)</th><th>Edit</th><th>Delete</th>");
            out.println("</tr></thead><tbody>");

            List<Appointment> list = AppointmentDao.getAllAppointments();
            if (list == null || list.isEmpty()) {
                out.println("<tr><td colspan='11'>No appointments found.</td></tr>");
            } else {
                for (Appointment a : list) {
                    out.println("<tr>");
                    out.println("<td>" + a.getId() + "</td>");
                    out.println("<td>" + a.getName() + "</td>");
                    out.println("<td>" + a.getPhone() + "</td>");
                    out.println("<td>" + a.getDate() + "</td>");
                    out.println("<td>" + a.getTime() + "</td>");
                    out.println("<td>" + a.getService() + "</td>");
                    out.println("<td>" + a.getBarber() + "</td>");
                    out.println("<td>" + a.getAddons() + "</td>");
                    out.println("<td>" + a.getPrice() + "</td>");
                    out.println("<td><a href='editAppointment.jsp?id=" + a.getId() + "'>Edit</a></td>");
                    out.println("<td><a href='DeleteAppointment?id=" + a.getId() + "' onclick=\"return confirm('Are you sure you want to delete this appointment?');\">Delete</a></td>");
                    out.println("</tr>");
                }
            }

            out.println("</tbody></table>");

            // Buttons
            out.println("<div class='buttons-container'>");
            out.println("<form action='index.html' method='get' style='display:inline;'>");
            out.println("<button type='submit'>Back</button>");
            out.println("</form>");
            out.println("<a href='BookAppointment.jsp'><button>Add New Appointment</button></a>");
            out.println("<a href='provideOffer.jsp'><button>Add New Offer</button></a>");
            out.println("</div>");

            out.println("</main>");

            // DataTables Initialization
            out.println("<script>");
            out.println("$(document).ready(function() { $('#appointmentTable').DataTable(); });");
            out.println("</script>");

            out.println("</body></html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Displays all appointments in a nice table format with sorting, searching and actions.";
    }
}
