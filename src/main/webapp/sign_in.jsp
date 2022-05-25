<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: asadb
  Date: 01.05.2022
  Time: 21:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h1>Sign in</h1>
<form action="sign-in.do" method="POST">
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
    <%
        String name = request.getParameter("name");
        if(name == null){
            name="";
        }
    %>
    <label for="name"><b>Name:</b></label>
    <input type="text" id="name" name="name" value="<%=name%>">
    <br>
    <%
        String surname = request.getParameter("surname");
        if(surname == null){
            surname="";
        }
    %>
    <label for="surname"><b>Surname:</b></label>
    <input type="text" id="surname" name="surname" value="<%=surname%>">
    <br>
    <%
        String phoneNumber = request.getParameter("phoneNumber");
        if(phoneNumber == null){
            phoneNumber="";
        }
    %>
    <label for="phoneNumber"><b>phoneNumber:</b></label>
    <input type="text" id="phoneNumber" name="phoneNumber" value="<%=phoneNumber%>">
    <br>
    <%
        String address = request.getParameter("address");
        if(address == null){
            address="";
        }
    %>
    <label for="address"><b>Address: </b></label>
    <input type="text" id="address" name="address" value="<%=address%>">
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
