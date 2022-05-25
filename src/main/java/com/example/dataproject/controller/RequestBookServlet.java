package com.example.dataproject.controller;

import com.example.dataproject.model.book.BookLibrary;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RequestBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*Cookie[] cookies = request.getCookies();
        int readerId = -1;
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals("readerId")) {
                readerId = Integer.parseInt(cookie.getValue());
            }
        }
        if (readerId != -1) {
            request.setAttribute("readerId", readerId);
        } else {
            request.setAttribute("readerId", -1);
        }
        RequestDispatcher rd = request.getRequestDispatcher("request_book.jsp");
        rd.forward(request, response);*/
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookLibrary bookLibrary = new BookLibrary();
        String type = request.getParameter("bookType");
        int fineId = Integer.parseInt(type);
        String book = request.getParameter("book");
        String[] genres = request.getParameterValues("genres");
        String year = request.getParameter("year");
        String author = request.getParameter("author");
        String duplicate = request.getParameter("duplicateCount");

        int bookId = bookLibrary.createBook(book, year, fineId);

        for (String genre: genres) {
            bookLibrary.setGenreToBook(Integer.parseInt(genre), bookId);
        }
        bookLibrary.setAuthorToBook(bookId, author);
        for (int i = 0; i < Integer.parseInt(duplicate); i++) {
            bookLibrary.createDuplicate(bookId);
        }
        response.sendRedirect("books.do");
    }
}
