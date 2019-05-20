package com.e.learnandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText eTextRealValue;
    TextView textDollarValue;
    Button   buttonCalculate;

    private double quotation;
    private static final String KEY_DOLLAR_VALUE = "keydollarvalue";

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCalculate = findViewById(R.id.button_calculate);
        eTextRealValue = findViewById(R.id.etext_real_value);
        textDollarValue = findViewById(R.id.text_dollar_value);

        mQueue = Volley.newRequestQueue(this);
        getDollarQuotation(getString(R.string.quotation_api));

        buttonCalculate.setOnClickListener(this::calculate);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_DOLLAR_VALUE, textDollarValue.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textDollarValue.setText(savedInstanceState.getString(KEY_DOLLAR_VALUE));
    }


    private void calculate(View v){
        if(!eTextRealValue.getText().toString().equals("")){

            DecimalFormat df = new DecimalFormat("#.00");

            double real = Double.parseDouble(eTextRealValue.getText().toString());
            double dollar = real / quotation;

            textDollarValue.setText(df.format(dollar));
        }

    }

    private void getDollarQuotation(String url){

        //wtf java? linguagem mt ruim

        StringRequest request = new StringRequest(Request.Method.GET,url,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        quotation = obj.getDouble("USD_BRL");
                    } catch (JSONException e) {
                        quotation = -1;
                        e.printStackTrace();
                    }
                },
                error ->{
                    quotation = -1;
                }
        );

        mQueue.add(request);

    }






}
