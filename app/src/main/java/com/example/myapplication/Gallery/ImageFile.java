package com.example.myapplication.Gallery;

import com.example.myapplication.Contact.Contact;
import com.example.myapplication.R;

import java.util.List;

public class ImageFile {
    private String name;
    private int image;
    private int tag; //0: 한식, 1:중식, 2:일식, 3:양식, 4:후식, 5:기타
    private String date;
    private int friend;

    public ImageFile(String name, int image, int tag, String date,
                     int fid){
        this.name = name;
        this.image = image;
        this.tag = tag;
        this.date = date;
        this.friend = fid;

    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public int getTag() {return tag;}

    public String getDate() {return date;}
    public int getFriend() {return friend;}

    public String getTagName() {
        switch (tag) {
            case 0: return "한식";
            case 1: return "중식";
            case 2: return "일식";
            case 3: return "양식";
            case 4: return "후식";
            default: return "기타";
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFriends(int pos) {this.friend = pos;}

    public void setTag(int tag) {
        this.tag = tag;
    }



}
