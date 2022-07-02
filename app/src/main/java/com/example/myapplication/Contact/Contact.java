package com.example.myapplication.Contact;

import android.annotation.SuppressLint;
import android.database.Cursor;

import com.example.myapplication.Common;

public class Contact implements Comparable<Contact> {
    private int id;
    private String name;
    private String phone;

    public Contact(String name, String phone) {
        this.id = Common.id_num++;
        this.name = name;
        this.phone = phone;
    }

    public int getId() { return id;}

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int compareTo(Contact contact) {
        return name.compareTo(contact.getName());
    }
}
