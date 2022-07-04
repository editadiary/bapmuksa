package com.example.myapplication.Contact;

import android.annotation.SuppressLint;
import android.database.Cursor;

import com.example.myapplication.Common;

import java.util.Date;

public class Contact implements Comparable<Contact> {
    private int id;
    private String name;
    private String phone;
    private boolean tags[];
    private String profileImage;
    private Date lastMeet;

    public Contact(int id, String name, String phone, boolean[] tags, String profileImage, Date lastMeet) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.tags = tags;
        this.profileImage = profileImage;
        this.lastMeet = lastMeet;
    }

    public int getId() { return id;}

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public boolean[] getTags() { return tags; }

    public String getProfileImage() { return profileImage; }

    public Date getLastMeet() { return lastMeet; };

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTags(boolean[] tags) { this.tags = tags; }

    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

    public void setLastMeet(Date lastMeet) { this.lastMeet = lastMeet; }

    @Override
    public int compareTo(Contact contact) {
        return name.compareTo(contact.getName());
    }
}
