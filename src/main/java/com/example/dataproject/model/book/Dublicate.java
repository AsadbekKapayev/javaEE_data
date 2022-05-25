package com.example.dataproject.model.book;

public class Dublicate {
    private int id;
    private int bookId;
    private int readerId;
    private String lentDate;

    public Dublicate(int id, int bookId, int readerId, String lentDate) {
        this.id = id;
        this.bookId = bookId;
        this.readerId = readerId;
        this.lentDate = lentDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public String getLentDate() {
        return lentDate;
    }

    public void setLentDate(String lentDate) {
        this.lentDate = lentDate;
    }
}
