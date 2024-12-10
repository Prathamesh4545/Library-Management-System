<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>Insert title here</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">

</head>
<body>
	<jsp:include page="header.jsp" />


	<div class="card-body">
		<div class="card">
			<table class="table">
				<thead>
					<tr>
						<th scope="col">#</th>
						<th scope="col">Title</th>
						<th scope="col">Author</th>
						<th scope="col">Genre</th>
						<th scope="col">Available</th>
						<th scope="col">Select</th>
					</tr>
				</thead>
				<%
				try {
					Class.forName("org.postgresql.Driver");
					Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/practiceDB", "postgres", "root8080");
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("SELECT * FROM books");
					while (rs.next()) {
				%>
				<tbody>
					<tr>
						<th scope="row"><%=rs.getInt(1)%></th>
						<td scope="row"><%=rs.getString(2)%></td>
						<td scope="row"><%=rs.getString(3)%> Hours</td>
						<td scope="row"><%=rs.getString(4)%></td>
						<td scope="row"><%=rs.getBoolean(5) ? "Yes" : "No"%></td>
						<td scope="row"><div class="d-grid gap-2 d-md-block">
								<a href="UpdateBook.jsp?id=<%=rs.getInt(1)%>"
									class="btn btn-info" type="button">Update</a>
								<form action="deleteBook" method="post" style="display: inline;">
									<input type="hidden" name="id" value="<%=rs.getInt("bookId")%>">
									<button type="submit" class="btn btn-danger">Delete</button>
								</form>
							</div></td>
					</tr>
				</tbody>
				<%
				}
				} catch (Exception e) {
				out.print(e);
				}
				%>
			</table>

		</div>
	</div>
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