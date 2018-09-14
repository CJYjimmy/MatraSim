package edu.gatech.github.martasimulation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class DataDetailActivity extends AppCompatActivity {

    private Bus bus;
    private Route route;
    private int randomForExit;
    private int randomForBoard;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_detail);

        if (getIntent().hasExtra("edu.gatech.github.martasimulation")) {
            TextView tv = (TextView) findViewById(R.id.dataDetailtextView);
            text = getIntent().getExtras().getString("edu.gatech.github.martasimulation");
            if (text.contains("bus")) {
                randomForExit = 2 + (int) (Math.random() * 4);
                randomForBoard = (int) (Math.random() * 11);
                bus = ListActivity.bus;
                route = ListActivity.route;
                int capacity = bus.getCapacity();
                if (bus.getRider() < randomForExit) {
                    randomForExit = bus.getRider();
                }
                if (capacity - (bus.getRider() - randomForExit) < randomForBoard) {
                    randomForBoard = capacity - (bus.getRider() - randomForExit);
                }

                text = text + ", Would Exit: " + randomForExit + ", Would Board: " + randomForBoard + ".";
            }

            tv.setText(text);
        }
    }

    public void goToNextStop (View v) {
        TextView tv = (TextView) findViewById(R.id.dataDetailtextView);
        MyDBHandler dbHandler = new MyDBHandler(getApplicationContext(), null, null, 1);
        if (text.contains("bus")) {
            int currStop = 0;
            int nextStop = 0;
            double distance = 0.0;
            int time = 0;
            currStop = bus.getLocal() + 1;
            if (currStop == route.getStops().size()) {
                currStop = 0;
            }
            bus.setLocal(currStop);
            if (currStop == route.getStops().size() - 1) {
                nextStop = route.getStops().get(0);
            } else {
                nextStop = route.getStops().get(currStop + 1);
            }
            currStop = route.getStops().get(currStop);


            Stop stopOfCurr = SelectDataActivity.storeData.stops.get(currStop);
            Stop stopOfNext = SelectDataActivity.storeData.stops.get(nextStop);


            double latitudeOfCurr = stopOfCurr.getLatitude();
            double longitudeOfCurr = stopOfCurr.getLongitude();
            double latitudeOfNext = stopOfNext.getLatitude();
            double longitudeOfNext = stopOfNext.getLongitude();
            DecimalFormat df = new DecimalFormat("#.#");
            distance = 70 * Math.sqrt(Math.pow((latitudeOfCurr - latitudeOfNext), 2) + Math.pow((longitudeOfCurr - longitudeOfNext), 2));
            time = 1 + (int) (distance * 60 / bus.getSpeed());
            bus.setCurrStop(currStop);
            bus.setNextStop(nextStop);
            bus.setArrivalTime(bus.getArrivalTime() + time);

            bus.setRider(bus.getRider() - randomForExit + randomForBoard);

            text =  "bus, ID: " + bus.getId() + ", " + bus.getRoute() + ", " + bus.getLocal() + ", "
                    + bus.getRider() + ", " + bus.getCapacity() + ", " + bus.getSpeed()
                    + ", Current Stop: " + currStop + ", Next Stop: "
                    + nextStop + ", Distance to next stop is " + df.format(distance) + " miles, and need "
                    + time + " minutes.";
            randomForExit = 2 + (int) (Math.random() * 4);
            randomForBoard = (int) (Math.random() * 11);
            bus = ListActivity.bus;
            route = ListActivity.route;
            int capacity = bus.getCapacity();
            if (bus.getRider() < randomForExit) {
                randomForExit = bus.getRider();
            }
            if (capacity - (bus.getRider() - randomForExit) < randomForBoard) {
                randomForBoard = capacity - (bus.getRider() - randomForExit);
            }

            text = text + ", Would Exit: " + randomForExit + ", Would Board: " + randomForBoard + ".";
            tv.setText(text);

            dbHandler.updateHandler(bus.getId(),bus.getRoute(),bus.getLocal(),bus.getRider(),
                    bus.getCapacity(),bus.getCurrStop(),bus.getNextStop(),bus.getArrivalTime(),bus.getSpeed());
        } else {
            Toast myToast = Toast.makeText(getApplicationContext(),"Canceled", Toast.LENGTH_LONG);
            myToast.show();
        }
    }

    public void returnB (View v) {
        Intent returnToLast = new Intent(DataDetailActivity.this, ListActivity.class);
        DataDetailActivity.this.startActivity(returnToLast);
        Toast myToast = Toast.makeText(getApplicationContext(),"Canceled", Toast.LENGTH_LONG);
        myToast.show();
    }

    public void returnMainTap (View v) {
        Intent returnToMain = new Intent(DataDetailActivity.this, HomeScreenActivity.class);
        DataDetailActivity.this.startActivity(returnToMain);
        Toast myToast = Toast.makeText(getApplicationContext(),"Canceled", Toast.LENGTH_LONG);
        myToast.show();
    }
}
