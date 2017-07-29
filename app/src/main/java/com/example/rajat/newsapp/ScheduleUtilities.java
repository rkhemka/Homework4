    package com.example.rajat.newsapp;

    /**
     * Created by rajat on 7/28/2017.
     */
    import android.content.Context;
    import android.support.annotation.NonNull;

    import com.firebase.jobdispatcher.Constraint;
    import com.firebase.jobdispatcher.Driver;
    import com.firebase.jobdispatcher.FirebaseJobDispatcher;
    import com.firebase.jobdispatcher.GooglePlayDriver;
    import com.firebase.jobdispatcher.Job;
    import com.firebase.jobdispatcher.Lifetime;
    import com.firebase.jobdispatcher.Trigger;

    import java.util.concurrent.TimeUnit;

    public class ScheduleUtilities {

        private static final int SCHEDULE_INTERVAL_MINUTES = 60;
        private static final int SYNC_FLEXTIME_SECONDS = 60;
        private static final String NEWS_JOB_TAG = "news_job_tag";

        private static boolean sInitialized;

        synchronized public static void scheduleRefresh(@NonNull final Context context){
            if(sInitialized) return;

            Driver driver = new GooglePlayDriver(context);
            FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

            Job constraintRefreshJob = dispatcher.newJobBuilder()
                    .setService(NewsJob.class)
                    .setTag(NEWS_JOB_TAG)
                    .setConstraints(Constraint.ON_ANY_NETWORK)
                    .setLifetime(Lifetime.FOREVER)
                    .setRecurring(true)
                    // Loading new news information every minute
                    .setTrigger(Trigger.executionWindow(SCHEDULE_INTERVAL_MINUTES,
                            0+SCHEDULE_INTERVAL_MINUTES ))
                    .setReplaceCurrent(true)
                    .build();

            dispatcher.schedule(constraintRefreshJob);
            sInitialized = true;

        }
    }
