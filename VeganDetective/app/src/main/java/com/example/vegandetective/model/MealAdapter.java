package com.example.vegandetective.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vegandetective.R;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MealAdapter extends ArrayAdapter<Meal> {

    private ArrayList<Meal> dataSetMeal;
    private Context myContext;
    private int rowLayout;

    public MealAdapter(Context context, int rowLayout, ArrayList<Meal> dataSetMeal) {
        super(context, rowLayout, dataSetMeal);
        this.dataSetMeal = dataSetMeal;
        this.myContext = context;
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listMeal = convertView;
        if (listMeal == null) {
            listMeal = LayoutInflater.from(myContext).inflate(rowLayout, parent, false);

        }

        Meal currentMeal = this.dataSetMeal.get(position);

        TextView mealName = listMeal.findViewById(R.id.meal_name);

        mealName.setText(currentMeal.getMealName());

        ImageView mealPicture = listMeal.findViewById(R.id.meal_image);
        mealPicture.setImageResource(currentMeal.getImage());

        int caloriesSum = 0;
        for (Item item : currentMeal.getItems()) {
            caloriesSum += item.getCalories();
        }
        String caloriesSumStr = "";

        caloriesSumStr = String.valueOf(caloriesSum);

        TextView calories = listMeal.findViewById(R.id.meal_calories);
        calories.setText("energy: " + caloriesSumStr);

        TextView mealDuration = listMeal.findViewById(R.id.meal_duration);
        mealDuration.setText("Duration: " + String.valueOf(currentMeal.getDurationInMinutes()) + " m");


        return listMeal;


    }

}
