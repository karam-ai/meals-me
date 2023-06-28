package com.example.vegandetective.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Meal implements Serializable {


    private String mealName;
    private ArrayList<Item> items;
    private String description;
    private int image;
    private int durationInMinutes;
    private boolean isSelected;
    private Date dateCreated;


    public Meal(String mealName, String description, int image, int durationInMinutes) {
        this.mealName = mealName;
        this.items = new ArrayList<Item>();
        this.description = description;
        this.image = image;
        this.durationInMinutes = durationInMinutes;
        this.isSelected = false;
        this.dateCreated = Calendar.getInstance().getTime();
    }


    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }


    public Meal() {
        this.isSelected = false;
        this.dateCreated = Calendar.getInstance().getTime();
    }

    public Meal(String mealName, String description, int durationInMinutes) {
        this.mealName = mealName;
        this.items = new ArrayList<Item>();
        this.description = description;
        this.durationInMinutes = durationInMinutes;
        this.isSelected = false;
        this.dateCreated = Calendar.getInstance().getTime();

    }

    public void addNewItem(Item newItem) {
        ArrayList<Item> items_ = new ArrayList<>();
        if (this.items.contains(newItem)) {
            for (Item item : this.items) {
                if (!(item == newItem)) {
                    items_.add(item);
                }
            }
        }
        this.items = items_;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public String getMealName() {
        return this.mealName;
    }

    public String getDescription() {
        return this.description;
    }

    public int getImage() {
        return this.image;
    }


    public int getDurationInMinutes() {
        return this.durationInMinutes;
    }


    public ArrayList<Item> getItems() {
        return this.items;
    }

    public int getCalories() {
        int sum = 0;
        for (Item item : this.items) {
            sum += item.getCalories();
        }

        return sum;
    }

    public void setImage(int image) {
        this.image = image;
    }


    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date datetime) {
        this.dateCreated = datetime;
    }


    /*Comparator for sorting the list by Meal Name*/
    public static Comparator<Meal> MealNameComparator = new Comparator<Meal>() {

        public int compare(Meal m1, Meal m2) {
            String mealName1 = m1.getMealName().toUpperCase();
            String mealName2 = m2.getMealName().toUpperCase();

            //ascending order
            return mealName1.compareTo(mealName2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }
    };

    /*Comparator for sorting the list by roll no*/
    public static Comparator<Meal> MealCaloriesComparator = new Comparator<Meal>() {

        public int compare(Meal m1, Meal m2) {

            int calories1 = m1.getCalories();
            int calories2 = m2.getCalories();

            /*For ascending order*/
            return calories1 - calories2;

            /*For descending order*/
            //calories2 - calories1;
        }
    };
    public static Comparator<Meal> MealDurationComparator = new Comparator<Meal>() {

        public int compare(Meal m1, Meal m2) {

            int duration1 = m1.getDurationInMinutes();
            int duration2 = m2.getDurationInMinutes();

            /*For ascending order*/
            return duration1 - duration2;

            /*For descending order*/
            //duration2 - duration1;
        }
    };


}


