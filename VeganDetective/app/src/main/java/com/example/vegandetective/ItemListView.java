package com.example.vegandetective;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vegandetective.model.Item;
import com.example.vegandetective.model.ItemAdapter;
import com.example.vegandetective.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

public class ItemListView extends AppCompatActivity {
    private ListView listView;
    private User user;
    private EditText searchField;
    private ItemAdapter itemAdapter;
    private ArrayList<Item> searchedItems;
    private Spinner sortSpinnerItems;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_activity_done);
        searchedItems = new ArrayList<>();

        user = (User) getIntent().getSerializableExtra("user");
        assert user != null;

        searchedItems.addAll(user.getItems());

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        progressBar.setMax(user.getItems().size());
        progressBar.setProgress(user.getSelectedItems().size());


        sortSpinnerItems = (Spinner) findViewById(R.id.sort_spinner_items);

        final ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(this, R.array.items_types, R.layout.support_simple_spinner_dropdown_item);
        sortAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sortSpinnerItems.setAdapter(sortAdapter);
        sortSpinnerItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> data, View view, int i, long l) {
                String searchType = data.getItemAtPosition(i).toString();



                if (searchType.equals("خضراوات")) {
                    searchType = "Vegetables";
                }
                if (searchType.equals("فاكهة")) {
                    searchType = "Fruits";
                }
                if (searchType.equals("حبوب")) {
                    searchType = "Seeds";
                }
                if (searchType.equals("زيوت")) {
                    searchType = "Oils";
                }
                if (searchType.equals("الجميع")) {
                    searchType = "All";
                }
                if (searchType.equals("بهارات")) {
                    searchType = "Spices";
                }
                if (searchType.equals("مستحلبات")) {
                    searchType = "Milk";
                }


                if (!searchType.equals("All")) {
                    searchedItems = new ArrayList<Item>();
                    for (Item item : user.getItems()) {
                        if (item.getDescription().equals(searchType)) {
                            searchedItems.add(item);
                        }
                    }
                } else {
                    searchedItems = new ArrayList<Item>();
                    searchedItems = user.getItems();
                }
                updateListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //System.out.println(adapterView.getItemAtPosition(i));
//                searchedItems.clear();
//                for(Item item:user.getItems()){
//                    if(item.getDescription().equals(adapterView.getItemAtPosition(i))){
//                        searchedItems.add(item);
//
//                    }
//                }
//                updateListView(searchedItems);

        listView = (ListView) findViewById(R.id.product_list_view);
        searchField = (EditText) findViewById(R.id.items_search_edit_text);


        itemAdapter = new ItemAdapter(this, R.layout.item_list_view_done, searchedItems);
        this.listView.setAdapter(itemAdapter);


        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchedItems.clear();
                for (Item item : user.getItems()) {
                    if (item.getItemName().contains(charSequence)) {
                        searchedItems.add(item);
                    }
                }
                updateListView();
                ((ItemAdapter) listView.getAdapter()).notifyDataSetChanged();
            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                                //user.getItems().get(position).isSelectedChange();
                                                //user.selectItemByName(searchedItems.get(position).getItemName());
                                                searchedItems.get(position).isSelectedChange();
                                                progressBar.setMax(user.getItems().size());
                                                progressBar.setProgress(user.getSelectedItems().size());


                                                ((ItemAdapter) listView.getAdapter()).notifyDataSetChanged();


                                            }
                                        }
        );


    }

    public void updateListView() {
        ItemAdapter itemAdapter = new ItemAdapter(this, R.layout.item_list_view_done, searchedItems);
        this.listView.setAdapter(itemAdapter);
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

    public void recommendMeMealsButton(View v) {
        Intent intent = new Intent(this, RecomendedMeals.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }


//    @Override
//    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//        return false;
//    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent,View view, int position,long l){
//
//    }
//    @Override
//    public void onNothingSelected(AdapterView<?> parent){
//
//    }

}
