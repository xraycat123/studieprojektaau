package dk.aau.gr6406.trainez;


import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.ArrayList;

/**
 * @author Group 6404, Aalborg University, Sundhedsteknologi, 6th semester
 * @version 1.0
 */

public class DbHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 21;
    private static final String DATABASE_NAME = "trainEZDB.db";
    public static final String TABLE_WEIGHT = "weightMeasurements";
    public static final String WEIGHT_COLUMN_ID = "_id";
    public static final String WEIGHT_COLUMN_WEIGHT = "weight";
    public static final String WEIGHT_COLUMN_DATE = "date";

    public static final String TABLE_EXERCISES = "exerciseMeasurements";
    public static final String EXERCISE_COLUMN_ID = "_id2";
    public static final String EXERCISE_COLUMN_DATE = "date";
    public static final String EXERCISE_COLUMN_EX1 = "exercise1";
    public static final String EXERCISE_COLUMN_EX2 = "exercise2";
    public static final String EXERCISE_COLUMN_EX3 = "exercise3";
    public static final String EXERCISE_COLUMN_EX4 = "exercise4";
    public static final String EXERCISE_COLUMN_EX5 = "exercise5";
    public static final String EXERCISE_COLUMN_EX6 = "exercise6";
    public static final String EXERCISE_COLUMN_EX7 = "exercise7";
    public static final String EXERCISE_COLUMN_EX8 = "exercise8";
    public static final String EXERCISE_COLUMN_EX9 = "exercise9";
    public static final String EXERCISE_COLUMN_EX10 = "exercise10";
    public static final String EXERCISE_COLUMN_EX11 = "exercise11";
    public static final String EXERCISE_COLUMN_EX12 = "exercise12";
    public static final String EXERCISE_COLUMN_EX13 = "exercise13";
    public static final String EXERCISE_COLUMN_EX14 = "exercise14";
    public static final String EXERCISE_COLUMN_EX15 = "exercise15";


    //We need to pass database information along to superclass
    public DbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createWieghtTable = "CREATE TABLE " + TABLE_WEIGHT + "(" +
                WEIGHT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WEIGHT_COLUMN_WEIGHT + " TEXT," + WEIGHT_COLUMN_DATE + " TEXT" +
                ");";


        String createDysExerciseTable = "CREATE TABLE " + TABLE_EXERCISES + "(" +
                EXERCISE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EXERCISE_COLUMN_EX1 + " TEXT," + EXERCISE_COLUMN_EX2 + " TEXT," + EXERCISE_COLUMN_EX3
                + " TEXT," + EXERCISE_COLUMN_EX4 + " TEXT," + EXERCISE_COLUMN_EX5 + " TEXT," +
                EXERCISE_COLUMN_EX6 + " TEXT," + EXERCISE_COLUMN_EX7 + " TEXT," +
                EXERCISE_COLUMN_EX8 + " TEXT," + EXERCISE_COLUMN_EX9 + " TEXT," +
                EXERCISE_COLUMN_EX10 + " TEXT," + EXERCISE_COLUMN_EX11 + " TEXT," +
                EXERCISE_COLUMN_EX12 + " TEXT," + EXERCISE_COLUMN_EX13 + " TEXT," + EXERCISE_COLUMN_EX14 + " TEXT," +
                EXERCISE_COLUMN_EX15 + " TEXT," +
                EXERCISE_COLUMN_DATE + " TEXT" +
                ");";


        db.execSQL(createWieghtTable);
        db.execSQL(createDysExerciseTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        onCreate(db);
    }

    //Add a new weight measurement row to the database
    public void addWeightMeasurement(WeightMeasurement weight) {
        ContentValues values = new ContentValues();
        values.put(WEIGHT_COLUMN_WEIGHT, weight.get_kilo());
        values.put(WEIGHT_COLUMN_DATE, weight.get_date());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_WEIGHT, null, values);
        db.close();
    }

    // Add a new exercise measurement row to the database
    public void addExerciseMeasurement(ExerciseMeasurement exercise) {

        ContentValues values2 = new ContentValues();

        values2.put(EXERCISE_COLUMN_DATE, exercise.get_date());
        values2.put(EXERCISE_COLUMN_EX1, exercise.getExercies().get(0));
        values2.put(EXERCISE_COLUMN_EX2, exercise.getExercies().get(1));
        values2.put(EXERCISE_COLUMN_EX3, exercise.getExercies().get(2));
        values2.put(EXERCISE_COLUMN_EX4, exercise.getExercies().get(3));
        values2.put(EXERCISE_COLUMN_EX5, exercise.getExercies().get(4));
        values2.put(EXERCISE_COLUMN_EX6, exercise.getExercies().get(5));
        values2.put(EXERCISE_COLUMN_EX7, exercise.getExercies().get(6));
        values2.put(EXERCISE_COLUMN_EX8, exercise.getExercies().get(7));
        values2.put(EXERCISE_COLUMN_EX9, exercise.getExercies().get(8));
        values2.put(EXERCISE_COLUMN_EX10, exercise.getExercies().get(9));
        values2.put(EXERCISE_COLUMN_EX11, exercise.getExercies().get(10));
        values2.put(EXERCISE_COLUMN_EX12, exercise.getExercies().get(11));
        values2.put(EXERCISE_COLUMN_EX13, exercise.getExercies().get(12));
        values2.put(EXERCISE_COLUMN_EX14, exercise.getExercies().get(13));
        values2.put(EXERCISE_COLUMN_EX15, exercise.getExercies().get(14));
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_EXERCISES, null, values2);
        db.close();

    }

    //Delete a exercisemeasurement from the database
    public void deleteExercice(String exercise) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EXERCISES + " WHERE " + EXERCISE_COLUMN_EX1 + "=\"" + exercise + "\";");
    }


    //Delete a weightmeasurement from the database
    public void deleteWeight(String weight) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_WEIGHT + " WHERE " + WEIGHT_COLUMN_WEIGHT + "=\"" + weight + "\";");
    }


    public String databaseToStringExercise() {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EXERCISES + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results.
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("exercise1")) != null) {
                dbString += c.getString(c.getColumnIndex("exercise1")) + " ";
                dbString += c.getString(c.getColumnIndex("exercise2")) + " ";
                dbString += c.getString(c.getColumnIndex("exercise3")) + " ";
                dbString += c.getString(c.getColumnIndex("exercise4")) + " ";
                dbString += c.getString(c.getColumnIndex("exercise5")) + " ";
                dbString += c.getString(c.getColumnIndex("exercise6")) + " ";
                dbString += c.getString(c.getColumnIndex("exercise7")) + " ";
                dbString += c.getString(c.getColumnIndex("exercise8")) + " ";
                dbString += c.getString(c.getColumnIndex("exercise9")) + " ";
                dbString += c.getString(c.getColumnIndex("exercise10")) + " ";
                dbString += c.getString(c.getColumnIndex("exercise11")) + " ";
                dbString += c.getString(c.getColumnIndex("exercise12")) + " ";
                dbString += c.getString(c.getColumnIndex("exercise13")) + " ";
                dbString += c.getString(c.getColumnIndex("exercise14")) + " ";
                dbString += " " + c.getString(c.getColumnIndex("exercise15")) + "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }


    ArrayList<ExerciseMeasurement> databaseToStringExerciseArray() {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EXERCISES + " WHERE 1";
        ArrayList<ExerciseMeasurement> resultList = new ArrayList<>();
        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results.
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("exercise1")) != null) {
                ExerciseMeasurement dysExerciseMeasurement = new ExerciseMeasurement();
                dysExerciseMeasurement.set_date(c.getString(c.getColumnIndex("date")));

                ArrayList<Integer> exercises = new ArrayList<>();


                int x = 1;
                do {
                    exercises.add(Integer.parseInt(c.getString(c.getColumnIndex("exercise" + x))));
                    x++;
                } while (x < 16);
                dysExerciseMeasurement.setExercises(exercises);

/*
                dysExerciseMeasurement.set_ex1(Integer.parseInt(c.getString(c.getColumnIndex("exercise1"))));
                dysExerciseMeasurement.set_ex2(Integer.parseInt(c.getString(c.getColumnIndex("exercise2"))));
                dysExerciseMeasurement.set_ex3(Integer.parseInt(c.getString(c.getColumnIndex("exercise3"))));
                dysExerciseMeasurement.set_ex4(Integer.parseInt(c.getString(c.getColumnIndex("exercise4"))));
                dysExerciseMeasurement.set_ex5(Integer.parseInt(c.getString(c.getColumnIndex("exercise5"))));
                dysExerciseMeasurement.set_ex6(Integer.parseInt(c.getString(c.getColumnIndex("exercise6"))));
                dysExerciseMeasurement.set_ex7(Integer.parseInt(c.getString(c.getColumnIndex("exercise7"))));
                dysExerciseMeasurement.set_ex8(Integer.parseInt(c.getString(c.getColumnIndex("exercise8"))));
                dysExerciseMeasurement.set_ex9(Integer.parseInt(c.getString(c.getColumnIndex("exercise9"))));
                dysExerciseMeasurement.set_ex10(Integer.parseInt(c.getString(c.getColumnIndex("exercise10"))));
                dysExerciseMeasurement.set_ex11(Integer.parseInt(c.getString(c.getColumnIndex("exercise11"))));
                dysExerciseMeasurement.set_ex12(Integer.parseInt(c.getString(c.getColumnIndex("exercise12"))));
                dysExerciseMeasurement.set_ex13(Integer.parseInt(c.getString(c.getColumnIndex("exercise13"))));
                dysExerciseMeasurement.set_ex14(Integer.parseInt(c.getString(c.getColumnIndex("exercise14"))));
                dysExerciseMeasurement.set_ex15(Integer.parseInt(c.getString(c.getColumnIndex("exercise15"))));

*/

                resultList.add(dysExerciseMeasurement);
            }
            c.moveToNext();
        }
        db.close();
        return resultList;
    }


    public String databaseToStringWeight() {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_WEIGHT + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results.
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("date")) != null) {
                //  dbString += c.getString(c.getColumnIndex("date"));
                dbString += " " + c.getString(c.getColumnIndex("weight")) + "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }


    public ArrayList<WeightMeasurement> databaseToStringWeightDate() {

        ArrayList<WeightMeasurement> resultList = new ArrayList<WeightMeasurement>();

        // String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_WEIGHT + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results.
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("date")) != null) {
                //// dbString += c.getString(c.getColumnIndex("date")) + "\n";
                //  dbString += " " + c.getString(c.getColumnIndex("weight")) + "\n";
                //   resultList.add(c.getString(c.getColumnIndex("date")));
                WeightMeasurement weightDate = new WeightMeasurement(Float.parseFloat(c.getString(c.getColumnIndex("weight"))));
                weightDate.set_date(c.getString(c.getColumnIndex("date")));
                resultList.add(weightDate);
            }
            c.moveToNext();
        }
        db.close();
        return resultList;
    }


}
