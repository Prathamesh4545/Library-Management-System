<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Library Management System</title>
<link rel="stylesheet" href="CSS/Style.css">
</head>
<body>

	<jsp:include page="header.jsp" />
	<section class="welcome">
		<h2>Welcome to Our Library</h2>
		<p>Manage your books, track issues, and search for new arrivals
			all in one place.</p>
	</section>

	<section class="search-section">
		<h2>Search for a Book</h2>
		<div class="container mt-5 col-6">
			<c:if test="${not empty status}">
				<div class="alert ${statusType} alert-dismissible fade show"
					role="alert">
					<strong>${statusTitle}</strong> ${status}
					<button type="button" class="btn-close" data-bs-dismiss="alert"
						aria-label="Close"></button>
				</div>
			</c:if>
		</div>
		<form action="searchBook" method="post">
			<input type="text" name="search"
				placeholder="Enter book title or author" required>
			<button type="submit">Search</button>
		</form>
	</section>

	<section class="book-catalog">
		<h2>Books List</h2>
		<div class="book-list">
			<%
			String searchTerm = request.getParameter("search");
			String sql;
			if (searchTerm != null && !searchTerm.isEmpty()) {
				sql = "SELECT * FROM books WHERE name LIKE ? OR author LIKE ?";
			} else {
				sql = "SELECT * FROM books"; // Default to all books if no search term 
			}
			String url = "jdbc:postgresql://localhost:5432/practiceDB";
			String username = "postgres";
			String password = "root8080";
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection(url, username, password);
				ps = con.prepareStatement(sql);
				if (searchTerm != null && !searchTerm.isEmpty()) {
					String searchPattern = "%" + searchTerm + "%";
					ps.setString(1, searchPattern);
					ps.setString(2, searchPattern);
				}
				rs = ps.executeQuery();

				while (rs.next()) {
					byte[] imgData = rs.getBytes("img"); // Get the image byte data from the database
					String imgBase64 = null;

					// Convert byte data to Base64 encoding
					if (imgData != null) {
				imgBase64 = "data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(imgData);
					}
			%>
			<div class="book-item">
				<!-- Use Base64 data for the image source -->
				<img
					src="<%=imgBase64 != null ? imgBase64 : "images/placeholder.png"%>"
					alt="Book Image"
					onerror="this.onerror=null;this.src='images/placeholder.png';">
				<h3>
					<%=rs.getString(2)%><br>
				</h3>
				<p>
					Author:
					<%=rs.getString(3)%><br> Genre:
					<%=rs.getString(4)%><br> Available:
					<%=rs.getBoolean(5) ? "Yes" : "No"%><br>
				</p>
				<!-- Download button that links directly to the PDF file -->
				<a href="downloadPdf?bookId=<%=rs.getInt(1)%>">
					<button type="button">Download PDF</button>
				</a>
			</div>
			<%
			}
			} catch (Exception e) {
			e.printStackTrace();
			} finally {
			try {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (con != null)
				con.close();
			} catch (SQLException e) {
			e.printStackTrace();
			}
			}
			%>

		</div>
	</section>
	<jsp:include page="footer.jsp" />

	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
		integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
		integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
		crossorigin="anonymous"></script>

</body>
</html>
