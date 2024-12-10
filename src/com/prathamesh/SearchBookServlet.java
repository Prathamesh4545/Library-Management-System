package com.prathamesh;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/searchBook")
public class SearchBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve search parameter
        String name = request.getParameter("search");

        // Validate input
        if (isNullOrEmpty(name)) {
            request.setAttribute("status", "Please provide all fields.");
            request.setAttribute("statusType", "alert-danger");
            request.setAttribute("statusTitle", "Error");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // SQL query to search for the book by name
        String selectSql = "SELECT * FROM books WHERE name = ?";
        try (Connection con = getDatabaseConnection();
             PreparedStatement ps = con.prepareStatement(selectSql)) {

            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    request.setAttribute("book", rs);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                } else {
                    request.setAttribute("status", "Book not found.");
                    request.setAttribute("statusType", "alert-warning");
                    request.setAttribute("statusTitle", "Warning");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
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

    private void handleError(HttpServletRequest request, HttpServletResponse response, SQLException e)
            throws ServletException, IOException {
        e.printStackTrace();
        request.setAttribute("status", "An error occurred. Please try again later.");
        request.setAttribute("statusType", "alert-danger");
        request.setAttribute("statusTitle", "Error");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
