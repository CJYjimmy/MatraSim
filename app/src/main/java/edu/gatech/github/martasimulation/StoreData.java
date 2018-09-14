package edu.gatech.github.martasimulation;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class StoreData {
    protected static List<Bus> buses;
    protected static List<Stop> stops;
    protected static List<Route> routes;

    private static int largestForBuses;
    private static int largestForStops;
    private static int largestForRoutes;

    public void readBusData(Context c) {
        InputStream is = c.getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        buses = new ArrayList<>();
        stops = new ArrayList<>();
        routes = new ArrayList<>();

        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");

                if (tokens[0].equals("bus")){
                    Bus bus = new Bus();
                    bus.setId(tokens[1]);
                    bus.setRoute(tokens[2]);
                    bus.setLocal(Integer.parseInt(tokens[3]));
                    bus.setRider(Integer.parseInt(tokens[4]));
                    bus.setCapacity(Integer.parseInt(tokens[5]));
                    bus.setSpeed(Integer.parseInt(tokens[6]));
                    int id = Integer.parseInt(tokens[1]);
                    if (id >= buses.size()) {
                        for (int i = largestForBuses; i <= id; i++) {
                            buses.add(null);
                        }
                        if (id > largestForBuses) {
                            largestForBuses = id;
                        }
                    }
                    buses.set(id,bus);
                }
                else if (tokens[0].equals("route")) {
                    Route route = new Route();
                    route.setId(tokens[1]);
                    route.setIndex(tokens[2]);
                    route.setName(tokens[3]);
                    ArrayList<Integer> temp = new ArrayList<>();
                    for (int i = 4; i < tokens.length; i++) {
                        if (Integer.parseInt(tokens[i]) != -1) {
                            temp.add(Integer.parseInt(tokens[i]));
                        }
                    }
                    route.setStops(temp);
                    int id = Integer.parseInt(tokens[1]);
                    if (id >= routes.size()) {
                        for (int i = largestForRoutes; i <= id; i++) {
                            routes.add(null);
                        }
                        if (id > largestForRoutes) {
                            largestForRoutes = id;
                        }
                    }
                    routes.set(id,route);
                }
                else if (tokens[0].equals("stop")) {
                    Stop stop = new Stop();
                    stop.setId(tokens[1]);
                    stop.setName(tokens[2]);
                    stop.setRider(tokens[3]);
                    stop.setLatitude(Double.parseDouble(tokens[4]));
                    stop.setLongitude(Double.parseDouble(tokens[5]));
                    int id = Integer.parseInt(tokens[1]);
                    if (id >= stops.size()) {
                        for (int i = largestForStops; i <= id; i++) {
                            stops.add(null);
                        }
                        if (id > largestForStops) {
                            largestForStops = id;
                        }
                    }
                    stops.set(id,stop);
                }

            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }
    }

    public void readBusDataForRestart(Context c) {
        InputStream is = c.getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        buses = new ArrayList<>();
        stops = new ArrayList<>();
        routes = new ArrayList<>();

        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");

                if (tokens[0].equals("route")) {
                    Route route = new Route();
                    route.setId(tokens[1]);
                    route.setIndex(tokens[2]);
                    route.setName(tokens[3]);
                    ArrayList<Integer> temp = new ArrayList<>();
                    for (int i = 4; i < tokens.length; i++) {
                        if (Integer.parseInt(tokens[i]) != -1) {
                            temp.add(Integer.parseInt(tokens[i]));
                        }
                    }
                    route.setStops(temp);
                    int id = Integer.parseInt(tokens[1]);
                    if (id >= routes.size()) {
                        for (int i = largestForRoutes; i <= id; i++) {
                            routes.add(null);
                        }
                        if (id > largestForRoutes) {
                            largestForRoutes = id;
                        }
                    }
                    routes.set(id,route);
                }
                else if (tokens[0].equals("stop")) {
                    Stop stop = new Stop();
                    stop.setId(tokens[1]);
                    stop.setName(tokens[2]);
                    stop.setRider(tokens[3]);
                    stop.setLatitude(Double.parseDouble(tokens[4]));
                    stop.setLongitude(Double.parseDouble(tokens[5]));
                    int id = Integer.parseInt(tokens[1]);
                    if (id >= stops.size()) {
                        for (int i = largestForStops; i <= id; i++) {
                            stops.add(null);
                        }
                        if (id > largestForStops) {
                            largestForStops = id;
                        }
                    }
                    stops.set(id,stop);
                }

            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }
    }


}
