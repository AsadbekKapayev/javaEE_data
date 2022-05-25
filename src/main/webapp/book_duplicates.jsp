<%@ page import="java.util.List" %>
<%@ page import="com.example.dataproject.model.book.*" %><%--
  Created by IntelliJ IDEA.
  User: asadb
  Date: 03.05.2022
  Time: 1:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    BookLibrary bookLibrary = new BookLibrary();
    BookDAO bookDAO = new BookDAO();
    List<Dublicate> dublicates = (List<Dublicate>) request.getAttribute("duplicates");
    for (Dublicate dublicate : dublicates) {%>
<p>DublicateId: <%=dublicate.getId()%></p>
<%
    Book book = bookLibrary.getBook(dublicate.getBookId());
%>
<%
    if (book.getImg() != null) {
%>
<img src="<%=book.getImg()%>" alt="image host"/>
<%}%>
<p>Book name: <%=book.getName()%></p>
<p>Year: <%=book.getYear()%></p>


<%
    List<Author> authors = bookDAO.getAuthors(book.getId());
    String authorStr = "";
    if (authors != null) {
        if (authors.size() > 1) {
            authorStr = "Authors: ";
        } else {
            authorStr = "Author: ";
        }
%>
<p><%=authorStr%>
    <%
        for (Author author : authors) {
    %>
    <%=author.getName()%>,
    <%}%></p>
<%}%>
<%
    List<Genre> genres = bookDAO.getGenres(book.getId());
    String genreStr = "";
    if (genres != null) {
        if (genres.size() > 1) {
            genreStr = "Genres: ";
        } else {
            genreStr = "Genre: ";
        }
%>
<p><%=genreStr%>
    <%
        for (Genre genre : genres) {
    %>
    <%=genre.getGenre()%>,
    <%}%></p>
<%}%>

<%
    if (dublicate.getReaderId() != 0) {
%>
<p>This book lent by another reader</p>
<%} else {%>
    <form action="duplicate.do" method="post">
        <input type="hidden" name="duplicateId" value="<%=dublicate.getId()%>">
        <input type="submit" value="Lend book">
    </form>
<%}%>
<hr/>

<%}%>
</body>
</html>
