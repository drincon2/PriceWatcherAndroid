package edu.utep.cs.cs4330.mypricewatcher;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Item> list = new ArrayList<>();
    private Item playstation4 = new Item("Playstation 4", "250.00", "232.45", "0.05");
    private Item asus7Pro = new Item("Asus7Pro", "500.00", "340.75", "30.00");
    private Item ninjaBullet = new Item("Ninja Bullet", "250.00", "150.00", "45.00");
    private ListView listView;

    public static double getRandom() {
        Random random = new Random();
        return (new BigDecimal(random.nextInt(1000 - 1 + 1) + 1).setScale(2, RoundingMode.CEILING).doubleValue());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = findViewById(R.id.ListView);
        list.add(playstation4);
        list.add(asus7Pro);
        list.add(ninjaBullet);
        renew();
    }

    private void renew() {
        ItemListAdapter listAdapter = new ItemListAdapter(this, list);
        listView.setAdapter(listAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                for (int i = 0; i < list.size(); i++) {
//                    list.get(i).setInitialPrice(String.valueOf(getRandom()));
                }
                renew();
                break;
            case R.id.add:
                    addItem();
                break;
            case R.id.edit:

                break;
            case R.id.remove:

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void addItem() {
        DialogFragment addItemFragment = new AddItemDialog();
        addItemFragment.show(getSupportFragmentManager(), "Add Item");
    }


}
