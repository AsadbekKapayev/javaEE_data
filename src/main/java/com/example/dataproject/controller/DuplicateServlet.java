package com.example.dataproject.controller;

import com.example.dataproject.model.book.BookLibrary;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class DuplicateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookIdStr = request.getParameter("bookId");
        int bookId = Integer.parseInt(bookIdStr);
        BookLibrary bookLibrary = new BookLibrary();
        request.setAttribute("duplicates", bookLibrary.getBookDuplicate(bookId));
        request.getRequestDispatcher("book_duplicates.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookLibrary bookLibrary = new BookLibrary();
        Cookie[] cookies = request.getCookies();
        int readerId = -1;
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals("readerId")) {
                readerId = Integer.parseInt(cookie.getValue());
            }
        }
        String duplicateIdStr = request.getParameter("duplicateId");
        int duplicateId = Integer.parseInt(duplicateIdStr);
        if (readerId != -1) {
            bookLibrary.updateDuplicate(readerId, duplicateId);
        }
        response.sendRedirect("books.do");
    }
}
