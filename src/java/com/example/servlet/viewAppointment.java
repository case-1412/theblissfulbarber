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
public class ViewAppointment extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head><title>Appointments</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f9f9f9; margin: 0; padding: 0; }");
            out.println("header { background-color: #333; color: white; padding: 10px 0; text-align: center; font-size: 24px; }");
            out.println("main { padding: 20px; }");
            out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
            out.println("th, td { padding: 12px; text-align: center; border: 1px solid #ddd; }");
            out.println("th { background-color: #4CAF50; color: white; }");
            out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
            out.println("tr:hover { background-color: #ddd; }");
            out.println("a { text-decoration: none; color: #007BFF; }");
            out.println("a:hover { color: #0056b3; }");
            out.println("button { padding: 10px 20px; background-color: #007BFF; border: none; color: white; font-size: 16px; cursor: pointer; margin: 5px; }");
            out.println("button:hover { background-color: #0056b3; }");
            out.println(".buttons-container { display: flex; justify-content: center; margin-top: 20px; }");
            out.println("</style>");
            out.println("</head><body>");
            
            out.println("<header>Appointment Management</header>");
            out.println("<main>");
            out.println("<h2>Appointment List</h2>");
            
            // Fetch appointments
            List<Appointment> list = AppointmentDao.getAllAppointments();
            
            out.print("<table>");
            out.print("<tr>"
                    + "<th>ID</th><th>Name</th><th>Date</th><th>Time</th>"
                    + "<th>Service</th><th>Barber</th><th>Add-ons</th><th>Price (RM)</th>"
                    + "<th>Edit</th><th>Delete</th>"
                    + "</tr>");
            
            if (list == null || list.isEmpty()) {
                out.println("<tr><td colspan='10'>No appointments found.</td></tr>");
            } else {
                for (Appointment a : list) {
                    out.print("<tr>"
                            + "<td>" + a.getId() + "</td>"
                            + "<td>" + a.getName() + "</td>"
                            + "<td>" + a.getDate() + "</td>"
                            + "<td>" + a.getTime() + "</td>"
                            + "<td>" + a.getService() + "</td>"
                            + "<td>" + a.getBarber() + "</td>"
                            + "<td>" + a.getAddons() + "</td>"
                            + "<td>" + a.getPrice() + "</td>"
                            + "<td><a href='editAppointment.jsp?id=" + a.getId() + "'>Edit</a></td>"
                            + "<td><a href='DeleteAppointment?id=" + a.getId() + "' onclick=\"return confirm('Are you sure?')\">Delete</a></td>"
                            + "</tr>");
                }
            }
            
            out.print("</table>");
            
            // Button container below the table
            out.println("<div class='buttons-container'>");
            out.println("<form action='index.html' method='get'>");
            out.println("<button type='submit'>Back</button>");
            out.println("</form>");
            
            out.println("<a href='BookAppointment.jsp'><button>Add New Appointment</button></a>");
            out.println("</div>");
            
            out.println("</main>");
            
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
        return "Displays all appointments in a table format.";
    }
}
