<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	// In your Logout.jsp
	<%
request.getSession(false); // Get existing session
if (session != null) {
	session.invalidate(); // Invalidate session
}
response.sendRedirect("Login.jsp");
%>

</body>
</html>