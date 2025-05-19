<%@ page import="com.example.dao.AppointmentDao" %>
<%@ page import="com.example.model.Appointment" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    Appointment appointment = AppointmentDao.getAppointmentById(id);
    if (appointment == null) {
%>
    <h2 style="text-align:center; margin-top:50px;">Appointment not found.</h2>
<%
    return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Appointment</title>
    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #d4fc79, #96e6a1);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .form-container {
            background-color: #fff;
            padding: 35px 30px;
            border-radius: 20px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 450px;
        }
        .form-container h2 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 25px;
        }
        label {
            display: block;
            margin-top: 15px;
            margin-bottom: 6px;
            font-weight: 600;
            color: #333;
        }
        input[type="text"],
        input[type="date"],
        input[type="time"],
        select {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #ccc;
            border-radius: 8px;
            background-color: #f9f9f9;
            font-size: 15px;
            margin-bottom: 10px;
        }
        .addons-group {
            margin-top: 10px;
            margin-bottom: 15px;
        }
        .addons-group label {
            display: block;
            margin: 6px 0;
            font-weight: normal;
        }
        .addons-group input[type="checkbox"] {
            margin-right: 8px;
        }
        button {
            width: 100%;
            padding: 12px;
            font-size: 16px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            margin-top: 15px;
        }
        .submit-btn {
            background-color: #27ae60;
            color: #fff;
        }
        .submit-btn:hover {
            background-color: #219150;
        }
        .back-button {
            background-color: #bdc3c7;
            color: #fff;
        }
        .back-button:hover {
            background-color: #95a5a6;
        }
    </style>
    <script>
        function calculatePrice() {
            const basePrice = 20;
            const addons = document.querySelectorAll('input[name="addons"]:checked');
            const addonPrice = addons.length * 5;
            const total = basePrice + addonPrice;
            document.getElementById('price').value = total.toFixed(2);
            return true;
        }
    </script>
</head>
<body>

<div class="form-container">
    <h2>Edit Appointment</h2>
    <form action="EditAppointment" method="post" onsubmit="return calculatePrice();">
        <input type="hidden" name="id" value="<%= appointment.getId() %>">

        <label for="name">Your Name</label>
        <input type="text" name="name" value="<%= appointment.getName() %>" required>

        <label for="date">Date</label>
        <input type="date" name="date" value="<%= appointment.getDate() %>" min="<%= LocalDate.now() %>" required>

        <label for="time">Time</label>
        <input type="time" name="time" value="<%= appointment.getTime() %>" required>

        <label for="service">Service</label>
        <select name="service" required>
            <option value="Haircut" <%= appointment.getService().equals("Haircut") ? "selected" : "" %>>Haircut</option>
            <option value="Shaving" <%= appointment.getService().equals("Shaving") ? "selected" : "" %>>Shaving</option>
            <option value="Hair Coloring" <%= appointment.getService().equals("Hair Coloring") ? "selected" : "" %>>Hair Coloring</option>
            <option value="Beard Trim" <%= appointment.getService().equals("Beard Trim") ? "selected" : "" %>>Beard Trim</option>
        </select>

        <label for="barber">Choose Barber</label>
        <select name="barber" required>
            <option value="John" <%= appointment.getBarber().equals("John") ? "selected" : "" %>>John</option>
            <option value="Mike" <%= appointment.getBarber().equals("Mike") ? "selected" : "" %>>Mike</option>
            <option value="Alex" <%= appointment.getBarber().equals("Alex") ? "selected" : "" %>>Alex</option>
        </select>

        <label>Add-ons</label>
        <div class="addons-group">
            <label><input type="checkbox" name="addons" value="Massage" <%= appointment.getAddons().contains("Massage") ? "checked" : "" %>> Massage</label>
            <label><input type="checkbox" name="addons" value="Facial" <%= appointment.getAddons().contains("Facial") ? "checked" : "" %>> Facial</label>
            <label><input type="checkbox" name="addons" value="Scalp Treatment" <%= appointment.getAddons().contains("Scalp Treatment") ? "checked" : "" %>> Scalp Treatment</label>
        </div>

        <input type="hidden" name="price" id="price" value="<%= appointment.getPrice() %>">

        <button type="submit" class="submit-btn">Update Appointment</button>
    </form>

    <form action="viewAppointments" method="get">
        <button type="submit" class="back-button">Back to Appointments</button>
    </form>
</div>

</body>
</html>
