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
                parseJson();
            }
        }, 1000);
    }

    void parseJson() {
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... params) {
                long t0 = System.currentTimeMillis();

                try {
                    JSONArray json = new JSONArray(jsonString);
                    Log.d("XXX", "length(): " + json.length());
                    json = new JSONArray(jsonString);
                    Log.d("XXX", "length(): " + json.length());
                    json = new JSONArray(jsonString);
                    Log.d("XXX", "length(): " + json.length());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                return System.currentTimeMillis() - t0;
            }

            @Override
            protected void onPostExecute(Long elapsed) {
                TextView textView = (TextView) findViewById(R.id.parse_json);
                textView.setText("Parse with JSONObject: " + elapsed + "ms");

                parseGson();
            }
        }.execute();
    }

    void parseGson() {
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... params) {
                long t0 = System.currentTimeMillis();

                JsonArray json = (JsonArray) new JsonParser().parse(jsonString);
                Log.d("XXX", "size(): " + json.size());
                json = (JsonArray) new JsonParser().parse(jsonString);
                Log.d("XXX", "size(): " + json.size());
                json = (JsonArray) new JsonParser().parse(jsonString);
                Log.d("XXX", "size(): " + json.size());

                return System.currentTimeMillis() - t0;
            }

            @Override
            protected void onPostExecute(Long elapsed) {
                TextView textView = (TextView) findViewById(R.id.parse_gson);
                textView.setText("Parse with Gson: " + elapsed + "ms");

                serializeJson();
            }
        }.execute();
    }

    void serializeJson() {
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... params) {
                JSONArray jsonArray;
                try {
                    jsonArray = new JSONArray(jsonString);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                long t0 = System.currentTimeMillis();
                Log.d("XXX", "toString().length(): " + jsonArray.toString().length());
                Log.d("XXX", "toString().length(): " + jsonArray.toString().length());
                Log.d("XXX", "toString().length: " + jsonArray.toString().length());
                return System.currentTimeMillis() - t0;
            }

            @Override
            protected void onPostExecute(Long elapsed) {
                TextView textView = (TextView) findViewById(R.id.serialize_json);
                textView.setText("Serialize with JSONObject: " + elapsed + "ms");

                serializeGson();
            }
        }.execute();
    }

    void serializeGson() {
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... params) {
                JsonArray jsonArray = (JsonArray) new JsonParser().parse(jsonString);

                long t0 = System.currentTimeMillis();
                Log.d("XXX", "toString().length(): " + jsonArray.toString().length());
                Log.d("XXX", "toString().length(): " + jsonArray.toString().length());
                Log.d("XXX", "toString().length: " + jsonArray.toString().length());
                return System.currentTimeMillis() - t0;
            }

            @Override
            protected void onPostExecute(Long elapsed) {
                TextView textView = (TextView) findViewById(R.id.serialize_gson);
                textView.setText("Serialize with Gson: " + elapsed + "ms");
            }
        }.execute();
    }
}
