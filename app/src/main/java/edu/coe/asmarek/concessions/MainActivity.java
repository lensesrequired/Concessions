package edu.coe.asmarek.concessions;

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
    private TextView item1Qty;
    private TextView item2Qty;
    private TextView item3Qty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        idControls();
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
        minus1 = (Button) this.findViewById(R.id.item1Minus);
        minus1.setOnClickListener(this);

        plus1 = (Button) this.findViewById(R.id.item1Plus);
        plus1.setOnClickListener(this);

        minus2 = (Button) this.findViewById(R.id.item2Minus);
        minus2.setOnClickListener(this);

        plus2 = (Button) this.findViewById(R.id.item2Plus);
        plus2.setOnClickListener(this);

        minus3 = (Button) this.findViewById(R.id.item3Minus);
        minus3.setOnClickListener(this);

        plus3 = (Button) this.findViewById(R.id.item3Plus);
        plus3.setOnClickListener(this);

        cancel = (Button) this.findViewById(R.id.btnCancel);
        cancel.setOnClickListener(this);

    }

    private void idControls() {
        item1Qty = (TextView) this.findViewById(R.id.item1Qty);
        item2Qty = (TextView) this.findViewById(R.id.item2Qty);
        item3Qty = (TextView) this.findViewById(R.id.item3Qty);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item1Minus:
                if((Integer.parseInt(item1Qty.getText().toString()) > 0))
                    item1Qty.setText(Integer.toString(Integer.parseInt(item1Qty.getText().toString())-1));
                break;
            case R.id.item2Minus:
                if((Integer.parseInt(item2Qty.getText().toString()) > 0))
                    item2Qty.setText(Integer.toString(Integer.parseInt(item2Qty.getText().toString())-1));
                break;
            case R.id.item3Minus:
                if((Integer.parseInt(item3Qty.getText().toString()) > 0))
                    item3Qty.setText(Integer.toString(Integer.parseInt(item3Qty.getText().toString())-1));
                break;
            case R.id.item1Plus:
                item1Qty.setText(Integer.toString(Integer.parseInt(item1Qty.getText().toString())+1));
                break;
            case R.id.item2Plus:
                item2Qty.setText(Integer.toString(Integer.parseInt(item2Qty.getText().toString())+1));
                break;
            case R.id.item3Plus:
                item3Qty.setText(Integer.toString(Integer.parseInt(item3Qty.getText().toString())+1));
                break;
            case R.id.btnCancel:
                item1Qty.setText("0");
                item2Qty.setText("0");
                item3Qty.setText("0");
                break;
        }
    }
}
