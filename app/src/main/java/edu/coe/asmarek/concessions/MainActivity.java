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
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button minus1;
    private Button plus1;
    private Button minus2;
    private Button plus2;
    private Button minus3;
    private Button plus3;
    private Button cancel;
    private Button total;
    private TextView item1Qty;
    private TextView item2Qty;
    private TextView item3Qty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        if (id == R.id.action_settings) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTotal:
                Intent i = new Intent("edu.coe.asmarek.Concessions.TotalActivity");
                //i.putExtra()
                startActivity(i);
                break;
            case R.id.btnCancel:
                item1Qty.setText("0");
                item2Qty.setText("0");
                item3Qty.setText("0");
                break;
        }
    }
}
