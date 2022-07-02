package com.example.myapplication.Gallery;

import com.example.myapplication.R;

public class ImageFile {
    private String name;
    private int image;
    private int tag; //0: 한식, 1:중식, 2:일식, 3:양식, 4:후식, 5:기타
    private int yymmdd;

    public ImageFile(String name, int image, int tag, int yymmdd){
        this.name = name;
        this.image = image;
        this.tag = tag;
        this.yymmdd = yymmdd;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public int getTag() {return tag;}

    public int getDate() {return yymmdd;}

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

    public void setTag(int tag) {
        this.tag = tag;
    }



}
