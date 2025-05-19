package com.example.servlet;

import com.example.model.Appointment;
import com.example.dao.AppointmentDao;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/BookAppointmentServlet")
public class BookAppointmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String dateStr = request.getParameter("date");
        String service = request.getParameter("service");
        String time = request.getParameter("time");
        String barber = request.getParameter("barber");
        String[] addons = request.getParameterValues("addons");
        String priceStr = request.getParameter("price");

        if (name == null || dateStr == null || service == null || time == null || barber == null || priceStr == null ||
                name.isEmpty() || dateStr.isEmpty() || service.isEmpty() || time.isEmpty() || barber.isEmpty() || priceStr.isEmpty()) {
            response.sendRedirect("error.jsp?message=" + URLEncoder.encode("All fields are required.", "UTF-8"));
            return;
        }

        LocalDate date;
        try {
            date = LocalDate.parse(dateStr);
            if (date.isBefore(LocalDate.now())) {
                response.sendRedirect("error.jsp?message=" + URLEncoder.encode("Date must be in the future.", "UTF-8"));
                return;
            }
        } catch (Exception e) {
            response.sendRedirect("error.jsp?message=" + URLEncoder.encode("Invalid date format.", "UTF-8"));
            return;
        }

        BigDecimal price;
        try {
            price = new BigDecimal(priceStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("error.jsp?message=" + URLEncoder.encode("Invalid price.", "UTF-8"));
            return;
        }

        String addonList = (addons != null) ? String.join(", ", addons) : "";

        Appointment appointment = new Appointment();
        appointment.setName(name);
        appointment.setDate(date);
        appointment.setService(service);
        appointment.setTime(time);
        appointment.setBarber(barber);
        appointment.setAddons(addonList);
        appointment.setPrice(price);

        int status = AppointmentDao.save(appointment);
        if (status > 0) {
            response.sendRedirect("success.jsp");
        } else {
            response.sendRedirect("error.jsp?message=" + URLEncoder.encode("Failed to save appointment.", "UTF-8"));
        }
    }
}
