package com.beekay.diary;

import java.util.Date;

/**
 * Created by krishna on 11/15/2014.
 */
public class DataClass {

    public String getText() {
        return head;
    }


    public DataClass(String head) {
        this.head = head;
    }

    private String head;
    private String body;
    private Date date;

    public String getBody() {
        return body;
    }

    public DataClass(Date date, String body, String head) {
        this.date = date;
        this.body = body;
        this.head = head;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
