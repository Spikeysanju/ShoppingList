package com.spikeysanju98gmail.shoppinglist;

import android.widget.Toast;

public class TodoItems {
    private String title;
    private String image;
    public TodoItems(){

    }

    public TodoItems(String title,String image) {
        this.title = title;
        this.image = image;
    }





    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
