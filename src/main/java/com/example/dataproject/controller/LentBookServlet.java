package com.example.dataproject.controller;

import com.example.dataproject.model.book.BookLibrary;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LentBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        BookLibrary bookLibrary = new BookLibrary();
        int readerId = -1;
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals("readerId")) {
                readerId = Integer.parseInt(cookie.getValue());
            }
        }
        request.setAttribute("dublicates", bookLibrary.getDublicate(readerId));

        request.getRequestDispatcher("lent-book.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
