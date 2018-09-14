package edu.gatech.github.martasimulation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    protected ListView myListView;
    protected List<String> typeAndId = new LinkedList<>();

    protected static Bus bus;
    protected static Route route;
    private Stop stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        myListView = (ListView) findViewById(R.id.myListView);

        int sizeOfBuses = SelectDataActivity.storeData.buses.size();
        int sizeOfStops = SelectDataActivity.storeData.stops.size();
        int sizeOfRoutes = SelectDataActivity.storeData.routes.size();


        for(int i = 0; i < sizeOfBuses; i++) {
            if (SelectDataActivity.storeData.buses.get(i) != null) {
                typeAndId.add("bus, ID: " + SelectDataActivity.storeData.buses.get(i).getId());
            }
        }
        for(int i = sizeOfBuses; i < (sizeOfStops + sizeOfBuses); i++) {
            if (SelectDataActivity.storeData.stops.get(i - sizeOfBuses) != null) {
                typeAndId.add("stop, ID: " + SelectDataActivity.storeData.stops.get(i - sizeOfBuses).getId());
            }
        }
        for(int i = sizeOfBuses + sizeOfStops; i < (sizeOfBuses + sizeOfRoutes + sizeOfStops); i++) {
            if (SelectDataActivity.storeData.routes.get(i - sizeOfBuses - sizeOfStops) != null) {
                typeAndId.add("route, ID: " + SelectDataActivity.storeData.routes.get(i - sizeOfBuses - sizeOfStops).getId());
            }

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_listview,typeAndId);
        myListView.setAdapter(adapter);
        registerClickCallback();
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.myListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyDBHandler dbHandler = new MyDBHandler(getApplicationContext(), null, null, 1);
                TextView textView = (TextView) view;
                int index = 0;
                String text = textView.getText().toString();
                DecimalFormat df = new DecimalFormat("#.#");

                String showDetail = "";
                if (text.contains("bus")) {
                    index = Integer.parseInt(text.substring(9));


                    bus = SelectDataActivity.storeData.buses.get(index);

                    showDetail = showDetail + "bus, ID: " + bus.getId() + ", " + bus.getRoute() + ", " + bus.getLocal() + ", "
                            + bus.getRider() + ", " + bus.getCapacity() + ", " + bus.getSpeed();
                } else if (text.contains("route")) {
                    index = Integer.parseInt(text.substring(11));


                    route = SelectDataActivity.storeData.routes.get(index);

                    showDetail = showDetail + "route, ID: " + route.getId() + ", " + route.getIndex() + ", "
                            + route.getName();
                    for (int i = 0; i < route.getStops().size(); i++) {
                        showDetail = showDetail + ", " + route.getStops().get(i);
                    }
                } else if (text.contains("stop")) {
                    index = Integer.parseInt(text.substring(10));


                    stop = SelectDataActivity.storeData.stops.get(index);

                    showDetail = showDetail + "stop, ID: " + stop.getId() + ", " + stop.getName() + ", "
                            + stop.getRider() + ", " + stop.getLatitude() + ", " + stop.getLongitude();
                }

                int currStop = 0;
                int nextStop = 0;
                double distance = 0.0;
                int time = 0;
                if (text.contains("bus")) {
                    currStop = bus.getLocal();
                    String currRoute = bus.getRoute();

                    int indexOfRoute = Integer.parseInt(currRoute);


                    route = SelectDataActivity.storeData.routes.get(indexOfRoute);

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

                    distance = 70 * Math.sqrt(Math.pow((latitudeOfCurr - latitudeOfNext), 2) + Math.pow((longitudeOfCurr - longitudeOfNext), 2));
                    time = 1 + (int) (distance * 60 / bus.getSpeed());
                    showDetail = showDetail + ", Current Stop: " + currStop + ", Next Stop: "
                            + nextStop + ", Distance to next stop is " + df.format(distance) + " miles, and need "
                            + time + " minutes.";
                    dbHandler.updateHandler(bus.getId(),bus.getRoute(),bus.getLocal(),bus.getRider(),
                            bus.getCapacity(),bus.getCurrStop(),bus.getNextStop(),bus.getArrivalTime(),bus.getSpeed());
                }

                Intent startIntent = new Intent(getApplicationContext(), DataDetailActivity.class);
                startIntent.putExtra("edu.gatech.github.martasimulation",showDetail);
                startActivity(startIntent);
            }
        });
    }
}
