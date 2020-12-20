package no3ratii.mohammad.dev.app.notebook.base;

import android.app.Application;
import android.content.Context;

public class G extends Application {

    //set min and max number picker
    public final static int MAX_HOUR_TIME = 23;
    public final static int MAX_MINUTES_TIME = 59;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }



}
