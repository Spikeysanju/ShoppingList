package com.spikeysanju98gmail.shoppinglist;

public class Item {
    private String item;
    private String image;
    private String quantity;
    private String menuID;
    public Item(){

    }


    public Item(String item, String  menuID,String image,String quantity) {
        this.item = item;
        this.menuID = menuID;
        this.image = image;
        this.quantity = quantity;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getMenuID() {
        return menuID;
    }

    public void setMenuID(String  menuID) {
        this.menuID = menuID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
