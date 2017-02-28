package edu.coe.asmarek.concessions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class SetItemsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private Button add;
    private Button clear;
    private Button save;
    private Button listClear;
    private Button priceClear;
    private EditText itemName;
    private EditText itemPrice;
    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> itemArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();

        SharedPreferences s = getSharedPreferences("myFile", 0);
        String itemNames = s.getString("itemNames", "");
        String itemPrices = s.getString("itemPrices", "");

        if(itemNames != "") {
            ArrayList<String> names = new ArrayList<String>(Arrays.asList(itemNames.split(",")));
            ArrayList<String> prices = new ArrayList<String>(Arrays.asList(itemPrices.split(",")));


            for (int j = 0; j < names.size(); j++) {
                itemArray.add(names.get(j) + " - $" + prices.get(j));
            }

            adapter.notifyDataSetChanged();
        }
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
        if (id == R.id.action_getFinalTotal) {
            Intent i = new Intent("edu.coe.asmarek.Concessions.FinalTotalActivity");

            startActivity(i);
        }
        if (id == R.id.action_setItems) {
            Intent i = new Intent("edu.coe.asmarek.Concessions.SetItemsActivity");

            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        add = (Button) findViewById(R.id.btnAdd);
        clear = (Button) findViewById(R.id.btnClear);
        listClear = (Button) findViewById(R.id.btnListClear);
        save = (Button) findViewById(R.id.btnSave);
        priceClear = (Button) findViewById(R.id.btnPClear);

        itemName = (EditText) findViewById(R.id.edtItemName);
        itemPrice = (EditText) findViewById(R.id.edtItemPrice);

        itemArray = new ArrayList<String>();
        list = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemArray);
        list.setAdapter(adapter);

        list.setOnItemClickListener(this);

        add.setOnClickListener(this);
        clear.setOnClickListener(this);
        listClear.setOnClickListener(this);
        save.setOnClickListener(this);
        priceClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                if (itemArray.size() < 8) {
                    String p = itemPrice.getText().toString();
                    String n = itemName.getText().toString();
                    if(!p.matches("") && !n.matches("") && !p.matches("0.00")) {
                        itemArray.add(n + " - $" + String.format("%.2f", Float.parseFloat(p)));
                        adapter.notifyDataSetChanged();
                        itemPrice.setText("0.00");
                        itemName.setText("");
                    }
                }
                break;
            case R.id.btnClear:
                itemName.setText("");
                itemPrice.setText("0.00");
                break;
            case R.id.btnListClear:
                itemArray.clear();
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();
                break;
            case R.id.btnSave:
                String itemsNameString = "";
                String itemsPriceString = "";
                SharedPreferences s = getSharedPreferences("myFile", 0);
                SharedPreferences.Editor e = s.edit();

                String allItemsString = s.getString("allItems", ",");
                String allItemTotalsString = s.getString("allTotals", ",");

                Log.d("allTotals", allItemTotalsString);

                if(!allItemsString.matches(",")) {
                    allItemsString = allItemsString + ",";
                    allItemTotalsString = allItemTotalsString + ",";
                } else {
                    allItemsString = "";
                    allItemTotalsString = "";
                }

                ArrayList<String> allNames = new ArrayList<String>(Arrays.asList(allItemsString.split(",")));

                for (int j = 0; j < itemArray.size(); j++) {
                    String item = itemArray.get(j);
                    int b = item.indexOf(" - ");

                    itemsNameString = itemsNameString + item.substring(0, b);
                    itemsPriceString = itemsPriceString + item.substring(b+4);

                    if(allNames.indexOf(item.substring(0, b)) == (-1)) {
                        allItemsString = allItemsString + item.substring(0, b) + ",";
                        allItemTotalsString = allItemTotalsString + "0,";
                    }

                    if(j < itemArray.size()-1) {
                        itemsNameString = itemsNameString + ",";
                        itemsPriceString = itemsPriceString + ",";
                    }
                }

                e.putString("allItems", allItemsString.substring(0, allItemsString.length()-1));
                e.putString("allTotals", allItemTotalsString.substring(0, allItemTotalsString.length()-1));
                e.putString("itemNames", itemsNameString);
                e.putString("itemPrices", itemsPriceString);


                Log.d("allItems", allItemsString.substring(0, allItemsString.length()-1));
                Log.d("allTotals", allItemTotalsString.substring(0, allItemTotalsString.length()-1));
                Log.d("itemNames", itemsNameString);
                Log.d("itemPrices", itemsPriceString);

                e.commit();

                Intent i = new Intent("edu.coe.asmarek.Concessions.MainActivity");
                startActivity(i);
                break;
            case R.id.btnPClear:
                itemPrice.setText("");
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = itemArray.get(position);
        int b = item.indexOf(" - $");

        String name = item.substring(0, b);
        String price = item.substring(b+4);
        itemName.setText(name.toString());
        itemPrice.setText(price.toString());

        itemArray.remove(position);
        adapter.notifyDataSetChanged();
        adapter.notifyDataSetInvalidated();
    }
}
