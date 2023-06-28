package com.example.vegandetective;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.vegandetective.model.Item;
import com.example.vegandetective.model.Meal;
import com.example.vegandetective.model.MealAdapter;
import com.example.vegandetective.model.User;

import java.util.ArrayList;
import java.util.Collections;

public class RecomendedMeals extends AppCompatActivity {
    ArrayList<Item> items = new ArrayList<Item>();
    ListView listView;
    ArrayList<Meal> mealsTemp = new ArrayList<>();
    User user;
    private Spinner sortSpinnerMeal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomended_meals_done);

        listView = (ListView) findViewById(R.id.meal_recommended_view);


        user = (User) getIntent().getSerializableExtra("user");


        sortSpinnerMeal = (Spinner) findViewById(R.id.sort_spinner_meals);


        final ArrayAdapter<CharSequence> sortMealsAdapter = ArrayAdapter.createFromResource(this, R.array.meals_types, R.layout.support_simple_spinner_dropdown_item);
        sortMealsAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sortSpinnerMeal.setAdapter(sortMealsAdapter);
        sortSpinnerMeal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> data, View view, int i, long l) {
                String searchKey = data.getItemAtPosition(i).toString();


                switch (searchKey) {
                    case "المدة":
                        searchKey = "Duration";
                        break;
                    case "الاسم":
                        searchKey = "Name";
                        break;
                    case "السعرات الحرارية":
                        searchKey = "Calories";
                        break;
                }


                switch (searchKey) {
                    case "Calories":
                        Collections.sort(user.getRecommendedMeals(), Meal.MealCaloriesComparator);
                        break;
                    case "Name":
                        Collections.sort(user.getRecommendedMeals(), Meal.MealNameComparator);
                        break;
                    case "Duration":
                        Collections.sort(user.getRecommendedMeals(), Meal.MealDurationComparator);
                        break;
                }


                if (searchKey.equals("Calories")) {
                    user.getAllRecommendedMeals();
                    Collections.sort(user.getRecommendedMeals(), Meal.MealCaloriesComparator);
                }
                if (searchKey.equals("Name")) {
                    user.getAllRecommendedMeals();
                    Collections.sort(user.getRecommendedMeals(), Meal.MealNameComparator);
                }
                if (searchKey.equals("Duration")) {
                    user.getAllRecommendedMeals();
                    Collections.sort(user.getRecommendedMeals(), Meal.MealDurationComparator);
                }
                if (searchKey.equals("cold")) {
                    user.filterRecommendedMeals(searchKey);
                }
                if (searchKey.equals("warm")) {
                    user.filterRecommendedMeals(searchKey);
                }


                updateAdapter();

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        assert user != null;
        final MealAdapter mealAdapter = new MealAdapter(this, R.layout.meal_list_view_done, user.getRecommendedMeals());
        this.listView.setAdapter(mealAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                user.setCurrentSelectedMeal(user.getRecommendedMeals().get(position));
                switchToMealDetailsActivity();
            }
        });
    }

    public void updateAdapter() {
        MealAdapter mealAdapter = new MealAdapter(this, R.layout.meal_list_view_done, user.getRecommendedMeals());
        this.listView.setAdapter(mealAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ItemListView.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    public void switchToMealDetailsActivity() {
        Intent intent = new Intent(this, MealDetailsActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
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


}
