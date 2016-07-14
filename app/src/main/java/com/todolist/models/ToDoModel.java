package com.todolist.models;

/**
 * Created by dhananjay on 19/3/16.
 */
public class ToDoModel {
    private int _id;
    private String text_to_do;
    private String date;
    private String time;
    private String location;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getText_to_do() {
        return text_to_do;
    }

    public void setText_to_do(String text_to_do) {
        this.text_to_do = text_to_do;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
