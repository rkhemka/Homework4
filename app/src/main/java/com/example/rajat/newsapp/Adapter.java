package com.example.rajat.newsapp;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by rajat on 6/29/2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.AdapterViewHolder> {
    private ArrayList<NewsItem> mNewsData;
    private NewsAdapterOnClickHandler mClickHandler=null;
    Context ct=null;

    Adapter(Context c){
        ct=c;
    }

    public interface NewsAdapterOnClickHandler {
        void onClick(String url);
    }

    public Adapter(NewsAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {


        public final TextView title;
        public final TextView description;
        public final TextView time;

        public AdapterViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            time = (TextView) view.findViewById(R.id.time);
        }


    }


    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.news_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder adapterViewHolder, final int position) {
        adapterViewHolder.title.setText(mNewsData.get(position).getTitle());
        adapterViewHolder.time.setText(mNewsData.get(position).getAuthor() + "    " + mNewsData.get(position).getPublished());
        adapterViewHolder.description.setText(mNewsData.get(position).getDescription());
        adapterViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Intent.ACTION_VIEW);
                Log.d("hello",mNewsData.get(position).getImageUrl()+"jjiijiji");
                in.setData(Uri.parse(mNewsData.get(position).getImageUrl()));
                ct.startActivity(in);
            }
        });

    }


    @Override
    public int getItemCount() {
        if (null == mNewsData) return 0;
        return mNewsData.size();
    }

    public void setNewsData(ArrayList<NewsItem> newsData) {
        mNewsData = newsData;
        notifyDataSetChanged();
    }

}

