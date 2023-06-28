package com.example.vegandetective;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vegandetective.ItemListView;
import com.example.vegandetective.R;
import com.example.vegandetective.RecomendedMeals;
import com.example.vegandetective.model.User;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.Locale;

public class MealDetailsActivity extends AppCompatActivity {
    User user;
    ImageView imageMealDetails;
    TextView nameMealDetails;
    TextView durationMealDetails;
    TextView caloriesMealDetails;
    TextView descriptionMealDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details_done);

        user = (User) getIntent().getSerializableExtra("user");

        imageMealDetails = (ImageView) findViewById(R.id.image_meal_details);
        nameMealDetails = (TextView) findViewById(R.id.name_meal_details);
        durationMealDetails = (TextView) findViewById(R.id.duration_meal_details);
        caloriesMealDetails = (TextView) findViewById(R.id.calories_meal_details);
        descriptionMealDetails = (TextView) findViewById(R.id.description_meal_details);

        imageMealDetails.setImageResource(user.getCurrentSelectedMeal().getImage());
        nameMealDetails.setText(user.getCurrentSelectedMeal().getMealName());
        durationMealDetails.setText(String.valueOf(user.getCurrentSelectedMeal().getDurationInMinutes())+" Minutes to prepare");
        caloriesMealDetails.setText(String.valueOf(user.getCurrentSelectedMeal().getCalories())+" Calories per 100g");
        descriptionMealDetails.setText(user.getCurrentSelectedMeal().getDescription());
        user.addToRecentMeals(user.getCurrentSelectedMeal());
        saveUser();


    }

    public void saveUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("user", json);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, RecomendedMeals.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }


}
