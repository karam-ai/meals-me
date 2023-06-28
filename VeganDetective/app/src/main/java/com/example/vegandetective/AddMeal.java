package com.example.vegandetective;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vegandetective.model.Item;
import com.example.vegandetective.model.ItemAdapter;
import com.example.vegandetective.model.Meal;
import com.example.vegandetective.model.MealAdapter;
import com.example.vegandetective.model.User;
import com.google.gson.Gson;

import java.util.Locale;

public class AddMeal extends AppCompatActivity {
    private User user;
    private EditText mealName;
    private EditText mealDescription;
    private EditText mealDuration;
    private ListView mealsListView;
    private static int RESULT_LOAD_IMAGE = 1;
    private Meal newMeal;
    private TextView resultsTextView;
    private TextView itemsList;
    private int columnIndex;
    private String picturePath;
    private ItemAdapter itemAdapter;
    private MealAdapter mealAdapter;
    private Button submitButton;
    private Button cancelButton;
    private Button selectItemsButton;
    private Button deleteButton;
    private boolean updateStatus = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal_done);


        // get data
        user = (User) getIntent().getSerializableExtra("user");

        assert user != null;
        user.setAllItemsUnSelected();

        // link widgets of the front-end to the back-end.
        mealsListView = (ListView) findViewById(R.id.meals_list_view);
        mealName = (EditText) findViewById(R.id.new_meal_name);
        mealDescription = (EditText) findViewById(R.id.new_meal_description);
        mealDuration = (EditText) findViewById(R.id.new_meal_duration);
        submitButton = (Button) findViewById(R.id.submit_new_meal);
        cancelButton = (Button) findViewById(R.id.cancel_new_meal);
        itemsList = (TextView) findViewById(R.id.items_list_textview);
        selectItemsButton = (Button) findViewById(R.id.select_items_button);
        deleteButton = (Button) findViewById(R.id.remove_button);


        mealAdapter = new MealAdapter(this, R.layout.meal_list_view_done, user.getAllMeals());
        this.mealsListView.setAdapter(mealAdapter);

        mealsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Toast.makeText(AddMeal.this, user.getAllMeals().get(position).getMealName(),
                                Toast.LENGTH_SHORT).show();
                        newMeal = user.getAllMeals().get(position);
                        updatingMeal();

                    }
                }
        );
    }

    private void afterUpdatingMeal() {
        mealName.getText().clear();
        mealDescription.getText().clear();
        mealDuration.getText().clear();
        submitButton.setText(R.string.submit_button);
        cancelButton.setText(R.string.cancel_button);
        itemsList.setText(R.string.items_selected);
        selectItemsButton.setText("SELECT ITEMS");
        updateStatus = false;
    }

    public void updateFrontend() {
        mealName.setText(newMeal.getMealName());
        mealDescription.setText(newMeal.getDescription());
        mealDuration.setText(String.valueOf(newMeal.getDurationInMinutes()));
        submitButton.setText(getString(R.string.update) + newMeal.getMealName());
        cancelButton.setText(R.string.deselect);
        itemsList.setText(" Items: " + getItemsStr());
        selectItemsButton.setText("UPDATE ITEMS");
        user.setAllItemsUnSelected();
    }


    private void updatingMeal() {
        setSelectedItemsForSelectedMeal();
        updateFrontend();

        updateStatus = true;
    }

    public void setSelectedItemsForSelectedMeal() {

        for (Item item : user.getItems()) {
            if (newMeal.getItems().contains(item)) {
                item.selectedChange();
            }
        }
    }


    public String getItemsStr() {
        String itemsList = "";


        for (Item item : user.getItems()) {
            if (item.isSelected()) {
                itemsList += item.getItemName() + ", ";
            }
        }


        return itemsList;
    }


    public void cancelDeselectButton(View v) {
        if (!updateStatus) {
            Intent intent = new Intent(this, ItemListView.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        } else {
            afterUpdatingMeal();
        }
    }


    public void submitUpdateButton(View v) {
        if (updateStatus) {
            if (editTextValidation()) {
                user.getMealByName(newMeal.getMealName()).setMealName(mealName.getText().toString());
                user.getMealByName(newMeal.getMealName()).setDescription(mealDescription.getText().toString());
                user.getMealByName(newMeal.getMealName()).setDurationInMinutes(Integer.parseInt(mealDuration.getText().toString()));
                // add new items for updated meal
                user.getMealByName(newMeal.getMealName()).getItems().clear();
                for (Item item : user.getItems()) {
                    if (item.isSelected()) {
                        user.getMealByName(newMeal.getMealName()).addItem(item);
                    }
                }

                Toast.makeText(this, "Meal Updated: " + newMeal.getMealName() + ".", Toast.LENGTH_SHORT).show();
                afterUpdatingMeal();
                updateListView();
                saveUser();

            }

        } else {
            if (editTextValidation()) {
                if (user.getMealByName(mealName.getText().toString()) == null) {
                    newMeal = new Meal(mealName.getText().toString(), mealDescription.getText().toString(), R.drawable.vegan, Integer.parseInt(mealDuration.getText().toString()));
                    newMeal.getItems().addAll(user.getSelectedItems());
                    //newMeal.setImage(columnIndex);
                    if (user.addMeal(newMeal)) {

                        updateListView();
                        Toast.makeText(this, "Meal " + newMeal.getMealName() + " added successfully!", Toast.LENGTH_SHORT).show();
                        user.getMealByName(newMeal.getMealName()).getItems().clear();
                        for (Item item : user.getItems()) {
                            if (item.isSelected()) {
                                user.getMealByName(newMeal.getMealName()).addItem(item);
                            }
                        }

                    }
                } else {
                    Toast.makeText(this, "meal name already existed!", Toast.LENGTH_SHORT).show();
                }
                saveUser();
            }
        }

    }


    public boolean editTextValidation() {

        if (mealName.getText().toString().equals("")) {
            Toast.makeText(this, "Please enter Meal name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mealDescription.getText().toString().equals("")) {
            Toast.makeText(this, "Please enter Meal Description", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mealDuration.getText().toString().equals("")) {
            Toast.makeText(this, "Please enter Duration", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            assert selectedImage != null;
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            // String picturePath contains the path of selected Image
        }
    }

    public void selectItem(View v) {
        if (updateStatus) {
            setSelectedItemsForSelectedMeal();
        }

        itemAdapter = new ItemAdapter(this, R.layout.item_list_view_done, user.getItems());

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(AddMeal.this);
        //builderSingle.setIcon(R.drawable.);
        builderSingle.setTitle("Select an Item");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(itemAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //user.getAllItems().get(which).isSelectedChange();

                user.getItems().get(which).isSelectedChange();
                itemsList.setText(" Items: " + getItemsStr());


                AlertDialog.Builder builderInner = new AlertDialog.Builder(AddMeal.this);

                builderInner.setMessage(user.getItems().get(which).getItemName());
                builderInner.setTitle("Your Selected Item is");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }

    public void updateListView() {
        mealAdapter = new MealAdapter(this, R.layout.meal_list_view_done, user.getAllMeals());
        this.mealsListView.setAdapter(mealAdapter);
    }

    public void selectImageFromGalleryButton(View v) {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
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
    public void onBackPressed() {
        Intent intent = new Intent(this, SettingsActivity.class);
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
