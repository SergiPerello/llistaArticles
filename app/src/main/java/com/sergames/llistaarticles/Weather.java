package com.sergames.llistaarticles;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Weather extends AppCompatActivity {
    double kelvin= -273.15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Button btnOk = findViewById(R.id.btnOk);
        final EditText editText = findViewById(R.id.city);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWeather(editText.getText().toString());
            }
        });
    }

    private void getWeather(String city) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0,10000);
        final TextView tv = findViewById(R.id.temp);
        String Url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=ff92b039a2462dd9065581f738c64086";
        client.get(this,Url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject weather = null;
                String str = new String(responseBody);
                try {
                    weather = new JSONObject(str);
                    tv.setText(String.valueOf((int)(weather.getJSONObject("main").getDouble("temp")+kelvin)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                String str = new String(e.getMessage().toString());
                String valor = "No se ha podido recuperar los datos desde el servidor. " + str;
            }

        });
    }
}