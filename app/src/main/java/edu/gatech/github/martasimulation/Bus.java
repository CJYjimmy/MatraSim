package edu.gatech.github.martasimulation;

import android.support.annotation.NonNull;

public class Bus implements Comparable {
    private String id;
    private String route;
    private int local;
    private int rider;
    private int capacity;
    private int nextStop;
    private int currStop;
    private int arrivalTime;
    private double speed;

    @Override
    public int compareTo(@NonNull Object o) {
        return this.arrivalTime - ((Bus)o).getArrivalTime();
    }

    public int getCurrStop() {
        return currStop;
    }

    public void setCurrStop(int currStop) {
        this.currStop = currStop;
    }

    public void goToNextStop() {
        int randomForExit;
        int randomForBoard;
        randomForExit = 2 + (int) (Math.random() * 4);
        randomForBoard = (int) (Math.random() * 11);
        if (getRider() < randomForExit) {
            randomForExit = getRider();
        }
        if (capacity - (getRider() - randomForExit) < randomForBoard) {
            randomForBoard = capacity - (getRider() - randomForExit);
        }
        this.rider = rider + randomForBoard - randomForExit;

        int currStop = 0;
        int nextStop = 0;
        double distance = 0.0;
        int time = 0;
        currStop = local + 1;
        Route thisRoute = SelectDataActivity.storeData.routes.get(Integer.parseInt(this.route));
        if (currStop == thisRoute.getStops().size()) {
            currStop = 0;
        }
        setLocal(currStop);
        if (currStop == thisRoute.getStops().size() - 1) {
            nextStop = thisRoute.getStops().get(0);
        } else {
            nextStop = thisRoute.getStops().get(currStop + 1);
        }
        currStop = thisRoute.getStops().get(currStop);

        Stop stopOfCurr = SelectDataActivity.storeData.stops.get(currStop);
        Stop stopOfNext = SelectDataActivity.storeData.stops.get(nextStop);

        double latitudeOfCurr = stopOfCurr.getLatitude();
        double longitudeOfCurr = stopOfCurr.getLongitude();
        double latitudeOfNext = stopOfNext.getLatitude();
        double longitudeOfNext = stopOfNext.getLongitude();
        distance = 70 * Math.sqrt(Math.pow((latitudeOfCurr - latitudeOfNext), 2) + Math.pow((longitudeOfCurr - longitudeOfNext), 2));
        time = 1 + (int) (distance * 60 / speed);
        this.nextStop = nextStop;
        this.currStop = currStop;
        arrivalTime += time;
    }

    public void updateData() {
        int currStop = 0;
        int nextStop = 0;
        double distance = 0.0;
        int time = 0;
        currStop = local;
        Route thisRoute = SelectDataActivity.storeData.routes.get(Integer.parseInt(this.route));
        if (currStop == thisRoute.getStops().size()) {
            currStop = 0;
        }
        setLocal(currStop);
        if (currStop == thisRoute.getStops().size() - 1) {
            nextStop = thisRoute.getStops().get(0);
        } else {
            nextStop = thisRoute.getStops().get(currStop + 1);
        }
        currStop = thisRoute.getStops().get(currStop);

        Stop stopOfCurr = SelectDataActivity.storeData.stops.get(currStop);
        Stop stopOfNext = SelectDataActivity.storeData.stops.get(nextStop);

        double latitudeOfCurr = stopOfCurr.getLatitude();
        double longitudeOfCurr = stopOfCurr.getLongitude();
        double latitudeOfNext = stopOfNext.getLatitude();
        double longitudeOfNext = stopOfNext.getLongitude();
        distance = 70 * Math.sqrt(Math.pow((latitudeOfCurr - latitudeOfNext), 2) + Math.pow((longitudeOfCurr - longitudeOfNext), 2));
        time = 1 + (int) (distance * 60 / speed);
        this.nextStop = nextStop;
        this.currStop = currStop;
        arrivalTime += time;
    }

    public String getId() {
        return id;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getCapacity() {

        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRider() {

        return rider;
    }

    public void setRider(int rider) {
        this.rider = rider;
    }

    public int getLocal() {

        return local;
    }

    public void setLocal(int local) {
        this.local = local;
    }

    public String getRoute() {

        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setId(String id) {

        this.id = id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getNextStop() {
        return nextStop;
    }

    public void setNextStop(int nextStop) {
        this.nextStop = nextStop;
    }

    public String toString() {
        return "Bus" + id + ", Next Stop: " + nextStop + ", Arrival Time: " + arrivalTime
                + ", passenger: " + rider;
    }
}
