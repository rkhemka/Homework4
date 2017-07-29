    package com.example.rajat.newsapp.Database;

    import android.content.ContentValues;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;

    import java.util.ArrayList;

    import static com.example.rajat.newsapp.Database.Contract.TABLE_NewsItem.*;
    /**
     * Created by rajat on 7/28/2017.
     */

    public class DatabaseUtils {
        public static Cursor getAll(SQLiteDatabase db) {
            Cursor cursor = db.query(
                    TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    COLUMN_NAME_PUBLISHED_At + " DESC"
            );
            return cursor;

        }






        public static void bulkInsert(SQLiteDatabase db, ArrayList<NewsItem> newsList) {

            // Context Value inserting values in database
            db.beginTransaction();
            try {
                for (NewsItem a : newsList) {
                    ContentValues cv = new ContentValues();
                    cv.put(COLUMN_NAME_TITLE, a.getTitle());
                    cv.put(COLUMN_NAME_DESCRIPTION, a.getDescription());
                    cv.put(COLUMN_NAME_URL_TO_IMAGE, a.getImageUrl());
                    cv.put(COLUMN_NAME_URL,a.getUrl());
                    cv.put(COLUMN_NAME_AUTHOR,a.getAuthor());
                    cv.put(COLUMN_NAME_PUBLISHED_At,a.getPublished());

                    db.insert(TABLE_NAME, null, cv);
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
                db.close();
            }
        }

        public static void deleteAll(SQLiteDatabase db) {
            db.delete(TABLE_NAME, null, null);
        }

    }
