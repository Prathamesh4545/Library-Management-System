package com.prathamesh;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/updateBook")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10) // 10 MB max
public class UpdateBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        Part imgPart = request.getPart("img");
        Part pdfPart = request.getPart("bookPdf");

        // Validate input
        if (isNullOrEmpty(name) || isNullOrEmpty(author) || isNullOrEmpty(genre)) {
            request.setAttribute("status", "Please provide all fields.");
            request.setAttribute("statusType", "alert-danger");
            request.setAttribute("statusTitle", "Error");
            request.getRequestDispatcher("UpdateBook.jsp").forward(request, response);
            return;
        }

        String updateSql = "UPDATE books SET name = ?, author = ?, genre = ?, img = ?, book_pdf = ? WHERE \"bookId\" = ?";
        String selectSql = "SELECT img, book_pdf FROM books WHERE \"bookId\" = ?";

        Connection con = null;
        PreparedStatement psSelect = null;
        PreparedStatement psUpdate = null;
        ResultSet rs = null;
        try {
            con = getDatabaseConnection();
            con.setAutoCommit(false); // Disable auto-commit mode

            // Retrieve existing image and PDF if new ones are not provided
            psSelect = con.prepareStatement(selectSql);
            psSelect.setInt(1, id);
            rs = psSelect.executeQuery();

            byte[] existingImg = null;
            byte[] existingPdf = null;
            if (rs.next()) {
                existingImg = rs.getBytes("img");
                existingPdf = rs.getBytes("book_pdf");
            }

            // Use new image and PDF if provided, else keep existing ones
            byte[] imgData = imgPart != null && imgPart.getSize() > 0 ? imgPart.getInputStream().readAllBytes() : existingImg;
            byte[] pdfData = pdfPart != null && pdfPart.getSize() > 0 ? pdfPart.getInputStream().readAllBytes() : existingPdf;

            psUpdate = con.prepareStatement(updateSql);
            psUpdate.setString(1, name);
            psUpdate.setString(2, author);
            psUpdate.setString(3, genre);
            psUpdate.setBytes(4, imgData);
            psUpdate.setBytes(5, pdfData);
            psUpdate.setInt(6, id);

            int result = psUpdate.executeUpdate();
            if (result > 0) {
                con.commit(); // Commit the transaction
                request.setAttribute("status", "Book updated successfully!");
                request.setAttribute("statusType", "alert-success");
                request.setAttribute("statusTitle", "Success");
            } else {
                con.rollback(); // Rollback the transaction
                request.setAttribute("status", "Error updating book.");
                request.setAttribute("statusType", "alert-danger");
                request.setAttribute("statusTitle", "Error");
            }
            request.getRequestDispatcher("UpdateBook.jsp").forward(request, response);
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Rollback the transaction on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            handleError(request, response, e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (psSelect != null) psSelect.close();
                if (psUpdate != null) psUpdate.close();
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
        request.getRequestDispatcher("UpdateBook.jsp").forward(request, response);
    }
}
