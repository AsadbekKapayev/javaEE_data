package com.example.dataproject.controller;

import com.example.dataproject.model.book.BookLibrary;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ReturnBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookLibrary bookLibrary = new BookLibrary();
        String duplicate = request.getParameter("duplicateId");
        int duplicateId = Integer.parseInt(duplicate);
        request.setAttribute("duplicateId", duplicate);
        bookLibrary.returnBook(duplicateId);

        request.getRequestDispatcher("success.jsp").forward(request, response);
    }
}
