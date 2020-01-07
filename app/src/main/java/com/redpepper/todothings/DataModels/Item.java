package com.redpepper.todothings.DataModels;

public class Item {

    private String id;

    private String description;

    private int amount;

    private boolean checked;

    public Item() {}

    public Item(String id, String description, int amount, boolean checked) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.checked = checked;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
