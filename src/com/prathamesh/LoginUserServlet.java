package com.prathamesh;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;

@WebServlet("/login")
public class LoginUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password"); 

        // Validate input
        if (isNullOrEmpty(name) || isNullOrEmpty(password)) {
            request.setAttribute("status", "Please provide both username and password.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return;
        }
        
        HttpSession session = request.getSession();
        String sql = "SELECT * FROM users where name = ? and password = ?";

        try (Connection con = getDatabaseConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
        	
        	ps.setString(1, name);
        	ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    session.setAttribute("name", rs.getString("name"));
                    request.getRequestDispatcher("welcome.jsp").forward(request, response);
                } else {
                    request.setAttribute("status", "failed");
                    request.getRequestDispatcher("Login.jsp").forward(request, response);
                }
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

    private void handleError(HttpServletRequest request, HttpServletResponse response, SQLException e) throws ServletException, IOException {
        e.printStackTrace();
        request.setAttribute("status", "An error occurred. Please try again later.");
        request.getRequestDispatcher("Login.jsp").forward(request, response);
    }
}
