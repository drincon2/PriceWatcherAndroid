package edu.utep.cs.cs4330.mypricewatcher;

import edu.utep.cs.cs4330.mypricewatcher.Item;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ItemListAdapter.Listener, EditItemDialog.editItemListener, AddItemDialog.addItemListener {


    private ArrayList<Item> list = new ArrayList<>();
    private Item playstation4 = new Item("Playstation 4", "250.00", "232.45", "0.05", "https://www.target.com/p/playstation-4-pro-1tb-console/-/A-51610033");
    private Item motorolaG6 = new Item("Motorola G6", "500.00", "340.75", "30.00", "https://www.target.com/p/motorola-g6-32gb-smartphone-universal-unlocked-black/-/A-53513846");
    private Item nutriNinjaPro = new Item("Nutri Ninja Pro", "250.00", "150.00", "45.00", "https://www.target.com/p/nutri-ninja-pro-bl456/-/A-15124275");
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = findViewById(R.id.ListView);
        list.add(playstation4);
        list.add(motorolaG6);
        list.add(nutriNinjaPro);


        renew();

        handleSharedLink(getIntent());
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

    /**
     * Toolbar Options
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Add cases itemDetailedDescription for itemDetail Activity, and browse pop up menu
            case R.id.refresh:
                Log.e("Menu Action: ", "Refresh");
                for(int i = 0; i < list.size(); i++) {
                    refreshItem(i);
                }
                break;
            case R.id.add:
                Log.e("Menu Action: ", "Add");
                addItemDialog("");
                break;
            case R.id.refresh_pop:
                Log.e("Pop Menu action: ", "Refresh_Pop");
                refreshItem(item.getItemId());
            case R.id.edit_pop:
                Log.e("Pop Menu action: ", "Edit_Pop");
                editItem(item.getItemId());
            case R.id.remove_pop:
                Log.e("Pop Menu action: ", "Remove_Pop");
                removeItem(item.getItemId());
            case R.id.browse_pop:
                Log.e("Pop Menu action: ", "Browse_Pop");
                browseItem(item.getItemId());
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }


    private void addItemDialog(String sharedText) {
        AddItemDialog dialog = new AddItemDialog();
        if (!sharedText.equals("")) {
            Bundle bundle = new Bundle();
            bundle.putString("url", sharedText);
            dialog.setArguments(bundle);
        }
        dialog.show(getSupportFragmentManager(), "");

    }

    private void editItemDialog(int index) {
        EditItemDialog dialog = new EditItemDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        bundle.putString("name", list.get(index).getName());
        bundle.putString("url", list.get(index).getURL());
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "Edit item");
    }

    /**
     * These methods are implemented due to ItemListAdapter having a listener.
     * In these methods will hold code that will modify the item accordingly.
     *
     * @param index
     */
    ////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void removeItem(int index) {
        list.remove(index);
        renew();
    }

    /**
     * This method is called when the edit menu is clicked in the popup-menu
     * @param index
     */
    @Override
    public void editItem(int index) {
        editItemDialog(index);
    }

    @Override
    public void refreshItem(int index) {
        list.get(index).setCurrentPrice("566.22");
        list.get(index).setChange("50");
        list.get(index).changePrice(list.get(index).getInitialPrice(), list.get(index).getCurrentPrice());
        renew();
    }

    @Override
    public void addItem(String name, String url) {
        new findItemPrice().doInBackground(url);
        Item item = new Item(name, "300", "400", "40", url);
        list.add(item);
        renew();
    }

    @Override
    public void updateItem(String name, String url, int index) {
        Item item = new Item(name, "300", "400", "40", url);
        list.set(index, item);
        renew();
    }

    @Override
    public void browseItem(int index) {
        String url = list.get(index).getURL();
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
            list.get(index).setURL(url);
        }

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
        //Add intent to display item web page
    }

    //share link
    private void handleSharedLink(Intent intent) {
        String action = intent.getAction();
        String type = intent.getType();

        if(Intent.ACTION_SEND.equals(action) && type != null) {
            if("text/plain".equals(type)) {
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                if(sharedText != null) {
                    addItemDialog(sharedText);
                }
            }
        }
    }



    //Find initial and current price of item
    private class findItemPrice extends AsyncTask<String, Void, Void> {
        String originalPrice;
        String latestPrice;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //add progress bar
        }

        @Override
        protected Void doInBackground (String... params) {
            //put code here
            Document document;
            try{
                document = Jsoup.connect(params[0]).get();
                Elements productPrice = document.select("div.h-text-bold:contains($)");
                latestPrice = productPrice.text();
                Elements regularPrice = document.select("span.h-margin-v-tight:contains($)");
                originalPrice = regularPrice.text();
                originalPrice = originalPrice.replace("$", "").trim();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected  void onPostExecute (Void result) {

        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////
}
