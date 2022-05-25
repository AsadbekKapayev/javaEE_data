package com.example.dataproject.model;

import java.util.List;

public class ReaderLibrary {
    private Reader reader;
    private ReaderDAO dao;

    public ReaderLibrary() {
        this.dao = new ReaderDAO();
    }

    public boolean isUsernameExist(String username) {
        User user = dao.getUser(username);
        if (user.getUsername() != null) {
            return true;
        }
        return false;
    }

    public int isCorrectPassword(String username, String password) {
        if (isUsernameExist(username)) {
            User user = dao.getUser(username);
            if (user.getUsername().matches(username)
                    && user.getPassword().matches(password)) {
                return user.getReader_id();
            }
        }
        return -1;
    }

    public void addReader(Reader reader, User user) {
        dao.addReader(reader, user);
    }

    public void addUser(User user) {

    }
}
