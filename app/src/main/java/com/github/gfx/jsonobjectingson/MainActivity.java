package com.github.gfx.jsonobjectingson;

import com.google.common.io.CharStreams;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {

    String jsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputStream is = getResources().openRawResource(R.raw.qiita);
        try {
            jsonString = CharStreams.toString(new InputStreamReader(is, Charset.forName("UTF-8")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runJson();
            }
        }, 1000);
    }

    void runJson() {
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... params) {
                long t0 = System.currentTimeMillis();

                try {
                    JSONArray json = new JSONArray(jsonString);
                    Log.d("XXX", "length: " + json.length());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                return System.currentTimeMillis() - t0;
            }

            @Override
            protected void onPostExecute(Long elapsed) {
                TextView textView = (TextView) findViewById(R.id.json);
                textView.setText("Parse with JSONObject: " + elapsed + "ms");

                runGson();
            }
        }.execute();
    }

    void runGson() {
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... params) {
                long t0 = System.currentTimeMillis();

                JsonArray json = (JsonArray) new JsonParser().parse(jsonString);
                Log.d("XXX", "size: " + json.size());

                return System.currentTimeMillis() - t0;
            }

            @Override
            protected void onPostExecute(Long elapsed) {
                TextView textView = (TextView) findViewById(R.id.gson);
                textView.setText("Parse with Gson: " + elapsed + "ms");
            }
        }.execute();

    }
}
