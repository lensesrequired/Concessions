package edu.coe.asmarek.concessions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class TotalActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<Integer> itemQtys;
    private LinearLayout itemTotalList;
    private TextView totalText;
    private Button cancel;
    private Button submit;
    private ArrayList<String> names;
    private EditText amtRec;
    private TextView chgdue;
    private Button calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        itemQtys = i.getIntegerArrayListExtra("qtys");

        displayItemTotals();

        setupButtons();
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

    private void displayItemTotals() {
        itemTotalList = (LinearLayout) findViewById(R.id.llItemTotals);
        Float total = Float.parseFloat("0.0");

        SharedPreferences s = getSharedPreferences("myFile", 0);
        String itemNames = s.getString("itemNames", "");
        String itemPrices = s.getString("itemPrices", "");

        names = new ArrayList<String>(Arrays.asList(itemNames.split(",")));
        ArrayList<String> prices = new ArrayList<String>(Arrays.asList(itemPrices.split(",")));

        for(int j = 0; j < itemQtys.size(); j++)
        {
            TextView itemN = new TextView(this);
            TextView itemQ = new TextView(this);
            TextView itemT = new TextView(this);
            LinearLayout ll = new LinearLayout(this);

            //LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 3);
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2);
            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);

            itemN.setText(names.get(j));
            itemN.setTextSize(25);
            itemN.setPadding(0,0,0,15);
            itemN.setLayoutParams(lp2);

            itemQ.setText(((Integer)itemQtys.get(j)).toString());
            itemQ.setTextSize(25);
            itemQ.setPadding(0,0,0,15);
            itemQ.setGravity(Gravity.CENTER);
            itemQ.setLayoutParams(lp1);

            itemT.setText(String.format("%.2f", (Float)((Float.parseFloat(prices.get(j))*itemQtys.get(j)))));
            itemT.setTextSize(25);
            itemT.setPadding(0,0,0,15);
            itemT.setGravity(Gravity.CENTER);
            itemT.setLayoutParams(lp2);
            total = total + ((Float.parseFloat(prices.get(j))*itemQtys.get(j)));

            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setWeightSum(5);
            ll.addView(itemN);
            ll.addView(itemQ);
            ll.addView(itemT);

            itemTotalList.addView(ll);
        }

        totalText = (TextView) findViewById(R.id.txtTotalVal);
        totalText.setText(String.format("%.2f", total));
    }

    private void setupButtons() {
        cancel = (Button) findViewById(R.id.btnCancel);
        submit = (Button) findViewById(R.id.btnSubmit);
        calc = (Button) findViewById(R.id.btnCalc);

        cancel.setOnClickListener(this);
        submit.setOnClickListener(this);
        calc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                Intent i = new Intent("edu.coe.asmarek.Concessions.MainActivity");
                startActivity(i);
                break;
            case R.id.btnSubmit:
                SharedPreferences s = getSharedPreferences("myFile", 0);

                String allItems = s.getString("allItems", "");
                String allTotals = s.getString("allTotals", "");
                Float finalTotal = s.getFloat("finalTotal", 0);
                String newAllTotals = "";

                Log.d("allTotals", allTotals);

                ArrayList<String> allNames = new ArrayList<String>(Arrays.asList(allItems.split(",")));
                ArrayList<String> allItemTotals = new ArrayList<String>(Arrays.asList(allTotals.split(",")));

                for(int j = 0; j < itemQtys.size(); j++) {
                    if (itemQtys.get(j) > 0) {
                        int m = allNames.indexOf(names.get(j));
                        int newVal = (Integer.parseInt(allItemTotals.get(m))) + itemQtys.get(j);
                        allItemTotals.set(j, ((Integer) newVal).toString());
                    }

                    newAllTotals = newAllTotals + allItemTotals.get(j);

                    if(j != itemQtys.size()-1) {
                        newAllTotals = newAllTotals + ",";
                    }
                }

                if(itemQtys.size() < allItemTotals.size()) {
                    for(int j = itemQtys.size(); j < allItemTotals.size(); j++) {
                        newAllTotals = newAllTotals + "," + allItemTotals.get(j);
                    }
                }

                Log.d("allTotals", newAllTotals);

                SharedPreferences.Editor e = s.edit();
                if(newAllTotals.endsWith(",")) {
                    e.putString("allTotals", newAllTotals.substring(0, newAllTotals.length() - 1));
                } else {
                    e.putString("allTotals", newAllTotals);
                }
                e.putFloat("finalTotal", finalTotal + Float.parseFloat(totalText.getText().toString()));
                e.commit();

                i = new Intent("edu.coe.asmarek.Concessions.MainActivity");
                startActivity(i);

                break;
            case R.id.btnCalc:
                chgdue = (TextView) findViewById(R.id.txtTenderAmount);
                amtRec = (EditText) findViewById(R.id.edtAmtRec);
                String t = totalText.getText().toString();
                chgdue.setText("Change Due: $" + ((Float)(Float.parseFloat(amtRec.getText().toString())-Float.parseFloat(t))).toString());
                break;
        }
    }
}
