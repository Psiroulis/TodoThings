package com.redpepper.todothings.DataModels;

public class Item {

    private String id;

    private String description;

    private int amount;

    private boolean checked;

    public Item() {}

    public Item(String id, String description, int amount) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.checked = false;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isChecked() {
        return checked;
    }
}
