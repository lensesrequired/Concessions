package edu.coe.asmarek.concessions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button cancel;
    private Button total;
    private ArrayList<itemControl> items;
    private ArrayList<Integer> qtys;
    private LinearLayout itemControls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpItemControls();

        setUpButtons();
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

    private void setUpItemControls() {
        SharedPreferences s = getSharedPreferences("myFile", 0);
        //int numItems = s.getInt("numItems", 0);
        items = new ArrayList<itemControl>();
        itemControls = (LinearLayout) findViewById(R.id.llItemControls);

        String itemNames = s.getString("itemNames", "");
        String itemPrices = s.getString("itemPrices", "");

        ArrayList<String> names = new ArrayList<String>(Arrays.asList(itemNames.split(",")));
        ArrayList<String> prices = new ArrayList<String>(Arrays.asList(itemPrices.split(",")));

        for(int j = 0; j < names.size(); j++) {
            itemControl itemC = new itemControl(this);
            itemC.setText(names.get(j), Float.parseFloat(prices.get(j)));

            itemControls.addView(itemC);
            items.add(itemC);
        }

        //Toast.makeText(this, ((Integer) numItems).toString(), Toast.LENGTH_SHORT).show();
    }

    private void setUpButtons() {
        cancel = (Button) this.findViewById(R.id.btnCancel);
        cancel.setOnClickListener(this);

        total = (Button) this.findViewById(R.id.btnTotal);
        total.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTotal:
                Intent i = new Intent("edu.coe.asmarek.Concessions.TotalActivity");

                qtys = new ArrayList<Integer>();
                for (itemControl item:items) {
                    qtys.add(item.getValue());
                }

                i.putExtra("qtys", qtys);
                startActivity(i);
                break;
            case R.id.btnCancel:
                for (itemControl item:items) {
                    item.setValue(0);
                }
                break;
        }
    }
}
