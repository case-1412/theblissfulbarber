<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Appointment</title>
    <style>
        * {
            box-sizing: border-box;
        }
        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #a1c4fd, #c2e9fb);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .form-container {
            background-color: #fff;
            padding: 40px 35px;
            border-radius: 20px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
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
            margin-bottom: 8px;
            font-weight: 600;
            color: #333;
        }
        input[type="text"],
        input[type="date"],
        input[type="time"],
        select {
            width: 100%;
            padding: 10px 12px;
            margin-bottom: 20px;
            border-radius: 8px;
            border: 1px solid #ccc;
            font-size: 15px;
            background-color: #f9f9f9;
            transition: border-color 0.3s;
        }
        input:focus, select:focus {
            border-color: #4285f4;
            outline: none;
        }
        .addons-group {
            margin-bottom: 20px;
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
            border: none;
            border-radius: 8px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .submit-btn {
            background-color: #4285f4;
            color: #fff;
            margin-bottom: 15px;
        }
        .submit-btn:hover {
            background-color: #3367d6;
        }
        .back-button {
            background-color: #999;
            color: #fff;
        }
        .back-button:hover {
            background-color: #777;
        }

        /* Responsive for small devices */
        @media (max-width: 500px) {
            .form-container {
                padding: 30px 20px;
            }
        }
    </style>
    <script>
        function calculatePrice() {
            const basePrice = 20;
            const addons = document.querySelectorAll('input[name="addons"]:checked');
            const addonPrice = addons.length * 5;
            const total = basePrice + addonPrice;
            document.getElementById('price').value = total.toFixed(2);
        }
    </script>
</head>
<body>

<div class="form-container">
    <h2>Book Appointment</h2>
    <form action="BookAppointmentServlet" method="post" onsubmit="calculatePrice()">

        <label for="name">Your Name</label>
        <input type="text" name="name" id="name" required>

        <label for="date">Date</label>
        <input type="date" name="date" id="date" min="<%= LocalDate.now() %>" required>

        <label for="service">Service</label>
        <select name="service" id="service" required>
            <option value="">-- Select a Service --</option>
            <option value="Haircut">Haircut</option>
            <option value="Shaving">Shaving</option>
            <option value="Hair Coloring">Hair Coloring</option>
            <option value="Beard Trim">Beard Trim</option>
        </select>

        <label for="barber">Choose Barber</label>
        <select name="barber" id="barber" required>
            <option value="">-- Select a Barber --</option>
            <option value="John">John</option>
            <option value="Mike">Mike</option>
            <option value="Alex">Alex</option>
        </select>

        <label>Add-ons</label>
        <div class="addons-group">
            <label><input type="checkbox" name="addons" value="Massage"> Massage</label>
            <label><input type="checkbox" name="addons" value="Facial"> Facial</label>
            <label><input type="checkbox" name="addons" value="Scalp Treatment"> Scalp Treatment</label>
        </div>

        <!-- Hidden price field -->
        <input type="hidden" name="price" id="price">

        <label for="time">Preferred Time</label>
        <input type="time" name="time" id="time" required>

        <button type="submit" class="submit-btn">Book Appointment</button>
    </form>

    <form action="index.html" method="get">
        <button type="submit" class="back-button">Back to Homepage</button>
    </form>
</div>

</body>
</html>
