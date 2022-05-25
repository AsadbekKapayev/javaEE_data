<%@ page import="com.example.dataproject.model.book.BookLibrary" %>
<%@ page import="com.example.dataproject.model.book.Fine" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.dataproject.model.book.Genre" %><%--
  Created by IntelliJ IDEA.
  User: asadb
  Date: 03.05.2022
  Time: 11:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Title</title>
  </head>
  <body>
  <a href="books.do">All books</a>
  <br/>
  <a href="lent-book.do">My books</a>
  <br/>
  <p>Request a book</p>
  <br/>
  <br/>
  <h1>Request a book</h1>
  <form action="request-book.do" method="POST">
    <label for="book">The book name: </label>
    <input type="text" id="book" name="book"><br/>
    <label for="author">The author: </label>
    <input type="text" id="author" name="author"><br/>
    <label for="year">The year: </label>
    <input type="date" id="year" name="year"><br/>

    <p>Select type: </p>
      <%
        BookLibrary bookLibrary = new BookLibrary();
        List<Fine> fines =  bookLibrary.getFines();
        for (Fine fine : fines) {
      %>
      <div>
      <input type="radio" id="bookType<%=fine.getId()%>" name="bookType" value="<%=fine.getId()%>">
      <label for="bookType<%=fine.getId()%>"><%=fine.getType()%></label>
      </div>
      <%}%>
    <br/>

    <fieldset>
      <legend>Genre</legend>
      <%
        List<Genre> genres = bookLibrary.getAllGenres();
        for (Genre genre: genres) {
      %>
      <div>
        <input type="checkbox" id="genre<%=genre.getId()%>" name="genres" value="<%=genre.getId()%>">
        <label for="genre<%=genre.getId()%>"><%=genre.getGenre()%></label>
      </div>
      <%}%>
    </fieldset>
    <br/>
    <label for="duplicateCount">Enter count of duplicates: </label>
    <input type="number" id="duplicateCount" name="duplicateCount">
    <br/>
    <br/>
    <input type="submit" value="Request a book">
  </form>
  </body>
</html>
