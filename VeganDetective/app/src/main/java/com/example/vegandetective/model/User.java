package com.example.vegandetective.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;


public class User implements Serializable {
    private String username;
    private ArrayList<Meal> meals;
    private ArrayList<Meal> recommendedMeals;
    private ArrayList<Meal> recentMeals;
    private ArrayList<Item> items;
    private Meal currentSelectedMeal;
    private String language;

    public User(String username) {
        this.username = username;
        this.meals = new ArrayList<>();
        this.items = new ArrayList<>();
        this.recentMeals = new ArrayList<>();
        this.recommendedMeals = new ArrayList<>();
        this.currentSelectedMeal = new Meal();
        this.language = "en";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Meal> getRecentMeals() {
        return recentMeals;
    }

    public void addToRecentMeals(Meal newMeal) {
        if (this.recentMeals.contains(newMeal)) {
            for (int i = 0; i < this.recentMeals.size(); i++) {
                if (this.recentMeals.get(i) == newMeal) {
                    this.recentMeals.get(i).setDateCreated(Calendar.getInstance().getTime());
                }
            }
        } else {
            newMeal.setDateCreated(Calendar.getInstance().getTime());
            this.recentMeals.add(newMeal);
        }


    }

    public void setCurrentSelectedMeal(Meal meal) {
        this.currentSelectedMeal = meal;
    }

    public Meal getCurrentSelectedMeal() {
        return this.currentSelectedMeal;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    public String getUsername() {
        return username;
    }

    public ArrayList<Item> getItems() {
        updateItems();
        return this.items;
    }

    public void updateItems() {
        this.items = new ArrayList<Item>();
        for (Meal meal : this.meals) {
            for (Item item : meal.getItems()) {
                if (!this.items.contains(item)) {
                    this.items.add(item);
                }
            }
        }
    }


    public ArrayList<Meal> getAllMeals() {
        return this.meals;
    }


    public boolean checkIfNameExisted(Meal newMeal) {
        for (Meal meal : this.meals) {
            if (meal.getMealName().equals(newMeal.getMealName())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    public boolean checkIfNameExistedStr(String name) {
        for (Meal meal : this.meals) {
            if (meal.getMealName().equals(name)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    public boolean addMeal(Meal newMeal) {
        if (!checkIfNameExisted(newMeal)) {
            this.meals.add(newMeal);
            return true;
        } else {
            return false;
        }
    }


    public Meal getMealByName(String mealName) {
        for (Meal meal : this.meals) {
            if (meal.getMealName().equals(mealName)) {
                return meal;
            }
        }
        return null;
    }

    public Item getItemByName(String itemName) {
        for (Item item : this.getItems()) {
            if (item.getItemName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void getAllRecommendedMeals() {
        for (Meal meal : this.meals) {
            if (this.getSelectedItems().containsAll(meal.getItems())) {
                if (!this.recommendedMeals.contains(meal)) {
                    this.recommendedMeals.add(meal);
                }
            }
        }
    }

    public ArrayList<Meal> getRecommendedMeals() {
        return this.recommendedMeals;
    }


    public void filterRecommendedMeals(String searchKey) {
        this.recommendedMeals.clear();
        for (Meal meal : this.meals) {
            if (meal.getDescription().contains(searchKey)) {
                this.recommendedMeals.add(meal);
            }
        }
    }


    public ArrayList<Item> getSelectedItems() {
        ArrayList<Item> tempItems = new ArrayList<>();

        for (Item item : this.getItems()) {
            if (item.isSelected()) {
                tempItems.add(item);
            }
        }
        return tempItems;
    }

    public ArrayList<Item> getUnSelectedItems() {
        ArrayList<Item> tempItems = new ArrayList<>();

        for (Item item : this.getItems()) {
            if (item.isSelected()) {
                tempItems.add(item);
            }
        }
        return tempItems;
    }


    public void setAllItemsUnSelected() {
        for (Item item : this.getItems()) {
            item.unSelectedChange();
        }
    }

    public void setAllItemsSelected() {
        for (Item item : this.getItems()) {
            item.selectedChange();
        }
    }

    public void selectItemByName(String name) {
        for (Item item : this.getItems()) {
            if (item.getItemName().equals(name)) {
                item.isSelectedChange();
            }
        }
    }

}
