package com.prathamesh;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet("/signUp")
public class SignUpUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phoneNumber = request.getParameter("phoneNumber");

        // Validate input
        if (isNullOrEmpty(name) || isNullOrEmpty(email) || isNullOrEmpty(password) || isNullOrEmpty(phoneNumber)) {
            request.setAttribute("status", "Please provide all mandatory fields.");
            request.getRequestDispatcher("SignUp.jsp").forward(request, response);
            return;
        }
        
        String sql = "INSERT INTO users (name, email, phone_number, password) VALUES (?, ?, ?, ?)";
       

        try (Connection con = getDatabaseConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Set the parameters
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phoneNumber);
            ps.setString(4, password);

            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Registered successfully");
                request.getRequestDispatcher("Login.jsp").forward(request, response);
            } else {
                request.setAttribute("status", "Registration failed.");
                request.getRequestDispatcher("SignUp.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            handleError(request, response, e);
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private Connection getDatabaseConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL Driver not found", e);
        }
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/practiceDB", "postgres", "root8080");
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, SQLException e)
            throws ServletException, IOException {
        e.printStackTrace();
        request.setAttribute("status", "An error occurred. Please try again later.");
        request.getRequestDispatcher("SignUp.jsp").forward(request, response);
    }
}
