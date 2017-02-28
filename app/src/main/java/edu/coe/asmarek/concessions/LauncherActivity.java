package edu.coe.asmarek.concessions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        SharedPreferences s = getSharedPreferences("myFile", 0);
        SharedPreferences.Editor e = s.edit();
        //e.clear();
        //e.commit();

        boolean beenOpened = s.getBoolean("beenOpened", false);
        if(beenOpened) {
            Intent i = new Intent("edu.coe.asmarek.Concessions.MainActivity");
            startActivity(i);
        }
        else {
            e.putBoolean("beenOpened", true);
            Intent i = new Intent("edu.coe.asmarek.Concessions.SetItemsActivity");
            startActivity(i);
        }
    }
}
