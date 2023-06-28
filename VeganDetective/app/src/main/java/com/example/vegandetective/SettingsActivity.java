package com.example.vegandetective;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.vegandetective.model.Item;
import com.example.vegandetective.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    User user;
    Switch customItemSwitch;
    Switch selectionStatusSwitch;
    TextView textView;
    private Spinner sortSpinnerItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_activity_done);
        user = (User) getIntent().getSerializableExtra("user");
        assert user != null;
        user.setUsername("Karam Updated");
        setLocale(user.getLanguage());
        sortSpinnerItems = (Spinner) findViewById(R.id.rating_spinner);


        final ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(this, R.array.rating_stars, R.layout.support_simple_spinner_dropdown_item);
        sortAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sortSpinnerItems.setAdapter(sortAdapter);
        sortSpinnerItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> data, View view, int i, long l) {
                String searchType = data.getItemAtPosition(i).toString();
                int rating = 1;

                if (searchType.equals("واحد") || searchType.equals("One")) {
                    rating = 1;
                }
                if (searchType.equals("اثنين") || searchType.equals("Two")) {
                    rating = 2;
                }
                if (searchType.equals("ثلاثة") || searchType.equals("Three")) {
                    rating = 3;
                }
                if (searchType.equals("اربعة") || searchType.equals("Four")) {
                    rating = 4;
                }
                if (searchType.equals("خمسة") || searchType.equals("Five")) {
                    rating = 5;
                }
                RatingView rv = findViewById(R.id.rv);
                rv.setRating(rating);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        customItemSwitch = (Switch) findViewById(R.id.custom_items_selection_switch);
        selectionStatusSwitch = (Switch) findViewById(R.id.set_Items_selection_status_switch);
        textView = (TextView) findViewById(R.id.set_Items_selection_status_textview);

        selectionStatusSwitch.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        customItemSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    selectionStatusSwitch.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                } else {
                    selectionStatusSwitch.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                }
            }
        });

        selectionStatusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    user.setAllItemsSelected();
                    textView.setText(R.string.set_all_items_selected);
                    saveUser();
                } else {
                    user.setAllItemsUnSelected();
                    textView.setText(R.string.set_all_items_deselected);
                    saveUser();
                }
            }
        });


    }


    public void addItemButton(View v) {
        RatingView rv = findViewById(R.id.rv);
        rv.setRating(5);
//        Intent intent = new Intent(this, RatingView.class);
//        //intent.putExtra("user", user);
//        startActivity(intent);
//        finish();
    }


    public void changeLanguage(View v) {
        final String[] listItems = {"English", "عربي"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
        mBuilder.setTitle("Choose Language...");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    user.setLanguage("en");
                    setLocale(user.getLanguage());
                    recreate();
                    saveUser();
                }
                if (i == 1) {
                    user.setLanguage("ar");
                    setLocale(user.getLanguage());
                    recreate();
                    saveUser();
                }
                dialogInterface.dismiss();
            }
        });
        AlertDialog mDialog = mBuilder.create();
        // show alert dialog
        mDialog.show();

    }

    private void setLocale(String la) {
        Locale locale = new Locale(la);
        locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
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


    public void addMealButton(View v) {
        Intent intent = new Intent(this, AddMeal.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }


    public void clearButton(View v) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        SharedPreferences preferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
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
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();

    }

}