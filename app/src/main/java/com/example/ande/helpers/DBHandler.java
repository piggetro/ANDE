package com.example.ande.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ande.model.Animal;
import com.example.ande.model.CollectionChar;
import com.example.ande.model.Quote;
import com.example.ande.model.Thought;
import com.example.ande.model.User;

import java.util.ArrayList;
import java.util.List;

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
    private static final String KEY_ANIMAL_TYPE = "animal_type";
    private static final String KEY_ANIMAL_MAX = "animal_max";

    private static final String TABLE_USER_ANIMAL = "user_animal";

    private static final String KEY_USER_ANIMAL_ID = "user_animal_id";

    private static final String KEY_USER_ANIMAL_NAME = "user_animal_name";
    private static final String KEY_USER_ANIMAL_USER_ID = "user_id";
    private static final String KEY_USER_ANIMAL_ANIMAL_ID = "animal_id";
    private static final String KEY_USER_ANIMAL_POINT = "animal_point";
    private static final String KEY_USER_ANIMAL_ISACTIVE = "animal_isactive";

    private static final String TABLE_USER_THOUGHTS = "user_thoughts";
    private static final String KEY_USER_THOUGHTS_ID = "user_thoughts_id";
    private static final String KEY_USER_THOUGHTS_USER_ID = "user_id";
    private static final String KEY_USER_THOUGHTS_THOUGHTS = "thoughts";
    private static final String KEY_USER_THOUGHTS_DATE = "date";
    private static final String KEY_USER_THOUGHTS_TIME = "time";

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


    private static final String TABLE_QUOTE= "quote";
    private static final String KEY_QUOTE_ID = "quote_id";
    private static final String KEY_QUOTE_TEXT = "quote_text";
    private static final String KEY_QUOTE_AUTHOR = "quote_author";
    private static final String KEY_QUOTE_CATEGORY = "quote_category";



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
                KEY_ANIMAL_TYPE +" TEXT," +
                KEY_ANIMAL_MAX + " INTEGER DEFAULT 200" +
                ");";

        String CREATE_TABLE_USER_ANIMAL = "CREATE TABLE IF NOT EXISTS " +
                TABLE_USER_ANIMAL + " (" +
                KEY_USER_ANIMAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_USER_ANIMAL_USER_ID + " INTEGER, " +
                KEY_USER_ANIMAL_NAME + " TEXT, " +
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
                KEY_USER_THOUGHTS_TIME + " TEXT DEFAULT (strftime('%H:%M:%S', 'now', 'localtime')), " +
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

        String CREATE_TABLE_QUOTE = "CREATE TABLE " + TABLE_QUOTE + "(" +
                KEY_QUOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_QUOTE_TEXT + " TEXT, " +
                KEY_QUOTE_AUTHOR + " TEXT, " +
                KEY_QUOTE_CATEGORY + " TEXT" +
                ");";

        String multiRowInsert = "INSERT INTO " + TABLE_ANIMAL + " ( " + KEY_ANIMAL_TYPE + ", " + KEY_ANIMAL_MAX + ") VALUES " +
                "('Pig', 100), " +
                "('Rabbit', 200), " +
                "('Chick', 200), " +
                "('Cow', 300);";

        String multiRowInsertHappyQuote = "INSERT INTO " + TABLE_QUOTE + " (" + KEY_QUOTE_TEXT + ", " + KEY_QUOTE_AUTHOR + ", " + KEY_QUOTE_CATEGORY + ") VALUES " +
                "('If you want to be happy, set a goal that commands your thoughts, liberates your energy, and inspires your hopes.', 'Andrew Carnegie', 'happy'), " +
                "('Pleasure can be supported by an illusion; but happiness rests upon truth.', 'Nicolas Chamfort', 'happy'), " +
                "('What have you done today to make someone else happy?', 'Deepam Chaterjee', 'happy'), " +
                "('To be happy we need something to solve. Happiness is therefore a form of action.', 'Mark Manson', 'happy'), " +
                "('Just because you are happy it does not mean that the day is perfect but that you have looked beyond its imperfections.', 'Bob Marley', 'happy'), " +
                "('Happiness comes when you believe in what you are doing, know what you are doing, and love what you are doing.', 'Brian Tracy', 'happy');";

        String multiRowInsertSadQuote = "INSERT INTO " + TABLE_QUOTE + " (" + KEY_QUOTE_TEXT + ", " + KEY_QUOTE_AUTHOR + ", " + KEY_QUOTE_CATEGORY + ") VALUES " +
                "('If you want to be happy, set a goal that commands your thoughts, liberates your energy, and inspires your hopes.', 'Andrew Carnegie', 'sad'), " +
                "('Pleasure can be supported by an illusion; but happiness rests upon truth.', 'Nicolas Chamfort', 'sad'), " +
                "('What have you done today to make someone else happy?', 'Deepam Chaterjee', 'sad'), " +
                "('Men are anxious to improve their circumstances but are unwilling to improve themselves; they therefore remain bound.', 'James Allen', 'sad'), " +
                "('Anxiety is the dizziness of freedom.', 'Soren Kierkegaard', 'sad'), " +
                "('The secret of perfect health lies in keeping the mind always cheerful - never worried, never hurried, never borne down by any fear, thought or anxiety.', 'Sathya Sai Baba', 'sad'), " +
                "('To be in harmony with the wholeness of things is not to have anxiety over imperfections.', 'Dogen', 'sad'), " +
                "('If you are depressed, you are living in the past. If you are anxious, you are living in the future. if you are at peace, you are living in the present.', 'Lao Tzu', 'sad'), " +
                "('Our anxiety does not empty tomorrow of its sorrows, but only empties today of its strengths.', 'Charles Spurgeon', 'sad');";

        String multiRowInsertAngryQuote = "INSERT INTO " + TABLE_QUOTE + " (" + KEY_QUOTE_TEXT + ", " + KEY_QUOTE_AUTHOR + ", " + KEY_QUOTE_CATEGORY + ") VALUES " +
                "('Reversing your treatment of the man you have wronged is better than asking his forgiveness.', 'Elbert Hubbard', 'angry'), " +
                "('Forgive those who have hurt you.', 'Les Brown', 'angry'), " +
                "('The weak can never forgive. Forgiveness is the attribute of the strong.', 'Mahatma Gandhi', 'angry'), " +
                "('Forgive yourself for your faults and your mistakes and move on.', 'Les Brown', 'angry'), " +
                "('It is important that we forgive ourselves for making mistakes. We need to learn from our errors and move on.', 'Steve Maraboli', 'angry'), " +
                "('To forgive means pardoning the unpardonable.', 'Gilbert Chesterton', 'angry'), " +
                "('Loving thoughts and actions are clearly beneficial for our physical and mental health.', 'Dalai Lama', 'angry'), " +
                "('Kindness in words creates confidence. Kindness in thinking creates profoundness. Kindness in giving creates love.', 'Lao Tzu', 'angry'), " +
                "('The greatest gift that you can give to others is the gift of unconditional love and acceptance.', 'Brian Tracy', 'angry');";

        String multiRowInsertNeutralQuote = "INSERT INTO " + TABLE_QUOTE + " (" + KEY_QUOTE_TEXT + ", " + KEY_QUOTE_AUTHOR + ", " + KEY_QUOTE_CATEGORY + ") VALUES " +
                "('Amateurs sit and wait for inspiration, the rest of us just get up and get to work.', 'Stephen King', 'neutral'), " +
                "('Inspiration exists, but it has to find you working.', 'Pablo Picasso', 'neutral'), " +
                "('If you want to be happy, set a goal that commands your thoughts, liberates your energy, and inspires your hopes.', 'Andrew Carnegie', 'neutral');";



        //TODO: ONLY FOR TESTING. Remove this after testing
        String multiRowInsertUser = "INSERT INTO " + TABLE_USER + " (" + KEY_USER_EMAIL + ", " + KEY_USER_USERNAME + ", " + KEY_USER_PASSWORD + ") VALUES " +
                "('t@gmail.com', 't', 't'), " +
                "('t2@gmail.com', 't', 't');";

        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(CREATE_TABLE_ANIMAL);
        sqLiteDatabase.execSQL(CREATE_TABLE_USER_ANIMAL);
        sqLiteDatabase.execSQL(CREATE_TABLE_USER_THOUGHTS);
        sqLiteDatabase.execSQL(CREATE_TABLE_USER_MOOD);
        sqLiteDatabase.execSQL(CREATE_TABLE_USER_MEDITATION);
        sqLiteDatabase.execSQL(CREATE_TABLE_QUOTE);


        sqLiteDatabase.execSQL(multiRowInsert);
        sqLiteDatabase.execSQL(multiRowInsertHappyQuote);
        sqLiteDatabase.execSQL(multiRowInsertSadQuote);
        sqLiteDatabase.execSQL(multiRowInsertAngryQuote);
        sqLiteDatabase.execSQL(multiRowInsertNeutralQuote);

        //TODO: ONLY FOR TESTING. Remove this after testing
        sqLiteDatabase.execSQL(multiRowInsertUser);
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

    public ArrayList<Thought> getThoughtsByUserIdAndDate(int userId, String date) throws SQLException {
        ArrayList<Thought> thoughtsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {KEY_USER_THOUGHTS_ID, KEY_USER_THOUGHTS_THOUGHTS};
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
                    thoughtsList.add(new Thought(thoughtId, thoughtText));
                } else {
                    throw new SQLException("Unable to retrieve thoughts");
                }
            }
            cursor.close();
        } else {
            throw new SQLException("Unable to retrieve thoughts");
        }

        db.close();
        return thoughtsList;
    }

    public ArrayList<Thought> getThoughtsByUserIdAndDateOrderByLatest(int userId, String date) throws SQLException {
        ArrayList<Thought> thoughtsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {KEY_USER_THOUGHTS_ID, KEY_USER_THOUGHTS_THOUGHTS};
        String selection = KEY_USER_THOUGHTS_USER_ID + " = ? AND " + KEY_USER_THOUGHTS_DATE + " = ?";
        String[] selectionArgs = {String.valueOf(userId), date};
        String orderBy = KEY_USER_THOUGHTS_TIME + " DESC";

        Cursor cursor = db.query(TABLE_USER_THOUGHTS, columns, selection, selectionArgs, null, null, orderBy);

        if (cursor != null) {
            int idIndex = cursor.getColumnIndex(KEY_USER_THOUGHTS_ID);
            int thoughtsIndex = cursor.getColumnIndex(KEY_USER_THOUGHTS_THOUGHTS);

            while (cursor.moveToNext()) {
                if (idIndex != -1 && thoughtsIndex != -1) {
                    String thoughtId = cursor.getString(idIndex);
                    String thoughtText = cursor.getString(thoughtsIndex);
                    thoughtsList.add(new Thought(thoughtId, thoughtText));
                } else {
                    throw new SQLException("Unable to retrieve thoughts");
                }
            }
            cursor.close();
        } else {
            throw new SQLException("Unable to retrieve thoughts");
        }

        db.close();
        return thoughtsList;
    }

    public ArrayList<Thought> getThoughtsByUserIdAndDateOrderByEarliest(int userId, String date) throws SQLException {
        ArrayList<Thought> thoughtsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {KEY_USER_THOUGHTS_ID, KEY_USER_THOUGHTS_THOUGHTS};
        String selection = KEY_USER_THOUGHTS_USER_ID + " = ? AND " + KEY_USER_THOUGHTS_DATE + " = ?";
        String[] selectionArgs = {String.valueOf(userId), date};
        String orderBy = KEY_USER_THOUGHTS_TIME + " ASC";

        Cursor cursor = db.query(TABLE_USER_THOUGHTS, columns, selection, selectionArgs, null, null, orderBy);

        if (cursor != null) {
            int idIndex = cursor.getColumnIndex(KEY_USER_THOUGHTS_ID);
            int thoughtsIndex = cursor.getColumnIndex(KEY_USER_THOUGHTS_THOUGHTS);

            while (cursor.moveToNext()) {
                if (idIndex != -1 && thoughtsIndex != -1) {
                    String thoughtId = cursor.getString(idIndex);
                    String thoughtText = cursor.getString(thoughtsIndex);
                    thoughtsList.add(new Thought(thoughtId, thoughtText));
                } else {
                    throw new SQLException("Unable to retrieve thoughts");
                }
            }
            cursor.close();
        } else {
            throw new SQLException("Unable to retrieve thoughts");
        }

        db.close();
        return thoughtsList;
    }

    public void addThought(int userId, String thoughts, String date) throws SQLException {

        try (SQLiteDatabase sqLiteDatabase = this.getWritableDatabase()) {
            ContentValues cv = new ContentValues();
            cv.put(KEY_USER_THOUGHTS_USER_ID, userId);
            cv.put(KEY_USER_THOUGHTS_THOUGHTS, thoughts);
            cv.put(KEY_USER_THOUGHTS_DATE, date);

            sqLiteDatabase.insert(TABLE_USER_THOUGHTS, null, cv);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error adding thought");
        }
    }

    public void updateThought(String thoughtId, String thoughtText) throws SQLException {

        try (SQLiteDatabase sqLiteDatabase = this.getWritableDatabase()) {
            ContentValues cv = new ContentValues();
            cv.put(KEY_USER_THOUGHTS_THOUGHTS, thoughtText);

            String whereClause = KEY_USER_THOUGHTS_ID + " = ?";
            String[] whereArgs = {thoughtId};

            sqLiteDatabase.update(TABLE_USER_THOUGHTS, cv, whereClause, whereArgs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error updating thought");
        }

    }

    public void deleteThought(String thoughtId) throws SQLException {
        try (SQLiteDatabase sqLiteDatabase = this.getWritableDatabase()) {
            String whereClause = KEY_USER_THOUGHTS_ID + " = ?";
            String[] whereArgs = {thoughtId};

            sqLiteDatabase.delete(TABLE_USER_THOUGHTS, whereClause, whereArgs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error deleting thought");
        }
    }

    //add point to user_animal
    public void addPointsToUserAnimal(int userId, int points) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String[] columns = {KEY_USER_ANIMAL_ANIMAL_ID, KEY_USER_ANIMAL_POINT, KEY_USER_ANIMAL_ISACTIVE};
        String selection = KEY_USER_ANIMAL_USER_ID + " = ? AND " + KEY_USER_ANIMAL_ISACTIVE + " = 1";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = sqLiteDatabase.query(TABLE_USER_ANIMAL, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int animalIdColumnIndex = cursor.getColumnIndex(KEY_USER_ANIMAL_ANIMAL_ID);
            int pointsColumnIndex = cursor.getColumnIndex(KEY_USER_ANIMAL_POINT);

            if (animalIdColumnIndex != -1 && pointsColumnIndex != -1) {
                int animalId = cursor.getInt(animalIdColumnIndex);
                int currentPoints = cursor.getInt(pointsColumnIndex);

                int maxPoints = getAnimalMaxPoints(animalId);

                int newTotalPoints = Math.min(currentPoints + points, maxPoints);

                ContentValues cv = new ContentValues();
                cv.put(KEY_USER_ANIMAL_POINT, newTotalPoints);

                String whereClause = KEY_USER_ANIMAL_USER_ID + " = ? AND " + KEY_USER_ANIMAL_ANIMAL_ID + " = ?";
                String[] whereArgs = {String.valueOf(userId), String.valueOf(animalId)};

                sqLiteDatabase.update(TABLE_USER_ANIMAL, cv, whereClause, whereArgs);
            } else {
                Log.e("DBHandler", "Column not found: " + KEY_USER_ANIMAL_ANIMAL_ID + " or " + KEY_USER_ANIMAL_POINT);
            }
        }

        cursor.close();
        sqLiteDatabase.close();
    }

    public void deductPointsFromUserAnimal(int userId, int points){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String[] columns = {KEY_USER_ANIMAL_ANIMAL_ID, KEY_USER_ANIMAL_POINT, KEY_USER_ANIMAL_ISACTIVE};
        String selection = KEY_USER_ANIMAL_USER_ID + " = ? AND " + KEY_USER_ANIMAL_ISACTIVE + " = 1";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = sqLiteDatabase.query(TABLE_USER_ANIMAL, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int animalIdColumnIndex = cursor.getColumnIndex(KEY_USER_ANIMAL_ANIMAL_ID);
            int pointsColumnIndex = cursor.getColumnIndex(KEY_USER_ANIMAL_POINT);

            if (animalIdColumnIndex != -1 && pointsColumnIndex != -1) {
                int animalId = cursor.getInt(animalIdColumnIndex);
                int currentPoints = cursor.getInt(pointsColumnIndex);

                int minPoints = 0;

                int newTotalPoints = Math.max(currentPoints - points, minPoints);

                ContentValues cv = new ContentValues();
                cv.put(KEY_USER_ANIMAL_POINT, newTotalPoints);

                String whereClause = KEY_USER_ANIMAL_USER_ID + " = ? AND " + KEY_USER_ANIMAL_ANIMAL_ID + " = ?";
                String[] whereArgs = {String.valueOf(userId), String.valueOf(animalId)};

                sqLiteDatabase.update(TABLE_USER_ANIMAL, cv, whereClause, whereArgs);
            } else {
                Log.e("DBHandler", "Column not found: " + KEY_USER_ANIMAL_ANIMAL_ID + " or " + KEY_USER_ANIMAL_POINT);
            }
        }

        cursor.close();
        sqLiteDatabase.close();
    }

    public int getAnimalMaxPoints(int animalId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {KEY_ANIMAL_MAX};
        String selection = KEY_ANIMAL_ID + " = ?";
        String[] selectionArgs = {String.valueOf(animalId)};

        Cursor cursor = db.query(TABLE_ANIMAL, columns, selection, selectionArgs, null, null, null);

        int maxPoints = 0;

        if (cursor.moveToFirst()) {
            int maxPointsIndex = cursor.getColumnIndex(KEY_ANIMAL_MAX);
            if (maxPointsIndex != -1) {
                maxPoints = cursor.getInt(maxPointsIndex);
            }
        }

        cursor.close();

        return maxPoints;
    }


    public void addMood(int userId, String mood) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_USER_MOOD_USER_ID, userId);
        cv.put(KEY_USER_MOOD_MOOD, mood);

        sqLiteDatabase.insert(TABLE_USER_MOOD, null, cv);
        addPointsToUserAnimal(userId, 30);

        sqLiteDatabase.close();
    }

    public String getMood(int userId, String date) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Log.e("mood", "getMood: " + date);
        String query = "SELECT * FROM " + TABLE_USER_MOOD + " WHERE " + KEY_USER_MOOD_USER_ID + " = " + userId + " AND " + KEY_USER_MOOD_DATE + " = '" + date + "';";

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(KEY_USER_MOOD_MOOD);
            String mood = cursor.getString(columnIndex);

            return mood;

        } else {
            return "";
        }
    }

    public String getLatestThought(int userId, String date) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // Query to retrieve the latest thought for the specified date
        String query = "SELECT " + KEY_USER_THOUGHTS_THOUGHTS +
                " FROM " + TABLE_USER_THOUGHTS +
                " WHERE " + KEY_USER_THOUGHTS_USER_ID + " = '" + userId + "'" +
                " AND " + KEY_USER_THOUGHTS_DATE + " = '" + date + "'" +
                " ORDER BY " + KEY_USER_THOUGHTS_TIME + " DESC" +
                " LIMIT 1;";

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(KEY_USER_THOUGHTS_THOUGHTS);
            String latestThought = cursor.getString(columnIndex);

            return latestThought;
        } else {
            return "";
        }
    }

    public String getMeditationTime (int userId, String date) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // Query to retrieve the total meditation time for the specified date
        String query = "SELECT SUM(" + KEY_USER_MEDITATION_MINUTES + ") AS totalMeditationTime" +
                " FROM " + TABLE_USER_MEDITATION +
                " WHERE " + KEY_USER_MEDITATION_USER_ID + " = '" + userId + "'" +
                " AND " + KEY_USER_MEDITATION_DATE + " = '" + date + "';";

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("totalMeditationTime");
            int totalMeditationTime = cursor.getInt(columnIndex);



            return Integer.toString(totalMeditationTime);
        } else {

            return "0";
        }
    }

    public void addMeditation(int userId, int minutes) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_USER_MEDITATION_USER_ID, userId);
        cv.put(KEY_USER_MEDITATION_MINUTES, minutes);

        sqLiteDatabase.insert(TABLE_USER_MEDITATION, null, cv);

        int points = 5 * minutes;

        addPointsToUserAnimal(userId, points);

        sqLiteDatabase.close();
    }

    public void addUserAnimal(int userId, int animalId, String animalName) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //Check for existing active animal
        String selection = KEY_USER_ANIMAL_USER_ID + " = ? AND " + KEY_USER_ANIMAL_ISACTIVE + " = 1";
        String[] selectionArgs = { String.valueOf(userId) };
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(KEY_USER_ANIMAL_ISACTIVE, 0);

        // Update existing active animal's isActive to 0
        sqLiteDatabase.update(TABLE_USER_ANIMAL, cvUpdate, selection, selectionArgs);

        // Step 2: Add the new animal
        ContentValues cv = new ContentValues();
        cv.put(KEY_USER_ANIMAL_USER_ID, userId);
        cv.put(KEY_USER_ANIMAL_POINT, 50); // default starting is 50
        cv.put(KEY_USER_ANIMAL_ANIMAL_ID, animalId);
        cv.put(KEY_USER_ANIMAL_NAME, animalName);
        cv.put(KEY_USER_ANIMAL_ISACTIVE, 1); // Set new animal as active

        // Insert the new animal
        sqLiteDatabase.insert(TABLE_USER_ANIMAL, null, cv);

        // Close the database
        sqLiteDatabase.close();
    }

    public List<CollectionChar> getAllUserAnimals(int userId, Context context) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<CollectionChar> characterList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_USER_ANIMAL +
                " INNER JOIN " + TABLE_ANIMAL +
                " ON " + TABLE_USER_ANIMAL + "." + KEY_USER_ANIMAL_ANIMAL_ID +
                " = " + TABLE_ANIMAL + "." + KEY_ANIMAL_ID +
                " WHERE " + KEY_USER_ANIMAL_USER_ID + " = " + userId +
                " AND " + KEY_USER_ANIMAL_ISACTIVE + " = 0;";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        int nameColumnIndex = cursor.getColumnIndex(KEY_USER_ANIMAL_NAME);
        int pointColumnIndex = cursor.getColumnIndex(KEY_USER_ANIMAL_POINT);
        int animalIdColumnIndex = cursor.getColumnIndex(KEY_ANIMAL_ID);
        int animalTypeColumnIndex = cursor.getColumnIndex(KEY_ANIMAL_TYPE);

        if (nameColumnIndex != -1 && pointColumnIndex != -1 && animalIdColumnIndex != -1 && animalTypeColumnIndex != -1) {
            while (cursor.moveToNext()) {
                String animalName = cursor.getString(nameColumnIndex);
                int animalPoints = cursor.getInt(pointColumnIndex);
                String animalType = cursor.getString(animalTypeColumnIndex);
                int animalId = cursor.getInt(animalIdColumnIndex);

                String petTypeDrawable = getPetTypeDrawable(animalId, animalPoints, animalType);
                int imageId = context.getResources().getIdentifier(petTypeDrawable, "drawable", context.getPackageName());

                CollectionChar character = new CollectionChar(animalName, imageId);
                characterList.add(character);

                Log.e("AnimalNameLog", "Animal Name: " + animalName);
            }
        } else {
            Log.e("AnimalNameLog", "Invalid column index");
        }

        cursor.close();
        return characterList;
    }

    public String getPetTypeDrawable (int animalId, int animalPoints, String animalType) {
        String emotion;
        String petTypeDrawable;

        if (animalPoints == -1) {
            emotion = "neutral";
        }else if (animalPoints < 20) {
            emotion = "verysad";
        } else if (animalPoints < 50) {
            emotion = "sad";
        } else if (animalPoints > (70/100.0*getAnimalMaxPoints(animalId))) { // Use floating point division
            emotion = "happy";
        } else {
            emotion = "neutral";
        }
        petTypeDrawable = "pet_" + emotion + "_" + animalType.toLowerCase();

        return petTypeDrawable;
    }

    public CollectionChar getActiveUserAnimal(int userId, Context context) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        CollectionChar character = null;

        String query = "SELECT * FROM " + TABLE_USER_ANIMAL +
                " INNER JOIN " + TABLE_ANIMAL +
                " ON " + TABLE_USER_ANIMAL + "." + KEY_USER_ANIMAL_ANIMAL_ID +
                " = " + TABLE_ANIMAL + "." + KEY_ANIMAL_ID +
                " WHERE " + KEY_USER_ANIMAL_USER_ID + " = " + userId +
                " AND " + KEY_USER_ANIMAL_ISACTIVE + " = 1;";

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            Log.e("FetchAnimal", "Animal Found");

            int nameColumnIndex = cursor.getColumnIndex(KEY_USER_ANIMAL_NAME);
            int pointColumnIndex = cursor.getColumnIndex(KEY_USER_ANIMAL_POINT);
            int animalIdColumnIndex = cursor.getColumnIndex(KEY_ANIMAL_ID);
            int animalTypeColumnIndex = cursor.getColumnIndex(KEY_ANIMAL_TYPE);

            if (nameColumnIndex != -1 && pointColumnIndex != -1 && animalIdColumnIndex != -1 && animalTypeColumnIndex != -1) {
                String animalName = cursor.getString(nameColumnIndex);
                int animalPoints = cursor.getInt(pointColumnIndex);
                String animalType = cursor.getString(animalTypeColumnIndex);
                int animalId = cursor.getInt(animalIdColumnIndex);

                // Determine the emotional state based on points
                String petTypeDrawable = getPetTypeDrawable(animalId, animalPoints, animalType);
                int imageId = context.getResources().getIdentifier(petTypeDrawable, "drawable", context.getPackageName());

                character = new CollectionChar(animalId, animalName, imageId, animalPoints);
            } else {
                Log.e("AnimalNameLog", "Invalid column index");
            }
        } else {
            Log.e("AnimalNameLog", "No active animal found for user");
            int placeholderImageId = context.getResources().getIdentifier("pet_placeholder", "drawable", context.getPackageName());
            character = new CollectionChar(-1, "No Animal", placeholderImageId, 0);
        }

        if (cursor != null) {
            cursor.close();
        }
        return character; // Returns the CollectionChar object with either animal data or placeholder
    }


public List<Animal> getAllAnimalTypes(Context context) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<Animal> animalList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_ANIMAL + ";";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        int animalIdColumnIndex = cursor.getColumnIndex(KEY_ANIMAL_ID);
        int animalTypeColumnIndex = cursor.getColumnIndex(KEY_ANIMAL_TYPE);
        int animalMaxColumnIndex = cursor.getColumnIndex(KEY_ANIMAL_MAX);

        if (animalIdColumnIndex != -1 && animalTypeColumnIndex != -1 && animalMaxColumnIndex != -1) {
            while (cursor.moveToNext()) {
                int animalId = cursor.getInt(animalIdColumnIndex);
                String animalType = cursor.getString(animalTypeColumnIndex);
                int animalMax = cursor.getInt(animalMaxColumnIndex);


                String petTypeDrawable = getPetTypeDrawable(animalId, -1, animalType);
                int imageId = context.getResources().getIdentifier(petTypeDrawable, "drawable", context.getPackageName());
                Animal animal = new Animal(animalId, animalType, animalMax, imageId);
                animalList.add(animal);
                Log.e("AnimalNameLog", "Animal Name: " + animalType);
            }
        } else {
            Log.e("AnimalNameLog", "Invalid column index");
        }

        cursor.close();
        return animalList;

    }

    //you only have one animal that is active at a time
    public void updateAnimalName (int userId, String newName) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();


        ContentValues cv = new ContentValues();
        cv.put(KEY_USER_ANIMAL_NAME, newName);

        String whereClause = KEY_USER_ANIMAL_USER_ID + " = ? AND " + KEY_USER_ANIMAL_ISACTIVE + " = 1";
        String[] whereArgs = {String.valueOf(userId)};

        sqLiteDatabase.update(TABLE_USER_ANIMAL, cv, whereClause, whereArgs);

        sqLiteDatabase.close();
    }

    // Quote Table
    public ArrayList<Quote> getQuotesByCategory(String category) {
        ArrayList<Quote> quotesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();


        String[] columns = {KEY_QUOTE_ID, KEY_QUOTE_TEXT, KEY_QUOTE_AUTHOR};
        String selection = KEY_QUOTE_CATEGORY + " = ?";
        String[] selectionArgs = {category.toLowerCase()};
        String orderBy = null;

        Cursor cursor = category.equals("All") ? db.query(TABLE_QUOTE, columns, null, null, null, null, null) : db.query(TABLE_QUOTE, columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            int idIndex = cursor.getColumnIndex(KEY_QUOTE_ID);
            int quoteTextIndex = cursor.getColumnIndex(KEY_QUOTE_TEXT);
            int quoteAuthorIndex = cursor.getColumnIndex(KEY_QUOTE_AUTHOR);

            while (cursor.moveToNext()) {
                if (idIndex != -1 && quoteTextIndex != -1 && quoteAuthorIndex != -1) {
                    String quoteText = cursor.getString(quoteTextIndex);
                    String quoteAuthor = cursor.getString(quoteAuthorIndex);
                    quotesList.add(new Quote(quoteText, quoteAuthor));
                } else {
                    Log.e("DBHandler", "Column not found: " + KEY_QUOTE_ID + " or " + KEY_QUOTE_TEXT + " or " + KEY_QUOTE_AUTHOR);
                }
            }
            cursor.close();
        }

        db.close();
        return quotesList;
    }




}

