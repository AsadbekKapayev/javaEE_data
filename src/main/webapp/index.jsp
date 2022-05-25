<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<%
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (Objects.equals(cookie.getName(), "readerId")) {
                request.setAttribute("readerId", cookie.getValue());
                response.sendRedirect("/books.do");
            }
        }
    }
%>
<a href="sign_in.jsp">Sign in</a><br/>
<a href="log_in.jsp">Log in</a>
</body>
</html>