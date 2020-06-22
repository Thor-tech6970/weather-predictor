package com.WeatherPredictor.myjsonpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView textView1, textView2, textView3, textView4, textView5;

    EditText editText1, editText2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView1 = (TextView) findViewById(R.id.textView1);

        textView2 = (TextView) findViewById(R.id.textView2);

        textView3 = (TextView) findViewById(R.id.textView3);

        textView4 = (TextView) findViewById(R.id.textView4);

        editText1 = (EditText) findViewById(R.id.editText1);

        textView5 = (TextView) findViewById(R.id.textView5);

    }

    public void findWeather(View view) {


        DownloadTask downloadTask = new DownloadTask();

        String output = "";

        try {

            output = downloadTask.execute("https://openweathermap.org/data/2.5/weather?q=" + editText1.getText().toString() + "&appid=439d4b804bc8187953eb36d2a8c26a02").get();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    public class DownloadTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {

            URL url;
            HttpURLConnection urlConnection = null;

            String output = "";

            try {

                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while (data != -1) {

                    char current = (char) data;

                    output += current;

                    data = inputStreamReader.read();

                }

                return output;

            } catch (Exception e) {

                e.printStackTrace();

                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {

            Log.i("JSON", s);

            try {


                JSONObject jsonObject = new JSONObject(s);

                String mainInfo = jsonObject.getString("main");

                JSONObject jpart1 = new JSONObject(mainInfo);

                // Log.i("temp",jpart1.getString("temp"));

                textView2.setText("Temperature: " + jpart1.getString("temp") + " Celsius");

                // Log.i("pressure" , jpart1.getString("pressure"));

                textView3.setText("Pressure: " + jpart1.getString("pressure") + " hPa");

                // Log.i("Weather Info" , mainInfo);

                String weatherInfo = jsonObject.getString("weather");

                JSONArray jsonArray = new JSONArray(weatherInfo);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jpart2 = jsonArray.getJSONObject(i);

                    //Log.i("Sky" , jpart2.getString("main"));

                    textView4.setText("Sky condition: " + jpart2.getString("main"));
                }


            }

            //  String weatherInfo = jsonObject.getString("weather");

            //JSONArray jsonArray2 = new JSONArray(weatherInfo);

            //for (int i = 0; i < jsonArray2.length(); i++) {

            // JSONObject jpart = jsonArray2.getJSONObject(i);

            // Log.i("Main", jpart.getString("main"));

            //textView2.setText("Weather condition: " + jpart.getString("main"));


            //}


            catch (Exception e) {

                e.printStackTrace();


            }
        }
    }


}