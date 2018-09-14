package edu.gatech.github.martasimulation;

import java.util.ArrayList;

public class Route {
    private String id;
    private String index;
    private String name;
    private ArrayList<Integer> stops;

    public ArrayList<Integer> getStops() {
        return stops;
    }

    public void setStops(ArrayList<Integer> stops) {
        this.stops = stops;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {

        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
