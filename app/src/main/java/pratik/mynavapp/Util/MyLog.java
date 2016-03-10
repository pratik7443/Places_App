package pratik.mynavapp.Util;

import android.util.Log;

/**
 * Created by xPointers on 2/20/2016.
 */
public class MyLog {

    static String Tag;
    static boolean showLog = true;

    public MyLog(String tag) {
        Tag = tag;
    }

    public static void m(String Msg) {

        if (showLog) {
            Log.d(Tag, Msg);
        } else {

        }
    }
}
