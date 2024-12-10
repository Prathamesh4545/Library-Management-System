<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
<title>Add Book</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
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

	<div class="card" style="width: 30rem; margin: auto; margin-top: 4rem">
		<div class="card-header text-center">Add Book</div>
		<div class="card-body">
			<form class="row g-3" action="addBook" method="post"
				enctype="multipart/form-data">
				<div class="col-12">
					<label for="name" class="form-label">Title</label> <input
						type="text" class="form-control" id="name" name="name" required>
				</div>
				<div class="col-12">
					<label for="author" class="form-label">Author</label> <input
						type="text" class="form-control" id="author" name="author"
						required>
				</div>
				<div class="col-12">
					<label for="genre" class="form-label">Genre</label> <input
						type="text" name="genre" class="form-control" id="genre" required>
				</div>
				<div class="col-12">
					<label for="formFile" class="form-label">Upload image</label> <input
						class="form-control" type="file" name="img" accept="image/*"
						id="formFile" required>
				</div>
				<div class="col-12">
					<label for="bookPdf" class="form-label">Upload book in PDF
						format</label> <input class="form-control" type="file" name="bookPdf"
						accept="application/pdf" id="bookPdf" required>
				</div>
				<div class="col-12">
					<button type="submit" class="btn btn-primary">Add Book</button>
				</div>
			</form>
		</div>
	</div>
	<hr />
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
