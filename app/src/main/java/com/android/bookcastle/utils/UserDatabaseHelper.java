package com.android.bookcastle.utils;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.bookcastle.models.Book;
import com.android.bookcastle.models.User;

import java.util.ArrayList;

public class UserDatabaseHelper extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "BookCastleDB";
    private static final int DATABASE_VERSION = 7;

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_SAVED_BOOKS = "saved_books";

    // User Table Columns
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_NAME = "username";
    private static final String KEY_USER_PASSWORD = "password";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_GENDER = "gender";
    private static final String KEY_USER_DAILY_READING_GOAL = "daily_reading_goal";

    // Saved Books Table Columns
    private static final String KEY_BOOK_ID = "id";
    private static final String KEY_BOOK_TITLE = "title";
    private static final String KEY_BOOK_AUTHOR = "author";
    private static final String KEY_BOOK_IMAGE = "image";
    private static final String KEY_BOOK_DESCRIPTION = "description";
    private static final String KEY_BOOK_LANGUAGE = "language";
    private static final String KEY_BOOK_PAGES = "pages";
    private static final String KEY_BOOK_DOWNLOAD_COUNT = "download_count";
    private static final String KEY_BOOK_RATING = "rating";



    private static UserDatabaseHelper sInstance;

    public static synchronized UserDatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new UserDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }


    private UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                KEY_USER_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                   KEY_USER_NAME + " TEXT," +
                     KEY_USER_PASSWORD + " TEXT," +
                    KEY_USER_EMAIL + " TEXT," +
                KEY_USER_GENDER + " TEXT," +
                KEY_USER_DAILY_READING_GOAL + " INTEGER" +
                ")";

        String CREATE_SAVED_BOOKS_TABLE = "CREATE TABLE " + TABLE_SAVED_BOOKS +
                "(" +
                KEY_BOOK_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_BOOK_TITLE + " TEXT, " + // Define title
                KEY_BOOK_AUTHOR + " TEXT, " + // Define author
                KEY_BOOK_IMAGE + " TEXT, " + // Define image
                KEY_BOOK_DESCRIPTION + " TEXT, " + // Define description
                KEY_BOOK_LANGUAGE + " TEXT, " + // Define language
                KEY_BOOK_PAGES + " INTEGER, " + // Define pages
                KEY_BOOK_DOWNLOAD_COUNT + " INTEGER, " + // Define download count
                KEY_BOOK_RATING + " INTEGER" + // Define rating
                ")";
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_SAVED_BOOKS_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS + ";");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVED_BOOKS + ";");
            onCreate(db);
        }
    }

    // Insert a user into the database
    public Boolean addUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            // The user might already exist in the database (i.e. the same user created multiple posts).
            ContentValues values = new ContentValues();
            values.put(KEY_USER_NAME, user.getUsername());
            values.put(KEY_USER_PASSWORD, user.getPassword());
            values.put(KEY_USER_EMAIL, user.getEmail());
            values.put(KEY_USER_GENDER, user.getGender());

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_USERS, null, values);
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add user to database");
            return false;
        } finally {
            db.endTransaction();
        }
    }


    // Insert or update a user in the database
    // Since SQLite doesn't support "upsert" we need to fall back on an attempt to UPDATE (in case the
    // user already exists) optionally followed by an INSERT (in case the user does not already exist).

    public long addOrUpdateUser(User user) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_USER_NAME, user.getUsername());

            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            int rows = db.update(TABLE_USERS, values, KEY_USER_NAME + "= ?", new String[]{user.getUsername()});

            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the user we just updated
                String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                        KEY_USER_ID, TABLE_USERS, KEY_USER_NAME);
                Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(user.getUsername())});
                try {
                    if (cursor.moveToFirst()) {
                        userId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                // user with this userName did not already exist, so insert new user
                userId = db.insertOrThrow(TABLE_USERS, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return userId;
    }
    public Boolean checkUsername(String username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_USER_NAME + " = ?", new String[]{username});
        if(cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

    public Boolean checkPassword(String username,String password){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_USER_NAME + " = ? AND " + KEY_USER_PASSWORD + " = ?", new String[]{username,password});
        if(cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }
    //Add a book to the database
    public Boolean addBook(Book book) {
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            // The user might already exist in the database (i.e. the same user created multiple posts).
            ContentValues values = new ContentValues();
            values.put(KEY_BOOK_ID, book.getId());
            values.put(KEY_BOOK_TITLE, book.getTitle());
            values.put(KEY_BOOK_AUTHOR, book.getAuthor());
            values.put(KEY_BOOK_IMAGE, book.getImage());
            values.put(KEY_BOOK_DESCRIPTION, book.getDescription());
            values.put(KEY_BOOK_LANGUAGE, book.getLanguage());
            values.put(KEY_BOOK_PAGES, book.getPages());
            values.put(KEY_BOOK_DOWNLOAD_COUNT, book.getDownload_count());
            values.put(KEY_BOOK_RATING, book.getRating());

            db.insertOrThrow(TABLE_SAVED_BOOKS, null, values);
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add book to database");
            return false;
        } finally {
            db.endTransaction();
        }
    }

    public boolean removeBook(String bookId) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(TABLE_SAVED_BOOKS, KEY_BOOK_ID + " = ?", new String[]{bookId});
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete book");
            return false;
        } finally {
            db.endTransaction();
        }
    }
    @SuppressLint("Range")
    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();

        String BOOKS_SELECT_QUERY =
                String.format("SELECT * FROM %s",
                        TABLE_SAVED_BOOKS);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(BOOKS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Book newBook = new Book();
                    newBook.setId(cursor.getString(cursor.getColumnIndex(KEY_BOOK_ID)));
                    newBook.setTitle(cursor.getString(cursor.getColumnIndex(KEY_BOOK_TITLE)));
                    newBook.setAuthor(cursor.getString(cursor.getColumnIndex(KEY_BOOK_AUTHOR)));
                    newBook.setImage(cursor.getString(cursor.getColumnIndex(KEY_BOOK_IMAGE)));
                    newBook.setDescription(cursor.getString(cursor.getColumnIndex(KEY_BOOK_DESCRIPTION)));
                    newBook.setLanguage(cursor.getString(cursor.getColumnIndex(KEY_BOOK_LANGUAGE)));
                    newBook.setPages(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_BOOK_PAGES))));
                    newBook.setDownload_count(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_BOOK_DOWNLOAD_COUNT))));
                    newBook.setRating(Double.parseDouble(cursor.getString(cursor.getColumnIndex(KEY_BOOK_RATING))));
                    books.add(newBook);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get books from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return books;
    }

    public void deleteBookmark(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_SAVED_BOOKS, KEY_BOOK_ID + " = ?", new String[]{id});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete bookmark");
        } finally {
            db.endTransaction();
        }
    }

    public boolean isFav(String id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SAVED_BOOKS + " WHERE " + KEY_BOOK_ID + " = ?", new String[]{id});
        if(cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }


    @SuppressLint("Range")
    public String getGender(String id) {
        SQLiteDatabase db = getReadableDatabase();
        //select only the gender column
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_USER_NAME + " = ?", new String[]{id});
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex(KEY_USER_GENDER));
        }else{
            return null;
        }
    }

    @SuppressLint("Range")
    public String getUserId(String username, String password) {
           SQLiteDatabase db = getReadableDatabase();
              Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_USER_NAME + " = ? AND " + KEY_USER_PASSWORD + " = ?", new String[]{username,password});
                if(cursor.getCount() > 0){
                    cursor.moveToFirst();
                    return cursor.getString(cursor.getColumnIndex(KEY_USER_ID));
                }else{
                    return null;
                }
    }

    @SuppressLint("Range")
    public User getUserById(String id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_USER_ID + " = ?", new String[]{id});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            User user = new User();
            user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_USER_ID))));
            user.setUsername(cursor.getString(cursor.getColumnIndex(KEY_USER_NAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(KEY_USER_EMAIL)));
            user.setGender(cursor.getString(cursor.getColumnIndex(KEY_USER_GENDER)));
            //verify if the user has a daily goal else set it to 0
            if(cursor.getString(cursor.getColumnIndex(KEY_USER_DAILY_READING_GOAL)) != null){
                user.setDailyReadingGoal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_USER_DAILY_READING_GOAL))));
            }else{
                user.setDailyReadingGoal(0);
            }
            return user;
        }
        return null;
    }


    public Boolean checkPass(String id, String oldPassword) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_USER_ID + " = ? AND " + KEY_USER_PASSWORD + " = ?", new String[]{id,oldPassword});
        if(cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

    public Boolean updatePass(String id, String newPassword) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USER_PASSWORD, newPassword);
        db.update(TABLE_USERS, contentValues, KEY_USER_ID + " = ?", new String[]{id});
        return true;
    }

    public void updateReadingGoal(long timeSpend) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_USER_DAILY_READING_GOAL, timeSpend);
            db.update(TABLE_USERS, values, KEY_USER_ID + " = ?", new String[]{String.valueOf(1)});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to update reading goal");
        } finally {
            db.endTransaction();
        }
    }

    public void updateUser(int id, String username, String email) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_USER_NAME, username);
            values.put(KEY_USER_EMAIL, email);
            db.update(TABLE_USERS, values, KEY_USER_ID + " = ?", new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to update user");
        } finally {
            db.endTransaction();
        }
    }
}