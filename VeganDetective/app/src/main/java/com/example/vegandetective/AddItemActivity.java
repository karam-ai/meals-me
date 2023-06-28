package com.example.vegandetective;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.vegandetective.model.Item;
import com.example.vegandetective.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

public class AddItemActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText item_name;
    EditText description;
    EditText calories;
//    private boolean isVegan = false;
//    private boolean isVegetarian = false;
//    private boolean isOmnivore = false;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // get data
        user = (User) getIntent().getSerializableExtra("user");


    }


    public void submitNewItem(View v) {

        saveUser();


//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int id) {
//                radioButton = (RadioButton) findViewById(id);
//                switch (radioButton.getId()) {
//                    case R.id.vegan_radio_button: {
//                        isVegan = true;
//                        isVegetarian = false;
//                        isOmnivore = false;
//                    }
//                    break;
//                    case R.id.vegetarian_radio_button: {
//                        isVegetarian = true;
//                        isVegan = false;
//                        isOmnivore = false;
//                    }
//                    break;
//                    case R.id.omnivore_radio_button: {
//                        isOmnivore = true;
//                        isVegetarian = false;
//                        isVegan = false;
//                    }
//
//                }
//            }
//        });

//        ArrayList<Item> items = loadDate();
//
//        items.add(new Item(item_name.getText().toString(), description.getText().toString(), Integer.parseInt(calories.getText().toString()), R.drawable.vegan, isVegan, isVegetarian, isOmnivore));
//        //items.add(new Item("banana", "fruit", 20, R.drawable.vegan, true, false, false));
//        saveDate(items);


    }


    public void SwitchToHome(View v) {
        //this.setContentView(R.layout.details);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


//    public void saveDate(ArrayList<Item> items) {
//        // https://www.youtube.com/watch?v=jcliHGR3CHo
//        //ArrayList<Item> items = loadDate();
//        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(items);
//        editor.putString("items", json);
//        editor.apply();
//    }

//    public ArrayList<Item> loadDate() {
//
//        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("items", null);
//        Type type = new TypeToken<ArrayList<Item>>() {
//        }.getType();
//        ArrayList<Item> products1 = gson.fromJson(json, type);
//
//        if (products1 == null) {
//            products1 = new ArrayList<>();
//        }
//        return products1;
//    }

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
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }


}
