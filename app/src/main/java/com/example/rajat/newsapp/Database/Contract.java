    package com.example.rajat.newsapp.Database;

    import android.provider.BaseColumns;

    /**
     * Created by rajat on 7/28/2017.
     */

    public class Contract {

        public class TABLE_NewsItem implements BaseColumns {

            // creating database and column with names
            public static final String TABLE_NAME = "newsItems1";

            public static final String COLUMN_NAME_TITLE = "title";
            public static final String COLUMN_NAME_DESCRIPTION = "description";
            public static final String COLUMN_NAME_URL_TO_IMAGE = "urlToImage";
            public static final String COLUMN_NAME_URL = "url";
            public static final String COLUMN_NAME_AUTHOR = "author";
            public static final String COLUMN_NAME_PUBLISHED_At = "publishedAt";


        }



    }
