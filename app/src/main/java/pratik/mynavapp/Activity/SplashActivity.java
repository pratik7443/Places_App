package pratik.mynavapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pratik.mynavapp.Json.JSONParser;
import pratik.mynavapp.Model.PlaceGetSet;
import pratik.mynavapp.R;
import pratik.mynavapp.Util.Connectivity;
import pratik.mynavapp.Util.MyLog;
import pratik.mynavapp.Util.Url;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {

    private Context ctx = SplashActivity.this;
    MyLog l = new MyLog("SPLASH_ACTIVITY");


    public ArrayList<PlaceGetSet> locationList = new ArrayList<>();


    private Connectivity conn = new Connectivity(ctx);
    private TextView txtVersion;
    private ImageButton imgRefresh;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        init();
        listener();
        txtVersion.setText("Version: 1.0.0");

        if (conn.isConnected()) {
            MyAsynTask myAsynTask = new MyAsynTask();
            myAsynTask.execute("");
        } else {
            pb.setVisibility(View.GONE);
            imgRefresh.setVisibility(View.VISIBLE);
            Toast.makeText(ctx, "No network", Toast.LENGTH_LONG).show();
        }
    }


    private void init() {
        txtVersion = (TextView) findViewById(R.id.textViewVersion);
        imgRefresh = (ImageButton) findViewById(R.id.imagebuttonRefresh);
        pb = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void listener() {

        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conn.isConnected()) {
                    MyAsynTask myAsynTask = new MyAsynTask();
                    myAsynTask.execute("");
                } else {
                    imgRefresh.setVisibility(View.VISIBLE);
                    Toast.makeText(ctx, "No network", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //TO Fetch Album List From Server
    class MyAsynTask extends AsyncTask<String, Void, Void> {


        MyLog L = new MyLog("ASYN_TASK");

        private JSONParser jsonParser = new JSONParser();
        //Json Key
        String KEY_ID = "id";
        String KEY_NAME = "name";
        String KEY_FROM_CENTRAL = "fromcentral";
        String KEY_LOCATION = "location";
        String KEY_CAR = "car";
        String KEY_TRAIN = "train";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(String... url) {


            String uri = Uri.parse(Url.url)
                    .buildUpon()
                    .build().toString();

            // String response = GET(uri);
           // String response = jsonParser.GET(Url.url);
            String response = jsonParser.getJSON(Url.url);
            L.m("response :" + response.toString());

            JSONArray jsonarray = null;
            try {
                jsonarray = new JSONArray(response);

                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject obj = jsonarray.getJSONObject(i);
                    L.m("response :" + obj.toString());
                    int id = obj.getInt(KEY_ID);
                    System.out.println(id);
                    String name = obj.getString(KEY_NAME);

                    String fromCentral = obj.getString(KEY_FROM_CENTRAL);

                    JSONObject jsonFromCentral = obj.getJSONObject(KEY_FROM_CENTRAL);
                    String car = null, train = null;
                    for (int j = 0; j < jsonFromCentral.length(); j++) {
                        switch (j) {
                            case 0:
                                car = jsonFromCentral.getString(KEY_CAR);
                                break;
                            case 1:
                                train = jsonFromCentral.getString(KEY_TRAIN);
                                break;
                        }

                    }

                    System.out.println("jsonFromCentral - " + jsonFromCentral);
                    System.out.println("jsonFromCentral Count -" + jsonFromCentral.length());
                    String loc = obj.getString(KEY_LOCATION);
                    JSONObject jsonLoc = obj.getJSONObject(KEY_LOCATION);
                    double lat = 0, lon = 0;
                    for (int j = 0; j < jsonLoc.length(); j++) {
                        lat = jsonLoc.getDouble("latitude");
                        lon = jsonLoc.getDouble("longitude");
                    }


                    System.out.println("id-----" + id);
                    System.out.println("name---" + name);
                    System.out.println("car----" + car);
                    System.out.println("train--" + train);
                    System.out.println("lat----" + lat);
                    System.out.println("lon----" + lon);


                    locationList.add(new PlaceGetSet(id, name, car, train,lat, lon));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            L.m("Done");
            L.m("albumList.count:" + locationList.size());
            if (locationList.size() != 0) {

                Intent i = new Intent(ctx, SearchActivity.class);

                i.putExtra("DATA", locationList);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(ctx, "Data not available", Toast.LENGTH_SHORT).show();

            }
        }
    }


}
