package pratik.mynavapp.Util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Pratik on 2/20/2016.
 */
public class Connectivity {
    static Context ctx;

    public Connectivity(Context ctx) {
        this.ctx = ctx;
    }

    //Check Internet status
    static public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) ctx.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

}
