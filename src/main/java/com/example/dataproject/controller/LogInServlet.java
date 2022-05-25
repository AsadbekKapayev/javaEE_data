package com.example.dataproject.controller;

import com.example.dataproject.model.ReaderLibrary;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogInServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        ReaderLibrary readerLibrary = new ReaderLibrary();
        int id;

        String username = request.getParameter("username");
        if (username.trim().length() == 0 ) {
            errors.add("Username must be supplied");
        }
        String password = request.getParameter("password");
        id = readerLibrary.isCorrectPassword(username, password);
        if (password.trim().length() == 0 ) {
            errors.add("Password must be supplied");
        } else if (id == -1) {
            errors.add("Username or password incorrect! Please enter again!");
        }

        if (errors.isEmpty()) {
            Cookie cookie = new Cookie("readerId", "" + id);
            cookie.setMaxAge(60 * 60);
            response.addCookie(cookie);
            response.sendRedirect("/books.do");
        } else {
            request.setAttribute("errors", errors);
            RequestDispatcher rd = request.getRequestDispatcher("log_in.jsp");
            rd.forward(request, response);
        }
    }
}
