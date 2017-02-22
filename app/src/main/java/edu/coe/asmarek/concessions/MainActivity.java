package edu.coe.asmarek.concessions;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button cancel;
    private Button total;
    private ArrayList<itemControl> items;
    private ArrayList<Integer> qtys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpButtons();
        createItemArray();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpButtons() {
        cancel = (Button) this.findViewById(R.id.btnCancel);
        cancel.setOnClickListener(this);

        total = (Button) this.findViewById(R.id.btnTotal);
        total.setOnClickListener(this);

    }
    
    private void createItemArray() {
        items = new ArrayList<itemControl>();
        items.add((itemControl) findViewById(R.id.item1));
        items.add((itemControl) findViewById(R.id.item2));
        items.add((itemControl) findViewById(R.id.item3));
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
                break;
        }
    }
}
