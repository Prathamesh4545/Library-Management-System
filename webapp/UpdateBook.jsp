<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.util.logging.*"%>

<%
request.getSession(false); // Don't create a new session if one doesn't exist 
String name = (String) session.getAttribute("name");
if (name == null) {
	response.sendRedirect("Login.jsp");
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Book</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet" crossorigin="anonymous">
</head>
<body>

	<jsp:include page="header.jsp" />

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

	<%
	int id = Integer.parseInt(request.getParameter("id"));
	String sql = "SELECT * FROM books WHERE \"bookId\" = ?";
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
		ps.setInt(1, id);
		rs = ps.executeQuery();

		if (rs.next()) {
	%>

	<div class="card" style="width: 30rem; margin: auto; margin-top: 4rem">
		<div class="card-header text-center">Update Book</div>
		<div class="card-body">
			<form class="row g-3" action="updateBook?id=<%=rs.getInt("bookId")%>"
				method="post" enctype="multipart/form-data">
				<div class="col-12">
					<label for="name" class="form-label">Title</label> <input
						type="text" class="form-control" id="name" name="name"
						value="<%=rs.getString("name")%>" required>
				</div>
				<div class="col-12">
					<label for="author" class="form-label">Author</label> <input
						type="text" class="form-control" id="author" name="author"
						value="<%=rs.getString("author")%>" required>
				</div>
				<div class="col-12">
					<label for="genre" class="form-label">Genre</label> <input
						type="text" name="genre" class="form-control" id="genre"
						value="<%=rs.getString("genre")%>" required>
				</div>
				<div class="col-12">
					<label for="formFile" class="form-label">Upload image</label> <input
						class="form-control" type="file" name="img" id="formFile">
				</div>
				<div class="col-12">
					<label for="bookPdf" class="form-label">Upload book in PDF
						format</label> <input class="form-control" type="file" name="bookPdf"
						id="bookPdf">
				</div>
				<div class="col-12">
					<button type="submit" class="btn btn-primary">Update Book</button>
				</div>
			</form>
		</div>
	</div>

	<%
	} else {
	out.println("<p>Book not found!</p>");
	}
	} catch (Exception e) {
	Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Database Error", e);
	} finally {
	try {
	if (rs != null)
		rs.close();
	if (ps != null)
		ps.close();
	if (con != null)
		con.close();
	} catch (SQLException e) {
	Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Closing resources failed", e);
	}
	}
	%>

	<hr />
	<jsp:include page="footer.jsp" />

	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
		crossorigin="anonymous"></script>

</body>
</html>
