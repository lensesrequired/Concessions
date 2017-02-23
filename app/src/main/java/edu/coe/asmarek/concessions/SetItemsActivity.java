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

public class SetItemsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private Button add;
    private Button clear;
    private Button save;
    private Button listClear;
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                if (itemArray.size() < 8) {
                    itemArray.add(itemName.getText().toString() + " - $" + itemPrice.getText().toString());
                    adapter.notifyDataSetChanged();
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
                SharedPreferences s = getSharedPreferences("myFile", 0);
                SharedPreferences.Editor e = s.edit();

                e.putInt("numItems", itemArray.size());

                for (int j = 0; j < itemArray.size(); j++) {
                    String item = itemArray.get(j);
                    int b = item.indexOf(" - ");

                    String name = item.substring(0, b);
                    Float price = Float.parseFloat(item.substring(b+4));

                    e.putString("itemName"+j, name);
                    e.putFloat("itemPrice"+j, price);
                }

                Intent i = new Intent("edu.coe.asmarek.Concessions.MainActivity");
                startActivity(i);
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = itemArray.get(position);
        int b = item.indexOf(" - ");

        String name = item.substring(0, b);
        String price = item.substring(b+4);
        itemName.setText(name.toString());
        itemPrice.setText(price.toString());

        itemArray.remove(position);
        adapter.notifyDataSetChanged();
        adapter.notifyDataSetInvalidated();
    }
}
