package com.prathamesh;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/downloadPdf")
public class downloadPdfServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int bookId = Integer.parseInt(request.getParameter("bookId"));

		String sql = "SELECT book_pdf FROM books WHERE  \"bookId\"= ?";

		try (Connection con = getDatabaseConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, bookId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					byte[] pdfData = rs.getBytes("book_pdf");
					response.setContentType("application/pdf");
					response.setContentLength(pdfData.length);
					response.setHeader("Content-Disposition", "attachment; filename=\"book.pdf\"");
					try (OutputStream os = response.getOutputStream()) {
						os.write(pdfData);
					}
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND, "Book not found");
				}
			}
		} catch (SQLException e) {
			throw new ServletException("Database error while retrieving PDF", e);
		}

	}

	private Connection getDatabaseConnection() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return DriverManager.getConnection("jdbc:postgresql://localhost:5432/practiceDB", "postgres", "root8080");
	}
}
