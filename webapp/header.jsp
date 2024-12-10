<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> <!-- For responsive design -->
    <title>Library Management System</title>
    <link rel="stylesheet" href="CSS/Style.css"> <!-- Ensure the path is correct -->
</head>
<body>
    <header>
        <div class="logo">
            <h1>Library Management System</h1>
        </div>
        <nav>
            <ul>
                <li><a href="index.jsp">Home</a></li>
                <li><a href="Contact.jsp">Contact</a></li>

                <%-- Check if the user is logged in --%>
                <%
                    // Retrieve user name from session
                    String name = (String) session.getAttribute("name");
                    if (name != null) {  // If the user is logged in
                %>
                    <li><a href="profile.jsp">Welcome, <%= name %></a></li>
                    <li><a href="Logout.jsp">Logout</a></li> <!-- Ensure Logout.jsp properly ends session -->
                <% 
                    } else {  // If the user is not logged in
                %>
                    <li><a href="Login.jsp">Login</a></li>
                    <li><a href="SignUp.jsp">Sign Up</a></li>
                <% 
                    }
                %>
            </ul>
        </nav>
    </header>
</body>
</html>
