<%@ page import="java.util.List" %>
<%@ page import="com.example.dataproject.model.book.*" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: asadb
  Date: 02.05.2022
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p>All books</p>
<br/>
<a href="lent-book.do">My books</a>
<br/>
<a href="request_book.jsp">Request a book</a>
<br/>
<br/>
<p>Books</p>
<%
    String search = (String)request.getAttribute("search");
    if (search == null) {
        search = "";
    }
    String sort = (String)request.getAttribute("sort");
    String filter = (String)request.getAttribute("filter");
%>
<form action="books.do" method="post">
    <label for="search">Search: </label>
    <input type="text" id="search" name="search" value="<%=search%>"><br/>
    <label for="sort">Sort by: </label>
    <select name="sort" id="sort">
        <option selected disabled>choose</option>
        <option value="name">Name of book</option>
        <option value="year">Year of book</option>
    </select>
    <label for="filter">Filter by: </label>
    <select id="filter" name="filter">
        <option selected disabled>choose</option>
        <%
            BookLibrary bookLibrary = new BookLibrary();
            List<Fine> fines =  bookLibrary.getFines();
            for (Fine fine : fines) {
        %>
        <option value="<%=fine.getId()%>"><%=fine.getType()%></option>
        <%}%>
        <br/>
    </select>
    <input type="submit" value="Update">
</form>

<%
    List<Book> books;
    boolean sorted = false;
    if (sort != null) {
        books = bookLibrary.getBooks(sort);
    } else {
        books = (List<Book>) request.getAttribute("books");
    }
    List<Book> sortedBooks = new ArrayList<>();
    for (Book book : books) {
        if (search != null) {
            if (book.getName().contains(search)) {
                sortedBooks.add(book);
            }
            sorted = true;
        }
    }
    if (sorted) {
        books = sortedBooks;
    }
    for (Book book : books) {
%>
<%
    if (book.getImg() != null) {
%>
<img src="<%=book.getImg()%>" alt="image host"/>
<%}%>
<p>Name: <%=book.getName()%></p>
<p>Year: <%=book.getYear()%></p>
<%
    List<Author> authors = book.getAuthors();
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
    List<Genre> genres = book.getGenres();
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
<a href="duplicate.do?bookId=<%=book.getId()%>">Go to duplicate</a>
<hr/>
<%}%>

<div>
    <%
        Integer pageQuantity = (Integer)request.getAttribute("pageQuantity");
        if (pageQuantity != null) {
            for (int i = 0; i <= pageQuantity; i++) {
                int num = i + 1;
    %>
    <a href="books.do?pageNum=<%=num%>"><%=num%>  </a>
    <%}}%>
</div>
</body>
</html>
