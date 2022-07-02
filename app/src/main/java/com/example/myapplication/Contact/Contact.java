package com.example.myapplication.Contact;

import android.annotation.SuppressLint;
import android.database.Cursor;

import com.example.myapplication.Common;

public class Contact implements Comparable<Contact> {
    private String id;
    private String name;
    private String phone;
    private String tags[];

    public Contact(String id, String name, String phone, String[] tags) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.tags = tags;
    }

    public String getId() { return id;}

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String[] getTags() { return tags; }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTags(String[] tags) { this.tags = tags; }

    @Override
    public int compareTo(Contact contact) {
        return name.compareTo(contact.getName());
    }
}
