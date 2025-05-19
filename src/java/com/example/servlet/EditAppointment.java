package com.example.servlet;

import com.example.dao.AppointmentDao;
import com.example.model.Appointment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@WebServlet("/EditAppointment")
public class EditAppointment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Fetch appointment ID from the request parameter
        int id = Integer.parseInt(request.getParameter("id"));

        // Get the Appointment from the DB by ID
        Appointment appointment = AppointmentDao.getAppointmentById(id);

        if (appointment == null) {
            // Handle case where the appointment doesn't exist
            response.sendRedirect("error.jsp?message=Appointment not found.");
            return;
        }

        // Set appointment object as request attribute and forward to JSP
        request.setAttribute("appointment", appointment);
        request.getRequestDispatcher("editAppointment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        LocalDate date = LocalDate.parse(request.getParameter("date"));
        String time = request.getParameter("time");
        String service = request.getParameter("service");
        String barber = request.getParameter("barber");
        String[] addons = request.getParameterValues("addons");
        String addonList = (addons != null) ? String.join(", ", addons) : "";
        
        BigDecimal price = BigDecimal.ZERO;
        try {
            // Safely parse the price (check for invalid input)
            price = new BigDecimal(request.getParameter("price"));
        } catch (NumberFormatException e) {
            e.printStackTrace(); // Log the error if price is invalid
        }

        // Create updated Appointment object
        Appointment updated = new Appointment();
        updated.setId(id);
        updated.setName(name);
        updated.setDate(date);
        updated.setTime(time);
        updated.setService(service);
        updated.setBarber(barber);
        updated.setAddons(addonList);
        updated.setPrice(price);

        // Update the appointment in the DB
        int status = AppointmentDao.update(updated);

        if (status > 0) {
            // Successfully updated, redirect to view appointments
            response.sendRedirect("viewAppointments");
        } else {
            // Failed to update, redirect to error page
            response.sendRedirect("error.jsp?message=Failed to update appointment.");
        }
    }
}
