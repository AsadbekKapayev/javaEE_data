package com.example.dataproject.model;

public class User {
    private String username;
    private String password;
    private int reader_id;

    public User() {}

    public User(String username, String password, int reader_id) {
        this.username = username;
        this.password = password;
        this.reader_id = reader_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getReader_id() {
        return reader_id;
    }

    public void setReader_id(int reader_id) {
        this.reader_id = reader_id;
    }
}
