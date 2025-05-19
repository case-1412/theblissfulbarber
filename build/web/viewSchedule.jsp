<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Appointment</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7f9;
            margin: 0;
            padding: 0;
        }
        .form-container {
            width: 400px;
            margin: 80px auto;
            padding: 30px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .form-container h2 {
            text-align: center;
            color: #333;
        }
        .form-container label {
            display: block;
            margin: 15px 0 5px;
            font-weight: bold;
        }
        .form-container input, .form-container select {
            width: 100%;
            padding: 10px;
            font-size: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .form-container button {
            width: 100%;
            padding: 12px;
            background-color: #4285f4;
            color: white;
            border: none;
            border-radius: 5px;
            margin-top: 20px;
            font-size: 16px;
            cursor: pointer;
        }
        .form-container button:hover {
            background-color: #3367d6;
        }
    </style>
</head>
<body>

<div class="form-container">
    <h2>Book Appointment</h2>
    <form action="BookAppointmentServlet" method="post">

        <label for="name">Your Name:</label>
        <input type="text" name="name" id="name" required>

        <label for="date">Date:</label>
        <input type="date" name="date" id="date" min="<%= LocalDate.now() %>" required>

        <label for="service">Service:</label>
        <select name="service" id="service" required>
            <option value="">-- Select a Service --</option>
            <option value="Haircut">Haircut</option>
            <option value="Shaving">Shaving</option>
            <option value="Hair Coloring">Hair Coloring</option>
            <option value="Beard Trim">Beard Trim</option>
        </select>

        <label for="time">Preferred Time:</label>
        <input type="time" name="time" id="time" required>

        <button type="submit">Book Appointment</button>
    </form>
</div>

</body>
</html>
