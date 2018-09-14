package edu.gatech.github.martasimulation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class SimulationPage extends AppCompatActivity {

    private ListView simListview;
    private List<String> busList;
    private int time;
    private PriorityQueue<Bus> pq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation_page);
        simListview = (ListView) findViewById(R.id.simList);

        MyDBHandler dbHandler = new MyDBHandler(getApplicationContext(), null, null, 1);

        List<Bus> buses = new ArrayList<>();
        int sizeOfBuses = SelectDataActivity.storeData.buses.size();
        for(int i = 0; i < sizeOfBuses; i++) {
            if (SelectDataActivity.storeData.buses.get(i) != null) {
                buses.add(SelectDataActivity.storeData.buses.get(i));
            }
        }

        pq = new PriorityQueue<>(buses);

        busList = new ArrayList<>();

        for (Bus b : pq) {
            if (b.getArrivalTime() == 0) {
                b.updateData();
            }
            dbHandler.updateHandler(b.getId(),b.getRoute(),b.getLocal(),b.getRider(),
                    b.getCapacity(),b.getCurrStop(),b.getNextStop(),b.getArrivalTime(),b.getSpeed());
            busList.add(b.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.my_simulate_listview, busList);
        simListview.setAdapter(adapter);
    }

    public void runEvent(View v) {
        TextView tv = (TextView) findViewById(R.id.simListViewDetail);
        simListview = (ListView) findViewById(R.id.simList);
        Bus firstBus = pq.remove();
        firstBus.goToNextStop();

        MyDBHandler dbHandler = new MyDBHandler(getApplicationContext(), null, null, 1);

        dbHandler.updateHandler(firstBus.getId(),firstBus.getRoute(),firstBus.getLocal(),firstBus.getRider(),
                firstBus.getCapacity(),firstBus.getCurrStop(),firstBus.getNextStop(),firstBus.getArrivalTime(),firstBus.getSpeed());

        pq.add(firstBus);
        busList.clear();
        for (Bus b:pq) {
            busList.add(b.toString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.my_simulate_listview, busList);
        simListview.setAdapter(adapter);
    }

    public void returnMainTap2 (View v) {
        Intent returnToMain = new Intent(SimulationPage.this, HomeScreenActivity.class);
        SimulationPage.this.startActivity(returnToMain);
    }

    public void toMap (View v) {
        Intent toList = new Intent(SimulationPage.this, MapActivity.class);
        startActivity(toList);
    }

}
