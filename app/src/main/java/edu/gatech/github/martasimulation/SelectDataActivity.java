package edu.gatech.github.martasimulation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SelectDataActivity extends AppCompatActivity {

    protected static StoreData storeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_data);
    }

    public void startNewTap (View v) {
        MyDBHandler dbHandler = new MyDBHandler(getApplicationContext(), null, null, 1);
        String allBusID = dbHandler.loadHandler();
        String[] tokens = allBusID.split(",");
        for (int i = 0; i < tokens.length; i++) {
            dbHandler.deleteHandler(tokens[i]);
        }
        storeData = new StoreData();
        storeData.readBusData(getApplicationContext());

        List<Bus> buses = new ArrayList<>();
        int sizeOfBuses = storeData.buses.size();
        for(int i = 0; i < sizeOfBuses; i++) {
            if (storeData.buses.get(i) != null) {
                buses.add(storeData.buses.get(i));
            }
        }
        for (int i = 0; i < buses.size(); i++) {
            dbHandler.addHandler(buses.get(i));
        }

        Intent login = new Intent(SelectDataActivity.this, HomeScreenActivity.class);
        SelectDataActivity.this.startActivity(login);
    }

    public void restartTap (View v) {
        MyDBHandler dbHandler = new MyDBHandler(getApplicationContext(), null, null, 1);
        storeData = new StoreData();
        storeData.readBusDataForRestart(getApplicationContext());
        String allBusID = dbHandler.loadHandler();
        String[] tokens = allBusID.split(",");
        List<Bus> buses = storeData.buses;
        int largestForBuses = 0;

        for (int i = 0; i < tokens.length; i++) {
            int id = Integer.parseInt(dbHandler.findHandler(tokens[i]).getId());
            if (id >= buses.size()) {
                for (int j = largestForBuses; j <= id; j++) {
                    buses.add(null);
                }
                if (id > largestForBuses) {
                    largestForBuses = id;
                }
            }
            buses.set(id, dbHandler.findHandler(tokens[i]));
        }
        Intent login = new Intent(SelectDataActivity.this, HomeScreenActivity.class);
        SelectDataActivity.this.startActivity(login);
    }

}
