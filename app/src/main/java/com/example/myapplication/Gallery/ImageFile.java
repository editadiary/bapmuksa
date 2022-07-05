package com.example.myapplication.Gallery;

import com.example.myapplication.Contact.Contact;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageFile {
    private final int id;
    private String name;
    private int image;
    private int tag; //0: 한식, 1:중식, 2:일식, 3:양식, 4:후식, 5:기타
    private String date;
    private int f1, f2, f3, f4;

    public ImageFile(int id, String name, int image, int tag, String date,
                     int f1, int f2, int f3, int f4){
        this.id = id;
        this.name = name;
        this.image = image;
        this.tag = tag;
        this.date = date;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;

    }

    public int getId() { return id;}

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public int getTag() {return tag;}

    public String getDate() {return date;}

    public int getF1() {return f1;}
    public int getF2() {return f2;}
    public int getF3() {return f3;}
    public int getF4() {return f4;}

    public ArrayList<Integer> getFriends() {
        ArrayList<Integer> friends= new ArrayList<Integer>(Arrays.asList(f1, f2, f3, f4));
        return friends;
    }

    public int getFriendsSize() {
        if(f1==-1) return 0;
        else if(f2==-1) return 1;
        else if(f3==-1) return 2;
        else return 3;
    }

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

    public void addFriend(int pos) {
        if(f1==-1) f1 = pos;
        else if(f2==-1) f2 = pos;
        else if(f3==-1) f3 = pos;
        else f4 = pos;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

}
