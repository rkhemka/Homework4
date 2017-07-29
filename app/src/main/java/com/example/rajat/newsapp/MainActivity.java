    package com.example.rajat.newsapp;

    //import android.app.LoaderManager;

    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.net.Uri;
    import android.os.Bundle;
    import android.preference.PreferenceManager;
    import android.support.v4.app.LoaderManager;
    import android.support.v4.content.AsyncTaskLoader;
    import android.support.v4.content.Loader;
    import android.support.v7.app.AppCompatActivity;
    import android.support.v7.widget.LinearLayoutManager;
    import android.support.v7.widget.RecyclerView;
    import android.util.Log;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.ProgressBar;


    import com.example.rajat.newsapp.Database.Contract;
    import com.example.rajat.newsapp.Database.DBHelper;
    import com.example.rajat.newsapp.Database.DatabaseUtils;

    import java.util.ArrayList;

    // Replacing Asyn Task with Asyn Task Loader by implementing LoadManager
    public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>, Adapter.NewsAdapterOnClickHandler {
        private ProgressBar progressDialog;

        // Initializing Loader
        private static final int loaderID = 1;

        static final String TAG = "mainactivity";
        // Creating object for cursor and SQLiteDatabase
        private Cursor cursor;
        private SQLiteDatabase db;

        private RecyclerView RecyclerView;
        private Adapter Adapter;
        // Removed Arraylist Object as we are storing all values in database
        //    private ArrayList articlesList = new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            progressDialog = (ProgressBar) findViewById(R.id.progressBar);

            RecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            RecyclerView.setLayoutManager(layoutManager);

            //RecyclerView.setHasFixedSize(true);


            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            boolean isFirst = prefs.getBoolean("isfirst", true);
            // onCreate, have your activity load what's currently in your database into the recyclerview for display
            if (isFirst) {
                load();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isfirst", false);
                editor.commit();
            }
            ScheduleUtilities.scheduleRefresh(this);


        }

        public void load() {
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.restartLoader(loaderID, null, this).forceLoad();

        }

        @Override
        protected void onStart() {
            super.onStart();
            db = new DBHelper(MainActivity.this).getReadableDatabase();
            cursor = DatabaseUtils.getAll(db);
            Adapter = new Adapter(cursor, this);
            RecyclerView.setAdapter(Adapter);
        }

        @Override
        protected void onStop() {
            super.onStop();
            db.close();
            cursor.close();
        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            int id = item.getItemId();
            if (id == R.id.search) {
                load();
            }

            return true;
        }

        // Implementing Loader
        @Override
        public Loader<Void> onCreateLoader(int id, final Bundle args) {
            return new AsyncTaskLoader<Void>(this) {


                @Override
                protected void onStartLoading() {
                    super.onStartLoading();
                    progressDialog.setVisibility(View.VISIBLE);


                }

                @Override
                public Void loadInBackground() {
                    RefreshTasks.refreshArticles(MainActivity.this);
                    return null;
                }

            };

        }

        @Override
        public void onLoadFinished(android.support.v4.content.Loader<Void> loader, Void data) {

            progressDialog.setVisibility(View.GONE);
            db = new DBHelper(MainActivity.this).getReadableDatabase();
            cursor = DatabaseUtils.getAll(db);
            Adapter = new Adapter(cursor, this);
            RecyclerView.setAdapter(Adapter);
            Adapter.notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(android.support.v4.content.Loader<Void> loader) {

        }

        // Onclick method responding to users clicks
        @Override
        public void onItemClick(Cursor cursor, int clickedItemIndex) {
            cursor.moveToPosition(clickedItemIndex);
            String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_NewsItem.COLUMN_NAME_URL));
            Log.d(TAG, String.format("Url %s", url));

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }