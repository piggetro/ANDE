package com.example.ande.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ande.model.ThoughtRecyclerItem;
import com.example.ande.model.User;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ANDE";

    private static final String TABLE_USER = "user";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_USERNAME = "username";
    private static final String KEY_USER_PASSWORD = "password";

    private static final String TABLE_ANIMAL = "animal";
    private static final String KEY_ANIMAL_ID = "animal_id";
    private static final String KEY_ANIMAL_NAME = "animal_name";
    private static final String KEY_ANIMAL_MAX = "animal_max";

    private static final String TABLE_USER_ANIMAL = "user_animal";
    private static final String KEY_USER_ANIMAL_ID = "user_animal_id";
    private static final String KEY_USER_ANIMAL_USER_ID = "user_id";
    private static final String KEY_USER_ANIMAL_ANIMAL_ID = "animal_id";
    private static final String KEY_USER_ANIMAL_POINT = "animal_point";
    private static final String KEY_USER_ANIMAL_ISACTIVE = "animal_isactive";

    private static final String TABLE_USER_THOUGHTS = "user_thoughts";
    private static final String KEY_USER_THOUGHTS_ID = "user_thoughts_id";
    private static final String KEY_USER_THOUGHTS_USER_ID = "user_id";
    private static final String KEY_USER_THOUGHTS_THOUGHTS = "thoughts";
    private static final String KEY_USER_THOUGHTS_DATE = "date";

    private static final String TABLE_USER_MOOD = "user_mood";
    private static final String KEY_USER_MOOD_ID = "user_mood_id";
    private static final String KEY_USER_MOOD_USER_ID = "user_id";
    private static final String KEY_USER_MOOD_MOOD = "mood";
    private static final String KEY_USER_MOOD_DATE = "date";

    private static final String TABLE_USER_MEDITATION = "user_meditation";
    private static final String KEY_USER_MEDITATION_ID = "user_meditation_id";
    private static final String KEY_USER_MEDITATION_USER_ID = "user_id";
    private static final String KEY_USER_MEDITATION_MINUTES = "user_meditation_minutes";
    private static final String KEY_USER_MEDITATION_DATE = "date";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " +
                TABLE_USER + " (" +
                KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_USER_EMAIL + " TEXT, " +
                KEY_USER_USERNAME + " TEXT, " +
                KEY_USER_PASSWORD + " TEXT" +
                ");";

        String CREATE_TABLE_ANIMAL = "CREATE TABLE IF NOT EXISTS " +
                TABLE_ANIMAL + " (" +
                KEY_ANIMAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_ANIMAL_NAME + " TEXT, " +
                KEY_ANIMAL_MAX + " INTEGER" +
                ");";

        String CREATE_TABLE_USER_ANIMAL = "CREATE TABLE IF NOT EXISTS " +
                TABLE_USER_ANIMAL + " (" +
                KEY_USER_ANIMAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_USER_ANIMAL_USER_ID + " INTEGER, " +
                KEY_USER_ANIMAL_ANIMAL_ID + " INTEGER, " +
                KEY_USER_ANIMAL_POINT + " INTEGER DEFAULT 0, " +
                KEY_USER_ANIMAL_ISACTIVE + " INTEGER DEFAULT 0, " +
                "FOREIGN KEY (" + KEY_USER_ANIMAL_USER_ID +") REFERENCES user(" + KEY_USER_ID + "), " +
                "FOREIGN KEY (" + KEY_USER_ANIMAL_ANIMAL_ID + ") REFERENCES animal(" + KEY_ANIMAL_ID + ")" +
                ");";

        String CREATE_TABLE_USER_THOUGHTS = "CREATE TABLE IF NOT EXISTS " +
                TABLE_USER_THOUGHTS + " (" +
                KEY_USER_THOUGHTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_USER_THOUGHTS_USER_ID + " INTEGER, " +
                KEY_USER_THOUGHTS_THOUGHTS + " TEXT, " +
                KEY_USER_THOUGHTS_DATE + " TEXT DEFAULT (strftime('%m/%d/%Y', 'now', 'localtime')), " +
                "FOREIGN KEY (" + KEY_USER_THOUGHTS_USER_ID + ") REFERENCES user(" + KEY_USER_ID + ")" +
                ");";

        String CREATE_TABLE_USER_MOOD = "CREATE TABLE IF NOT EXISTS " +
                TABLE_USER_MOOD + " (" +
                KEY_USER_MOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_USER_MOOD_USER_ID + " INTEGER, " +
                KEY_USER_MOOD_MOOD + " TEXT, " +
                KEY_USER_MOOD_DATE + " TEXT DEFAULT (strftime('%m/%d/%Y', 'now', 'localtime')), " +
                "FOREIGN KEY (" + KEY_USER_MOOD_USER_ID + ") REFERENCES user(" + KEY_USER_ID + ")" +
                ");";

        String CREATE_TABLE_USER_MEDITATION = "CREATE TABLE " + TABLE_USER_MEDITATION + "(" +
                KEY_USER_MEDITATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_USER_MEDITATION_USER_ID + " INTEGER, " +
                KEY_USER_MEDITATION_MINUTES + " INTEGER, " +
                KEY_USER_MEDITATION_DATE + " TEXT DEFAULT (strftime('%m/%d/%Y', 'now', 'localtime')), " +
                "FOREIGN KEY (" + KEY_USER_MEDITATION_USER_ID + ") REFERENCES your_user_table_name(your_user_id_column_name)" +
                ");";

        String multiRowInsert = "INSERT INTO " + TABLE_ANIMAL + " (" + KEY_ANIMAL_NAME + ", " + KEY_ANIMAL_MAX + ") VALUES " +
                "('Pig', 100), " +
                "('Rabbit', 200), " +
                "('Cow', 300);";

        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(CREATE_TABLE_ANIMAL);
        sqLiteDatabase.execSQL(CREATE_TABLE_USER_ANIMAL);
        sqLiteDatabase.execSQL(CREATE_TABLE_USER_THOUGHTS);
        sqLiteDatabase.execSQL(CREATE_TABLE_USER_MOOD);
        sqLiteDatabase.execSQL(CREATE_TABLE_USER_MEDITATION);

        sqLiteDatabase.execSQL(multiRowInsert);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_USER_EMAIL, user.getEmail());
        cv.put(KEY_USER_USERNAME, user.getUsername());
        cv.put(KEY_USER_PASSWORD, user.getPassword());

        sqLiteDatabase.insert(TABLE_USER, null, cv);

        sqLiteDatabase.close();
    }
    public boolean checkIfUserExists(String email) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_USER_EMAIL + " = '" + email + "';";

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public int loginUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_USER_EMAIL + " = '" + user.getEmail() + "' AND " + KEY_USER_PASSWORD + " = '" + user.getPassword() + "';";

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(KEY_USER_ID);
            int userId = cursor.getInt(columnIndex);

            Log.e("UserIdLog", "User ID: " + userId);

            return userId;

        } else {
            Log.e("UserIdLog", "No matching user found");
            return -1;
        }
    }

    public ArrayList<ThoughtRecyclerItem> getThoughtsByUserIdAndDate(int userId, String date) {
        ArrayList<ThoughtRecyclerItem> thoughtsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {KEY_USER_THOUGHTS_ID, KEY_USER_THOUGHTS_THOUGHTS};  // Include the ID column if needed
        String selection = KEY_USER_THOUGHTS_USER_ID + " = ? AND " + KEY_USER_THOUGHTS_DATE + " = ?";
        String[] selectionArgs = {String.valueOf(userId), date};
        String orderBy = null;

        Cursor cursor = db.query(TABLE_USER_THOUGHTS, columns, selection, selectionArgs, null, null, orderBy);

        if (cursor != null) {
            int idIndex = cursor.getColumnIndex(KEY_USER_THOUGHTS_ID);
            int thoughtsIndex = cursor.getColumnIndex(KEY_USER_THOUGHTS_THOUGHTS);

            while (cursor.moveToNext()) {
                if (idIndex != -1 && thoughtsIndex != -1) {
                    String thoughtId = cursor.getString(idIndex);
                    String thoughtText = cursor.getString(thoughtsIndex);
                    thoughtsList.add(new ThoughtRecyclerItem(thoughtId, thoughtText));
                } else {
                    Log.e("DBHandler", "Column not found: " + KEY_USER_THOUGHTS_ID + " or " + KEY_USER_THOUGHTS_THOUGHTS);
                }
            }
            cursor.close();
        }

        db.close();
        return thoughtsList;
    }


}
