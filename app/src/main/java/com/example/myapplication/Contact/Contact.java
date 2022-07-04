package com.example.myapplication.Contact;

public class Contact implements Comparable<Contact> {
    private final int id;
    private String name;
    private String phone;
    private String tags;
    private String profileImage;
    private String lastMeet;

    public Contact(int id, String name, String phone, String tags, String profileImage, String lastMeet) {
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

    public String getTags() { return tags; }

    public String getProfileImage() { return profileImage; }

    public String getLastMeet() { return lastMeet; }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTags(String tags) { this.tags = tags; }

    public void setLastMeet(String lastMeet) { this.lastMeet = lastMeet; }

    @Override
    public int compareTo(Contact contact) {
        return name.compareTo(contact.getName());
    }
}
