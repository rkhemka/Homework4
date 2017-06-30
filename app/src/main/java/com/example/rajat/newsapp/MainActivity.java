package com.example.rajat.newsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rajat.newsapp.utilites.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ProgressDialog ProgressDialog;

    private RecyclerView RecyclerView;
    private Adapter Adapter;
    private ArrayList articlesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.setLayoutManager(layoutManager);
        RecyclerView.setHasFixedSize(true);
        Adapter = new Adapter(this);
        RecyclerView.setAdapter(Adapter);


        new GetResponseTask().execute(NetworkUtils.makeURL());
    }


    private class GetResponseTask extends AsyncTask<URL, Void, ArrayList<NewsItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialog = new ProgressDialog(MainActivity.this);
            ProgressDialog.setMessage("Loading data...");
            ProgressDialog.setIndeterminate(false);
            ProgressDialog.show();
        }

        @Override
        protected ArrayList<NewsItem> doInBackground(URL... params) {
            String jsonResults;
            ArrayList<NewsItem> results = null;
            try {
                jsonResults = NetworkUtils.getResponseFromHttpUrl(params[0]);
                results = NetworkUtils.parseJSON(jsonResults);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(final ArrayList<NewsItem> data) {

            if(data != null)
                Adapter.setNewsData(data);

            ProgressDialog.dismiss();
        }
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
            new GetResponseTask().execute(NetworkUtils.makeURL());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}