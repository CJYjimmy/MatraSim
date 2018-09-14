package edu.gatech.github.martasimulation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.shapes.OvalShape;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class myMap extends View {

    Context context2;
    Paint paint;
    OvalShape oval;
    List<Stop> stops = StoreData.stops;
    List<Route> routes = StoreData.routes;
    List<Bus> buses = StoreData.buses;

    public myMap(Context context) {
        super(context);
        context2 = context;

        paint = new Paint();
        oval = new OvalShape();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context2).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int canvasHeight = dm.widthPixels;
        int canvasWidth = dm.widthPixels;
//        int canvasHeight = 1080;
//        int canvasWidth = 1080;
        int border = 200;
        int originX = border;
        int originY = border;
        int horiInc = (canvasWidth - 2 * border) / 16;
        int vertiInc = (canvasHeight - 2 * border) / 16;
        int diameter = 40;


        // drawing all the stops
        for(int i = 0; i < stops.size(); i++) {
            Stop tempStop = stops.get(i);
            if (tempStop != null) {
                int y = (int)(originY + horiInc * tempStop.getLongitude() * 100);
                int x = (int)(originX + vertiInc * tempStop.getLatitude() * 100);
                canvas.drawOval(x,y,(x+diameter),(y+diameter),paint);
                paint.setColor(Color.BLACK);
                paint.setTextSize(25);
                canvas.drawText(tempStop.getName(), x + 40, y + 40, paint);
            }
        }

        // color list for different routes
        List<Integer> colorList = new ArrayList<>();
        colorList.add(Color.RED);
        colorList.add(Color.BLUE);
        colorList.add(Color.GREEN);

        //painting routes
        int strokeWidth = 16;
        for (int i = 0; i < routes.size(); i++) {
            Route tempRoute = routes.get(i);
            paint.setColor(colorList.get(i%colorList.size()));
            strokeWidth *= 0.5;
            paint.setStrokeWidth(strokeWidth);
            if (tempRoute != null) {
                List<Integer> stopList = tempRoute.getStops();
                for (int j = 0; j < stopList.size() - 1; j++) {
                    if (stopList.get(j + 1) != -1) {
                        Stop tempStopStart = stops.get(stopList.get(j));
                        Stop tempStopEnd = stops.get(stopList.get(j + 1));
                        int y1 = (int)(originY + vertiInc * tempStopStart.getLongitude() * 100) + diameter/2;
                        int x1 = (int)(originX + vertiInc * tempStopStart.getLatitude() * 100) + diameter/2;
                        int y2 = (int)(originY + vertiInc * tempStopEnd.getLongitude() * 100) + diameter/2;
                        int x2 = (int)(originX + vertiInc * tempStopEnd.getLatitude() * 100) + diameter/2;
                        canvas.drawLine(x1, y1, x2, y2,paint);
                    }

                }

            }
        }

        //drawing buses
        int height = 1000;
        for (int i = 0; i < buses.size(); i++) {
            Bus tempBus = buses.get(i);
            if (tempBus != null) {
                paint.setColor(Color.CYAN);
                int x = (int)(originX + vertiInc * stops.get(tempBus.getCurrStop()).getLatitude() * 100);
                int y = (int)(originY + vertiInc * stops.get(tempBus.getCurrStop()).getLongitude() * 100);
                canvas.drawRect(x,y,x+diameter,y+diameter,paint);
                paint.setColor(Color.BLACK);
                paint.setTextSize(20);
                canvas.drawText(tempBus.getId(),x + diameter/2,y + diameter/2,paint);
                paint.setTextSize(40);
                canvas.drawText("Bus: "+ tempBus.getId(),border,height,paint);
                height+=40;
                canvas.drawText("Current Location: " + Integer.toString(tempBus.getCurrStop()),border,height,paint);
                height+=40;
                canvas.drawText("Next Stop: " + Integer.toString(tempBus.getNextStop()),border,height,paint);
                height+=40;
                canvas.drawText("Arrival Time: " + Integer.toString(tempBus.getArrivalTime()),border,height,paint);
                height+=40;
                canvas.drawText("Passenger Count: " + Integer.toString(tempBus.getRider()),border,height,paint);
                height+=100;
            }
        }


    }
}
