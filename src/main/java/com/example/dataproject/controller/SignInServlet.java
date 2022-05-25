package com.example.dataproject.controller;

import com.example.dataproject.model.Reader;
import com.example.dataproject.model.ReaderLibrary;
import com.example.dataproject.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class SignInServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<String> errors = new ArrayList<>();
        ReaderLibrary readerLibrary = new ReaderLibrary();

        String username = request.getParameter("username");
        if(username.trim().length() == 0 ) {
            errors.add("Username must be supplied");
        } else if (readerLibrary.isUsernameExist(username)) {
            errors.add("The username is already exist, enter other");
        }
        String password = request.getParameter("password");
        if(password.trim().length() == 0 ) {
            errors.add("Password must be supplied");
        }
        String name = request.getParameter("name");
        if(name.trim().length() == 0 ) {
            errors.add("Name must be supplied");
        }
        String surname = request.getParameter("surname");
        if(surname.trim().length() == 0 ) {
            errors.add("Surname must be supplied");
        }
        String phoneNumber = request.getParameter("phoneNumber");
        if(phoneNumber.trim().length() == 0 ) {
            errors.add("Phone number must be supplied");
        }
        String address = request.getParameter("address");
        if(address.trim().length() == 0 ) {
            errors.add("Address must be supplied");
        }

        if (errors.isEmpty()) {
            readerLibrary.addReader(new Reader(0, name + " " + surname, phoneNumber, address), new User(username, password, 0));
            response.sendRedirect("log_in.jsp");
        } else {
            request.setAttribute("errors", errors);
            RequestDispatcher rd = request.getRequestDispatcher("sign_in.jsp");
            rd.forward(request, response);
        }
    }
}
