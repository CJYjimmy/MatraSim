package edu.gatech.github.martasimulation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper {


    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "busesDB";
    public static final String TABLE_NAME = "Bus";
    public static final String COLUMN_ID = "busID";
    public static final String COLUMN_ROUTE = "routeID";
    public static final String COLUMN_LOCAL = "local";
    public static final String COLUMN_RIDER = "rider";
    public static final String COLUMN_CAPACITY = "capacity";
    public static final String COLUMN_CURRSTOP = "currStop";
    public static final String COLUMN_NEXTSTOP = "nextStop";
    public static final String COLUMN_ARRIVALTIME = "arrivalTime";
    public static final String COLUMN_SPEED = "speed";

    //initialize the database
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public boolean addHandler(Bus b) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, b.getId());
        values.put(COLUMN_ROUTE, b.getRoute());
        values.put(COLUMN_LOCAL, b.getLocal());
        values.put(COLUMN_RIDER, b.getRider());
        values.put(COLUMN_CAPACITY, b.getCapacity());
        values.put(COLUMN_CURRSTOP, b.getCurrStop());
        values.put(COLUMN_NEXTSTOP, b.getNextStop());
        values.put(COLUMN_ARRIVALTIME, b.getArrivalTime());
        values.put(COLUMN_SPEED, b.getSpeed());
        SQLiteDatabase db = this.getWritableDatabase();
        long lon = db.insert(TABLE_NAME, null, values);
        db.close();
        return lon != -1;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID +
                " TEXT PRIMARY KEY, " + COLUMN_ROUTE + " TEXT, " + COLUMN_LOCAL + " INTEGER, " +
                COLUMN_RIDER + " INTEGER, " + COLUMN_CAPACITY + " INTEGER, " +
                COLUMN_CURRSTOP + " INTEGER, " + COLUMN_NEXTSTOP + " INTEGER, " +
                COLUMN_ARRIVALTIME + " INTEGER, " + COLUMN_SPEED + " REAL)";
        db.execSQL(CREATE_TABLE);
    }

    public String loadHandler() {
        String result = "";
        String query = "Select * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String result_0 = cursor.getString(0);
            result += result_0 + ",";
        }
        cursor.close();
        db.close();
        return result;
    }

    public Bus findHandler(String busID) {
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + "'" + busID + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Bus b = new Bus();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            b.setId(cursor.getString(0));
            b.setRoute(cursor.getString(1));
            b.setLocal(cursor.getInt(2));
            b.setRider(cursor.getInt(3));
            b.setCapacity(cursor.getInt(4));
            b.setCurrStop(cursor.getInt(5));
            b.setNextStop(cursor.getInt(6));
            b.setArrivalTime(cursor.getInt(7));
            b.setSpeed(cursor.getDouble(8));
            cursor.close();
        } else {
            b = null;
        }
        db.close();
        return b;
    }

    public boolean deleteHandler(String ID) {
        boolean result = false;
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "= '" + ID + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Bus b = new Bus();
        if (cursor.moveToFirst()) {
            b.setId(cursor.getString(0));
            db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                    new String[] {
                b.getId()
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    public boolean updateHandler(String ID, String route, int local, int rider, int capacity,
                                 int currStop, int nextStop, int arrivalTime, double speed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_ID, ID);
        args.put(COLUMN_ROUTE, route);
        args.put(COLUMN_LOCAL, local);
        args.put(COLUMN_RIDER, rider);
        args.put(COLUMN_CAPACITY, capacity);
        args.put(COLUMN_CURRSTOP, currStop);
        args.put(COLUMN_NEXTSTOP, nextStop);
        args.put(COLUMN_ARRIVALTIME, arrivalTime);
        args.put(COLUMN_SPEED, speed);
        return db.update(TABLE_NAME, args, COLUMN_ID + " = ?", new String[] { ID }) > 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
