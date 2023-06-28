package com.example.vegandetective;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vegandetective.model.Item;
import com.example.vegandetective.model.Meal;
import com.example.vegandetective.model.MealAdapter;
import com.example.vegandetective.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    //initialize user data
    User user;
    ListView mealListView;
    TextView recentMealsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setDatabase();
        setLocale(user.getLanguage());
        this.setTitle("Meals Me");


        mealListView = (ListView) findViewById(R.id.meal_recent_listview);
        recentMealsTextView = (TextView) findViewById(R.id.recent_meals_textview);

        try {
            if (user.getRecentMeals().size() != 0) {
                mealListView.setVisibility(View.VISIBLE);
                final MealAdapter mealAdapter = new MealAdapter(this, R.layout.meal_list_view_done, user.getRecentMeals());
                this.mealListView.setAdapter(mealAdapter);

                mealListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        user.setCurrentSelectedMeal(user.getRecentMeals().get(position));
                        switchToMealDetailsActivity();
                    }
                });
            } else {
                recentMealsTextView.setVisibility(View.VISIBLE);
                recentMealsTextView.setText(R.string.no_recent_message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void switchToMealDetailsActivity() {
        Intent intent = new Intent(this, MealDetailsActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }


    // initialize data
    private void setDatabase() {
        // if not the first use
        if (loadDate()) {
            // if first use
        } else {
            initializeDatabase();
        }
    }

    public boolean loadDate() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("user", null);
        Type type = new TypeToken<User>() {
        }.getType();
        user = gson.fromJson(json, type);
        if (user == null) {
            return false;
        } else {
            return true;
        }
    }

    // add default user data.
    private void initializeDatabase() {
        user = new User("Karam");
        Meal dolma = new Meal("Dolma", "we mix tomatoes with paprika with onion and rice",
                R.drawable.dolma, 90);
        Meal salad = new Meal("Salad", "cut everything into pieces and mix them together ",
                R.drawable.salad, 14);

        Meal avocadoOnToast = new Meal("Avocado on Toast", "Cut the avocado in half" +
                " and carefully remove its stone, then scoop out the flesh into a bowl. Squeeze in " +
                "the lemon juice then mash with a fork to your desired texture. Season to taste with" +
                " sea salt, black pepper and chilli flakes. Toast your bread, drizzle over the oil " +
                "then pile the avocado on top you can eat it cold.", R.drawable.avocadoontoast, 15);

        Meal fatimaMeal = new Meal("Pancake", "Put the ingredients in the mixer " +
                "until it becomes texture like cake. Then Heat the Tefal " +
                "frying pan without oil Reduce the temperature and put the mixture in the pan and wait " +
                "When bubbles appear, flip them over.. I love it with peanut butter and fruits, you can eat it cold",
                R.drawable.fatima, 15);

        Meal spicedAubergineBake = new Meal("Spiced aubergine bake", "Heat oven to 220C/200C fan/gas 7. Generously brush each aubergine slice with vegetable oil and place in a single layer " +
                "on a baking tray, or two if they don’t fit on one. Cook on the low shelves for 10 mins, then turn over and cook for a further 5-10 mins until" +
                "they are golden. Reduce the oven to 180C/160C fan/gas 4.\n" +
                "\n" +
                "Heat the coconut oil in a large, heavy-based frying pan and add the onions. Cover and sweat on a low heat for about 5 mins until softened." +
                " Add the garlic, mustard seeds, fenugreek seeds, garam masala, chilli powder, cinnamon stick, cumin and ground coriander. Cook for a few secs until" +
                " it starts to smell beautiful and aromatic.\n" +
                "\n" +
                "Pour the chopped tomatoes and coconut milk into the spiced onions and stir well. Check the seasoning and add a little sugar, salt or pepper to taste.\n" +
                "\n" +
                "Spoon a third of the tomato sauce on the bottom of a 2-litre ovenproof dish. Layer with half the aubergine slices. " +
                "Spoon over a further third of tomato sauce, then the remaining aubergine slices, and finish with the rest of the sauce. Sprinkle over" +
                " the flaked almonds and coriander (if using), reserving some to serve, and bake for 25-30 mins. Serve garnished with more coriander.", R.drawable.spiced_aubergine_bake, 55);

        // add items
        Item tomatoes = new Item("tomatoes", "Vegetables", 15, R.drawable.vegan, true, true, true);
        Item paprika = new Item("paprika", "Vegetables", 15, R.drawable.vegan, true, true, true);
        Item onion = new Item("onion", "Vegetables", 15, R.drawable.vegan, true, true, true);
        Item potatoes = new Item("potatoes", "Vegetables", 15, R.drawable.vegan, true, true, true);
        Item broccoli = new Item("Brocooli", "Vegetables", 15, R.drawable.vegan, true, true, true);
        Item oliveOil = new Item("Olive Oil", "Oils", 15, R.drawable.vegan, true, true, true, false);
        Item avocado = new Item("Avocado", "Vegetables", 15, R.drawable.vegan, true, true, true, false);
        Item lemon = new Item("Lemon", "Vegetables", 15, R.drawable.vegan, true, true, true, false);
        Item chilliFlkes = new Item("Chilli Flakes ", "Vegetables", 10, R.drawable.vegan, true, true, true, false);
        Item oat = new Item("Oat", "Vegetables", 10, R.drawable.vegan, true, true, true, false);
        Item banana = new Item("Banana", "Fruits", 10, R.drawable.vegan, true, true, true, false);
        Item water = new Item("Water", "Milk", 10, R.drawable.vegan, true, true, true, false);
        Item almondMilk = new Item("Almond Milk", "Milk", 10, R.drawable.vegan, true, true, true, false);
        Item soyMilk = new Item("Soy Milk ", "Milk", 10, R.drawable.vegan, true, true, true, false);
        Item vanilla = new Item("Vanilla ", "Spices", 10, R.drawable.vegan, true, true, true, false);
        Item chiaSeed = new Item("Chia", "Seeds", 10, R.drawable.vegan, true, true, true, false);
        Item eggplant = new Item("Eggplant", "Vegetables", 20, R.drawable.vegan, true, true, true, false);
        Item coconutOil = new Item("Coconut Oil", "Oils", 900, R.drawable.vegan, true, true, true, false);
        Item garlic = new Item("Garlic", "Vegetables", 149, R.drawable.vegan, true, true, true, false);
        Item mustardSeeds = new Item("Mustard Seeds", "Seeds", 100, R.drawable.vegan, true, true, true, false);
        Item fenugreek = new Item("Fenugreek", "Seeds", 100, R.drawable.vegan, true, true, true, true);
        Item masala = new Item("Masala", "Spices", 100, R.drawable.vegan, true, true, true, false);
        Item chilliPowder = new Item("Chilli Powder", "Spices", 100, R.drawable.vegan, true, true, true, false);
        Item cinnamon = new Item("Cinnamon", "Spices", 100, R.drawable.vegan, true, true, true, false);
        Item cumin = new Item("Cumin", "Seeds", 100, R.drawable.vegan, true, true, true, false);
        Item coriander = new Item("Coriander", "Vegetables", 60, R.drawable.vegan, true, true, true, false);
        Item coconutMilk = new Item("Coconut Milk", "Milk", 59, R.drawable.vegan, true, true, true, false);
        Item sugar = new Item("Sugar", "Spices", 100, R.drawable.vegan, true, true, true, false);
        Item flakedAlmonds = new Item("flaked almonds", "Seeds", 60, R.drawable.vegan, true, true, true, false);


        // add Items to meal
        spicedAubergineBake.addItem(onion);
        spicedAubergineBake.addItem(tomatoes);
        spicedAubergineBake.addItem(eggplant);
        spicedAubergineBake.addItem(coconutOil);
        spicedAubergineBake.addItem(garlic);
        spicedAubergineBake.addItem(mustardSeeds);
        spicedAubergineBake.addItem(fenugreek);
        spicedAubergineBake.addItem(masala);
        spicedAubergineBake.addItem(chilliPowder);
        spicedAubergineBake.addItem(cinnamon);
        spicedAubergineBake.addItem(cumin);
        spicedAubergineBake.addItem(coriander);
        spicedAubergineBake.addItem(coconutMilk);
        spicedAubergineBake.addItem(sugar);
        spicedAubergineBake.addItem(flakedAlmonds);

        salad.addItem(tomatoes);
        salad.addItem(paprika);
        salad.addItem(onion);

        // add items to Meal Dolma
        dolma.addItem(tomatoes);
        dolma.addItem(paprika);
        dolma.addItem(onion);
        dolma.addItem(potatoes);

        // add items to Meal avocadoOnToast
        avocadoOnToast.addItem(avocado);
        avocadoOnToast.addItem(oliveOil);
        avocadoOnToast.addItem(lemon);
        avocadoOnToast.addItem(chilliFlkes);

        // add to fatima meal
        fatimaMeal.addItem(oat);
        fatimaMeal.addItem(banana);
        fatimaMeal.addItem(water);
        fatimaMeal.addItem(almondMilk);
        fatimaMeal.addItem(vanilla);
        fatimaMeal.addItem(chiaSeed);

        user.addMeal(dolma);
        user.addMeal(salad);
        user.addMeal(avocadoOnToast);
        user.addMeal(fatimaMeal);
        user.addMeal(spicedAubergineBake);


        // to combine all items from all meals into one list of items.
        user.updateItems();
        user.setAllItemsSelected();

    }


    public void startButton(View v) {
        Intent intent = new Intent(this, ItemListView.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    private void setLocale(String la) {
        Locale locale = new Locale(la);
        locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
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
