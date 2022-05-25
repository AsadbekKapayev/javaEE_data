<%@ page import="com.example.dataproject.model.book.Fine" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="com.example.dataproject.model.book.BookLibrary" %>
<%@ page import="com.example.dataproject.model.book.Dublicate" %>
<%@ page import="com.example.dataproject.model.book.Book" %><%--
  Created by IntelliJ IDEA.
  User: asadb
  Date: 03.05.2022
  Time: 23:00
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
  String duplicate = (String)request.getAttribute("duplicateId");
  int duplicateId = Integer.parseInt(duplicate);
  Dublicate dublicate = bookLibrary.getDuplicateById(duplicateId);
  Book book = bookLibrary.getBook(dublicate.getBookId());
  Fine fine = bookLibrary.getFine(book.getFineId());
%>
<p>Type: <%=fine.getType()%></p>

<p>Lent date: <%=dublicate.getLentDate()%></p>
<%
  Date data = new Date();
  SimpleDateFormat formatador = new SimpleDateFormat("ddMMyyyy");
  int dataAtual =  Integer.parseInt(formatador.format(data));

  int day = dataAtual / 1000000;
  dataAtual %= 1000000;
  int month = dataAtual / 10000;
  dataAtual %= 10000;

  DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
  DateFormat targetFormat = new SimpleDateFormat("ddMMyyyy");
  Date date = null;
  try {
    date = originalFormat.parse(dublicate.getLentDate());
  } catch (ParseException e) {
    throw new RuntimeException(e);
  }
  int dataAtual2 =  Integer.parseInt(targetFormat.format(date));
  int day1 = dataAtual2 / 1000000;
  dataAtual2 %= 1000000;
  int month1 = dataAtual2 / 10000;
  dataAtual2 %= 10000;

  if (month > month1) {
    day += 30;
  }
  int penalty = 0;
  int penaltyDays = day - day1;
  if (penaltyDays >= fine.getIssuedDays()) {
    for (int i = fine.getIssuedDays(); i < penaltyDays; i++) {
      penaltyDays--;
      penalty += fine.getFineAdditional();
    }
    penalty += fine.getFine();
  }


%>
<p>You should pay: <%=penalty%>$</p>
<a href="books.do">Home</a>
</body>
</html>
