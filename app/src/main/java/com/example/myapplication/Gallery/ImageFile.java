package com.example.myapplication.Gallery;

public class ImageFile {
    private String name;
    private int image;

    public ImageFile(String name, int image){
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }
}
