<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<body>
	<jsp:include page="header.jsp" />
	<div class="card" style="width: 30rem; margin: auto; margin-top: 4rem">

		<div class="card-header text-center">Sign-Up</div>
		<div class="card-body">
			<form class="row g-3" action="signUp" method="post">
				<div class="col-12">
					<label for="name" class="form-label">Name </label> <input
						type="text" class="form-control" id="name" name="name"
						placeholder="Enter Name">
				</div>
				<div class="col-md-6">
					<label for="inputEmail4" class="form-label">Email</label> <input
						type="email" class="form-control" name="email" id="inputEmail4">
				</div>
				<div class="col-md-6">
					<label for="inputPassword4" class="form-label">Password</label> <input
						type="password" name="password" class="form-control"
						id="inputPassword4">
				</div>

				<div class="col-12">
					<label for="phoneNumber" class="form-label">Phone Number</label> <input
						type="number" name="phoneNumber" class="form-control"
						id="phoneNumber">
				</div>

				<div class="col-12">
					<button type="submit" class="btn btn-primary">Sign up</button>
				</div>
			</form>
		</div>
	</div>
	<hr/>
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