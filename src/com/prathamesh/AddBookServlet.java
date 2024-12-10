package com.prathamesh;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.sql.*;

@WebServlet("/addBook")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10) // 10 MB max
public class AddBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        Part imgPart = request.getPart("img");
        Part pdfPart = request.getPart("bookPdf");

        // Validate input
        if (isNullOrEmpty(name) || isNullOrEmpty(author) || isNullOrEmpty(genre) || pdfPart == null) {
            request.setAttribute("status", "Please provide all fields.");
            request.setAttribute("statusType", "alert-danger");
            request.setAttribute("statusTitle", "Error");
            request.getRequestDispatcher("AddBook.jsp").forward(request, response);
            return;
        }

        String sql = "INSERT INTO books (name, author, genre, \"isAvaliable\", img, book_pdf) VALUES (?, ?, ?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = getDatabaseConnection();
            con.setAutoCommit(false); // Disable auto-commit mode

            ps = con.prepareStatement(sql);

            // Set the parameters
            ps.setString(1, name);
            ps.setString(2, author);
            ps.setString(3, genre);
            ps.setBoolean(4, true); // Assuming the book is available when added
            ps.setBytes(5, imgPart.getInputStream().readAllBytes()); // Image Blob
            ps.setBytes(6, pdfPart.getInputStream().readAllBytes()); // PDF Blob

            int result = ps.executeUpdate();
            if (result > 0) {
                con.commit(); // Commit the transaction
                request.setAttribute("status", "Book added successfully!");
                request.setAttribute("statusType", "alert-success");
                request.setAttribute("statusTitle", "Success");
            } else {
                con.rollback();
                request.setAttribute("status", "Error adding book.");
                request.setAttribute("statusType", "alert-danger");
                request.setAttribute("statusTitle", "Error");
            }
            request.getRequestDispatcher("AddBook.jsp").forward(request, response);
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            handleError(request, response, e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        request.getRequestDispatcher("AddBook.jsp").forward(request, response);
    }
}
