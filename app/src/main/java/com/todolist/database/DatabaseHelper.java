package com.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.todolist.models.ToDoModel;
import com.todolist.utilities.AppUtil;

import java.util.ArrayList;

/**
 * Created by dhananjay on 19/3/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DB_ToDoListApp";
    private static final int DATABASE_VERSION = 1;
    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();

    private final Context mContext;
    private final String TABLE_TO_DO_LIST = "TO_DO_LIST";
    private final String KEY_ID = "_ID";
    private final String KEY_TEXT_TO_DO = "TEXT_TO_DO";
    private final String KEY_DATE = "DATE";
    private final String KEY_TIME = "TIME";
    private final String KEY_LOCATION = "LOCATION";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.v(LOG_TAG, "in DatabaseHelper() constructor");
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.v(LOG_TAG, "in onCreate()");

        String query_CreateTable_ToDoList = "CREATE TABLE " + TABLE_TO_DO_LIST + " ( " + KEY_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TEXT_TO_DO + " TEXT, " + KEY_DATE
                + " TEXT, " + KEY_TIME + " TEXT, " + KEY_LOCATION + " TEXT )";

        Log.v(LOG_TAG, "query_CreateTable_ToDoList: " + query_CreateTable_ToDoList);
        sqLiteDatabase.execSQL(query_CreateTable_ToDoList);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.v(LOG_TAG, "in onUpgrade()");
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TO_DO_LIST);

        onCreate(sqLiteDatabase);
    }

    public long insertToDo(ToDoModel toDoModel) {
        long result = 0;
        try {
            if (toDoModel != null) {

                SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

                if (sqLiteDatabase != null) {

                    ContentValues contentValues = new ContentValues();
//                    contentValues.put(KEY_ID, toDoModel.get_id());//Primary Key auto increment
                    contentValues.put(KEY_TEXT_TO_DO, toDoModel.getText_to_do());
                    contentValues.put(KEY_DATE, toDoModel.getDate());
                    contentValues.put(KEY_TIME, toDoModel.getTime());
                    contentValues.put(KEY_LOCATION, toDoModel.getLocation());

                    Log.v(LOG_TAG, "inserting values:" + contentValues);

                    result = sqLiteDatabase.insert(TABLE_TO_DO_LIST, null, contentValues);
                    Log.v(LOG_TAG, "result just after insert:" + result);
                } else {
                    Log.e(LOG_TAG, "sqLiteDatabase is null");
                }

            } else {
                Log.e(LOG_TAG, "toDoModel is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
            AppUtil.showToast(mContext, e.getMessage(), true);
        }
        return result;
    }

    public ArrayList<ToDoModel> getAllToDo() {
        Log.v(LOG_TAG, "in getAllToDo()");

        ArrayList<ToDoModel> arrayList_ToDoModel = null;
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

            if (sqLiteDatabase != null) {

                String[] strArr_fieldsToFetch = new String[]{KEY_ID, KEY_TEXT_TO_DO, KEY_DATE,
                        KEY_TIME, KEY_LOCATION};
                Cursor cursor = sqLiteDatabase.query(TABLE_TO_DO_LIST, strArr_fieldsToFetch, null,
                        null, null, null, null);

                if (cursor != null) {
                    if (cursor.moveToFirst()) {

                        arrayList_ToDoModel = new ArrayList<ToDoModel>();

                        while (cursor.moveToNext()) {

                            ToDoModel toDoModel = new ToDoModel();
                            toDoModel.set_id(cursor.getInt(0));
                            toDoModel.setText_to_do(cursor.getString(1));
                            toDoModel.setDate(cursor.getString(2));
                            toDoModel.setTime(cursor.getString(3));
                            toDoModel.setLocation(cursor.getString(4));

                            Log.v(LOG_TAG, "_id:" + toDoModel.get_id() + " Text_to_do:" + toDoModel.getText_to_do());
                            arrayList_ToDoModel.add(toDoModel);
                        }
                        Log.v(LOG_TAG, "after adding all arrayList_ToDoModel.size():" + arrayList_ToDoModel.size());
                    } else {
                        Log.v(LOG_TAG, "cursor.moveToFirst() returns false... no records found");
                    }
                    cursor.close();
                } else {
                    Log.e(LOG_TAG, "cursor is null");
                }
            } else {
                Log.e(LOG_TAG, "sqLiteDatabase is null");
            }

        } catch (Exception e) {
            e.printStackTrace();
            AppUtil.showToast(mContext, e.getMessage(), true);
        }
        return arrayList_ToDoModel;
    }
}