package com.example.dataproject.controller;

import com.example.dataproject.model.book.Book;
import com.example.dataproject.model.book.BookLibrary;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookLibraryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookLibrary bookLibrary = new BookLibrary();
        int num = 1;

        String pageNum = request.getParameter("pageNum");

        System.out.println(pageNum);

        if (pageNum != null) {
            num = Integer.parseInt(pageNum);
        }

        List<Book> bookList = bookLibrary.getBooks("");
        List<Book> result = new ArrayList<>();

        for (int i = (num * 5) - 5; num * 5 > i; i++) {
            if (bookList.size() > i) {
                result.add(bookList.get(i));
            }
        }


        int pageQuantity = (int)Math.ceil(bookList.size() / 5);
        request.setAttribute("pageQuantity", pageQuantity);
        request.setAttribute("books", result);
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
