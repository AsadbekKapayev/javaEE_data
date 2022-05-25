<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %><%--
  Created by IntelliJ IDEA.
  User: asadb
  Date: 01.05.2022
  Time: 22:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Log in</h1>
<form action="log-in.do" method="POST">
    <%
        String username = request.getParameter("username");
        if(username == null){
            username="";
        }
    %>
    <label for="username"><b>Username:</b></label>
    <input type="text" id="username" name="username" value="<%=username%>">
    <br>
    <%
        String password = request.getParameter("password");
        if(password == null){
            password="";
        }
    %>
    <label for="password"><b>Password:</b></label>
    <input type="text" id="password" name="password" value="<%=password%>">
    <br>
    <br>
    <input type="submit" value="Sign in">
</form>
<%
    List errors = (ArrayList) request.getAttribute("errors");
    if(errors!=null) {
%>
<p style="color: red">Please correct the following errors:</p>
<ul>
    <%
        Iterator i = errors.iterator();
        while (i.hasNext()){
            String error = (String) i.next();
    %>
    <li><%=error%></li>
    <%}%>
</ul>
<%}%>
</body>
</html>
