package edu.coe.asmarek.concessions;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TotalActivity extends AppCompatActivity {
    private ArrayList<Integer> itemQtys;
    private LinearLayout itemTotalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        itemQtys = i.getIntegerArrayListExtra("qtys");

        itemTotalList = (LinearLayout) findViewById(R.id.llItemTotals);

        for(int j = 0; j < itemQtys.size(); j++)
        {
            TextView t = new TextView(this);
            t.setText(((Integer)itemQtys.get(j)).toString());
            t.setTextSize(25);
            t.setPadding(0,0,0,15);

            itemTotalList.addView(t);
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



}
