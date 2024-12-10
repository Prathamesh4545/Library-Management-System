package com.prathamesh;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/deleteBook")
public class DeleteBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String sql = "DELETE FROM books WHERE \"bookId\" = ?";
		try (Connection con = getDatabaseConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			int result = ps.executeUpdate();
			if (result > 0) {
				request.setAttribute("status", "Book deleted successfully!");
			} else {
				request.setAttribute("status", "Error deleting book.");
			}
			request.getRequestDispatcher("BookList.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("status", "An error occurred. Please try again later.");
			request.getRequestDispatcher("BookList.jsp").forward(request, response);
		}
	}
	private Connection getDatabaseConnection() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DriverManager.getConnection("jdbc:postgresql://localhost:5432/practiceDB", "postgres", "root8080");
	}

}
