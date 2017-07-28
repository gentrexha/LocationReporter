package com.grasslever.gentrexha.locationreporter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by GRexha on 25-Jul-17.
 */

class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "locations.db";
    private static final int DB_VERSION = 1;

    private static final String LOCATION_TABLE_NAME = "person";
    private static final String LOCATION_COLUMN_ID = "_id";
    static final String LOCATION_COLUMN_PLACE = "place";
    static final String LOCATION_COLUMN_WEATHER = "weather";
    static final String LOCATION_COLUMN_LAT = "lat";
    static final String LOCATION_COLUMN_LNG = "lng";
    static final String LOCATION_COLUMN_TIME = "time";

    Database(Context context) {
        super(context, DB_NAME , null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + LOCATION_TABLE_NAME + "(" +
                LOCATION_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                LOCATION_COLUMN_PLACE + " TEXT, " +
                LOCATION_COLUMN_WEATHER + " TEXT, " +
                LOCATION_COLUMN_LAT + " TEXT, " +
                LOCATION_COLUMN_LNG + " TEXT, " +
                LOCATION_COLUMN_TIME + " TEXT)"
        );
    }

    public void insertLocation(String name, String weather, String lat, String lng, String time) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOCATION_COLUMN_PLACE, name);
        contentValues.put(LOCATION_COLUMN_WEATHER, weather);
        contentValues.put(LOCATION_COLUMN_LAT, lat);
        contentValues.put(LOCATION_COLUMN_LNG, lng);
        contentValues.put(LOCATION_COLUMN_TIME, time);
        db.insert(LOCATION_TABLE_NAME, null, contentValues);
    }

    public Cursor getAllLocations() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "SELECT * FROM " + LOCATION_TABLE_NAME + " ORDER BY " + LOCATION_COLUMN_ID + " DESC", null );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LOCATION_TABLE_NAME);
        onCreate(db);
    }
}
