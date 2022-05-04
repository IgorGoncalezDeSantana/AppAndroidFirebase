package com.example.tccbcc.models;

public class Denuncia {
    private String uid;
    private String message;

    public Denuncia() {
    }

    public Denuncia(String uid, String message) {
        this.uid = uid;
        this.message = message;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
