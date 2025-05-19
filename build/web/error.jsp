<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Error</title>
    <style>
        body {
            background-color: #f8d7da;
            font-family: Arial, sans-serif;
        }
        .error-container {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
            padding: 20px;
            margin: 20px;
            border-radius: 8px;
        }
        .error-container a {
            color: #721c24;
            text-decoration: none;
            font-weight: bold;
        }
    </style>
</head>
<body>

<div class="error-container">
    <h2>An error occurred while processing your request.</h2>
    <p>${param.message}</p>
    <a href="index.html">Return to Home</a>
</div>

</body>
</html>
