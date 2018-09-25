package com.example.android.socialloginandroid;

public class ComplainPOJO {

    String title;
    String description;
    String date;
    String category;
    String name;

    public ComplainPOJO() {
    }

    public ComplainPOJO(String name,String title, String description, String date, String category) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.date = date;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
