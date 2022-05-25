package com.example.dataproject.controller;

import com.example.dataproject.model.book.BookLibrary;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class BookLibraryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookLibrary bookLibrary = new BookLibrary();
        request.setAttribute("books", bookLibrary.getBooks(""));
        request.getRequestDispatcher("books.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookLibrary bookLibrary = new BookLibrary();
        String search = request.getParameter("search");
        String sort = request.getParameter("sort");
        String filter = request.getParameter("filter");
        System.out.println(search + " " + sort + " " + filter);
        request.setAttribute("search", search);
        request.setAttribute("sort", sort);
        request.setAttribute("filter", filter);
        request.setAttribute("books", bookLibrary.getBooks(""));
        request.getRequestDispatcher("books.jsp").forward(request, response);
    }
}
