package com.space.testspeed;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by licht on 2019/8/21.
 */

public class DPUtil {
    public static int dp2px(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (dp*displayMetrics.density+0.5);
    }

    public static int px2dp(Context context,int px){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (px/displayMetrics.density+0.5);
    }
}
