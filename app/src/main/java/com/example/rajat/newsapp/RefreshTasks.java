    package com.example.rajat.newsapp;

    import android.content.Context;
    import android.database.sqlite.SQLiteDatabase;

    import com.example.rajat.newsapp.Database.DBHelper;
    import com.example.rajat.newsapp.Database.DatabaseUtils;
    import com.example.rajat.newsapp.Database.NewsItem;
    import com.example.rajat.newsapp.utilites.NetworkUtils;

    import org.json.JSONException;

    import java.io.IOException;
    import java.net.URL;
    import java.util.ArrayList;

    /**
     * Created by rajat on 7/28/2017.
     */

    public class RefreshTasks {


        public static final String ACTION_REFRESH = "refresh";


        public static void refreshArticles(Context context) {
            ArrayList<NewsItem> result = null;
            URL url = NetworkUtils.makeURL();

            SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

            try {
                DatabaseUtils.deleteAll(db);
                String json = NetworkUtils.getResponseFromHttpUrl(url);
                result = NetworkUtils.parseJSON(json);
                // insert into database
                DatabaseUtils.bulkInsert(db, result);

            } catch (IOException e) {
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            db.close();
        }


    }
