package com.example.myapplication.Gallery;

public class ImageFile {
    private String name;
    private int image;
    private int tag; //0: 한식, 1:중식, 2:일식, 3:양식, 4:후식, 5:기타

    public ImageFile(String name, int image, int tag){
        this.name = name;
        this.image = image;
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public int getTag() {return tag;}

    public void setName(String name) {
        this.name = name;
    }
    public void setImage(int image) {
        this.image = image;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

}
