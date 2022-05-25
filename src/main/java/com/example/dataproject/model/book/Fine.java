package com.example.dataproject.model.book;

public class Fine {
    private int id;
    private String type;
    private int fine;
    private int fineAdditional;
    private int issuedDays;

    public Fine() {}

    public Fine(int id, String type, int fine, int fineAdditional, int issuedDays) {
        this.id = id;
        this.type = type;
        this.fine = fine;
        this.fineAdditional = fineAdditional;
        this.issuedDays = issuedDays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    public int getFineAdditional() {
        return fineAdditional;
    }

    public void setFineAdditional(int fineAdditional) {
        this.fineAdditional = fineAdditional;
    }

    public int getIssuedDays() {
        return issuedDays;
    }

    public void setIssuedDays(int issuedDays) {
        this.issuedDays = issuedDays;
    }
}
