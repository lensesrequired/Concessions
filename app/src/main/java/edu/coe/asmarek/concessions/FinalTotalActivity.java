package edu.coe.asmarek.concessions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class FinalTotalActivity extends AppCompatActivity implements View.OnClickListener {

    private Float total;
    private LinearLayout itemTotalList;

    private Button submit;
    private Button cancel;
    private TextView totalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_total);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();

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

    private void initView() {
        itemTotalList = (LinearLayout) findViewById(R.id.llItemTotals);

        SharedPreferences s = getSharedPreferences("myFile", 0);
        String allItems = s.getString("allItems", "");
        String allTotals = s.getString("allTotals", "");
        total = s.getFloat("finalTotal", 0);

        ArrayList<String> allItemNames = new ArrayList<String>(Arrays.asList(allItems.split(",")));
        ArrayList<String> allItemTotals = new ArrayList<String>(Arrays.asList(allTotals.split(",")));

        Log.d("allItems", allItems);
        Log.d("allTotals", allTotals);

        Log.d("num", ((Integer) allItemNames.size()).toString());

        for(int j = 0; j < allItemNames.size(); j++)
        {
            Log.d("item", allItemNames.get(j));
            TextView itemN = new TextView(this);
            TextView itemQ = new TextView(this);
            TextView itemT = new TextView(this);
            LinearLayout ll = new LinearLayout(this);

            //LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 3);
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2);
            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);

            itemN.setText(allItemNames.get(j));
            itemN.setTextSize(25);
            itemN.setPadding(0,0,0,15);
            itemN.setLayoutParams(lp2);

            itemQ.setText(allItemTotals.get(j).toString());
            itemQ.setTextSize(25);
            itemQ.setPadding(0,0,0,15);
            itemQ.setGravity(Gravity.CENTER);
            itemQ.setLayoutParams(lp1);

            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setWeightSum(3);
            ll.addView(itemN);
            ll.addView(itemQ);
            ll.addView(itemT);

            itemTotalList.addView(ll);
        }

        totalText = (TextView) findViewById(R.id.txtTotalVal);
        totalText.setText(total.toString());
    }

    private void setupButtons() {
        submit = (Button) findViewById(R.id.btnSubmit);
        cancel = (Button) findViewById(R.id.btnCancel);

        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);
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
                SharedPreferences.Editor e = s.edit();

                e.putString("allItems", "");
                e.putString("allPrices", "");
                e.putString("allTotals", "");

                e.commit();

                i = new Intent("edu.coe.asmarek.Concessions.MainActivity");
                startActivity(i);
                break;
        }
    }
}
