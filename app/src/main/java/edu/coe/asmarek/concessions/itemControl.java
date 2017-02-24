package edu.coe.asmarek.concessions;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Anna on 2/18/17.
 */

public class itemControl extends LinearLayout implements View.OnClickListener{

    private Button plus;
    private Button minus;
    private TextView qty;
    private TextView item;
    private String itemName;
    private float itemPrice;

    public itemControl(Context context) {
        super(context);
        initializeView(context);
    }

    public itemControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.itemControl);
        itemName = ta.getString(R.styleable.itemControl_itemName);
        itemPrice = ta.getFloat(R.styleable.itemControl_itemPrice, 0);
        ta.recycle();
    }

    public itemControl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.itemControl);
        itemName = ta.getString(R.styleable.itemControl_itemName);
        itemPrice = ta.getFloat(R.styleable.itemControl_itemPrice, 0);
        ta.recycle();
    }

    private void initializeView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.itemcontrol, this);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();

        plus = (Button) findViewById(R.id.btnPlus);
        minus = (Button) findViewById(R.id.btnMinus);
        qty = (TextView) findViewById(R.id.itemQty);

        plus.setOnClickListener(this);
        minus.setOnClickListener(this);

        item = (TextView) findViewById(R.id.itemName);
        setText(itemName, itemPrice);

    }

    public int getValue() {
        return Integer.parseInt(qty.getText().toString());
    }

    public void setValue(int v) {
        if (v > 0) {
            qty.setText(((Integer) v).toString());
        }
        else {
            qty.setText("0");
        }
    }

    public void setText(String itemN, Float itemP) {
        item = (TextView) findViewById(R.id.itemName);
        item.setText(itemN + " - $" + Float.toString(itemP));
    }

    @Override
    public void onClick(View v) {
        int val = getValue();
        switch (v.getId()) {
            case (R.id.btnPlus):
                val++;
                break;
            case (R.id.btnMinus):
                val--;
                break;
        }

        setValue(val);
    }
}
