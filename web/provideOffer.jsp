<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Provide Offer</title>
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
    
    
</head>
<body>

<div class="form-container">
    <h2>Provide Offer</h2>
    
<form action="SaveOfferServlet" method="post">
    <label for="offerName">Offer Name:</label>
    <input type="text" id="offerName" name="offerName" required>

    <label for="appointmentId">Appointment ID:</label>
    <input type="text" id="appointmentId" name="appointmentId" required>

    <label for="offerType">Offer Type:</label>
    <select id="offerType" name="offerType" onchange="toggleOfferDetails()" required>
        <option value="">Select Type</option>
        <option value="discount">Discount</option>
        <option value="addon">Free Add-ons</option>
    </select>

    <div id="discountSection" style="display:none;">
        <label for="discountPercentage">Discount Percentage:</label>
        <input type="number" id="discountPercentage" name="discountPercentage" min="0" max="100">
    </div>

    <div id="addonSection" style="display:none;">
        <label for="addons">Choose Add-ons:</label>
        <select id="addons" name="addons">
            <option value="free_service">Massage</option>
            <option value="product_sample">Facial</option>
            <option value="extended_support">Scalp treatment</option>
        </select>
    </div>

    <button type="submit" class="submit-btn">Save Offer</button>
    <button type="button" onclick="window.location.href='index.html'">Back to Home Page</button>
</form>

<script>
function toggleOfferDetails() {
    var offerType = document.getElementById("offerType").value;
    document.getElementById("discountSection").style.display = (offerType === "discount") ? "block" : "none";
    document.getElementById("addonSection").style.display = (offerType === "addon") ? "block" : "none";
}
</script>


</div>

</body>
</html>
