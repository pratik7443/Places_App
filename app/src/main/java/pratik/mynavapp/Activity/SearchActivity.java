package pratik.mynavapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pratik.mynavapp.Model.PlaceGetSet;
import pratik.mynavapp.R;
import pratik.mynavapp.Util.MyLog;

public class SearchActivity extends AppCompatActivity {
    private Context ctx = SearchActivity.this;

    private MyLog l = new MyLog("SearchActivity");
    private ArrayList<PlaceGetSet> locationList;
    private TextView txtCar, txtTrain;
    private Spinner spnLocation;
    private MySpinnerAdapter mySpinnerAdapter;
    private PlaceGetSet placeGetSet;
    private FloatingActionButton fab;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        l.m("in SearchActivity  ");

        locationList = this.getIntent().getExtras().getParcelableArrayList("DATA");
        l.m("locationList 2 count - " + locationList.size());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();

        //Set Adapter To spinner
        spnLocation.setAdapter(new MySpinnerAdapter(this, R.layout.custom_spinner,
                locationList));
        //Disable Home
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        listener();

    }

    private void listener() {
        spnLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtCar.setVisibility(View.VISIBLE);
                txtTrain.setVisibility(View.VISIBLE);
                if (locationList.get(position).getlByCar() != null)
                    txtCar.setText(locationList.get(position).getlByCar().toString());
                else
                    txtCar.setVisibility(View.GONE);

                if (locationList.get(position).getlByTrain() != null)
                    txtTrain.setText(locationList.get(position).getlByTrain().toString());
                else
                    txtTrain.setVisibility(View.GONE);

                placeGetSet = new PlaceGetSet(
                        locationList.get(position).getlId(),
                        locationList.get(position).getlName(),
                        locationList.get(position).getlByCar(),
                        locationList.get(position).getlByTrain(),
                        locationList.get(position).getlLat(),
                        locationList.get(position).getlLon());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                l.m("PLaceGetSet - " + placeGetSet.getlName());
                Intent i = new Intent(ctx, MapsActivity.class);
                i.putExtra("LOC", placeGetSet);
                startActivity(i);

            }
        });
    }


    //findViewById here
    private void init() {
        spnLocation = (Spinner) findViewById(R.id.spinner_location);
        txtCar = (TextView) findViewById(R.id.textView_car);
        txtTrain = (TextView) findViewById(R.id.textView_train);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    //Adapter for Spinner
    public class MySpinnerAdapter extends ArrayAdapter<PlaceGetSet> {
        ArrayList<PlaceGetSet> locationList;

        public MySpinnerAdapter(Context context, int resource, ArrayList<PlaceGetSet> locationList) {
            super(context, resource, locationList);
            this.locationList = locationList;
        }

        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getCustomView(position, cnvtView, prnt);
        }

        @Override
        public View getView(int pos, View cnvtView, ViewGroup prnt) {
            return getCustomView(pos, cnvtView, prnt);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.custom_spinner, parent, false);
            TextView main_text = (TextView) mySpinner.findViewById(R.id.text_main_seen);
            main_text.setText(locationList.get(position).getlName().toString());
            TextView subSpinner = (TextView) mySpinner.findViewById(R.id.sub_text_seen);

            String strMode = "By: ";

            if (locationList.get(position).getlByCar() != null)
                strMode = strMode + "Car";
            if (locationList.get(position).getlByTrain() != null)
                strMode = strMode + ",Train";

            subSpinner.setText(strMode);
            return mySpinner;
        }


    }
}
