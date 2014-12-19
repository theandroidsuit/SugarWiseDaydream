package com.theandroidsuit.sugarwisedaydream;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

/**
 *
 * THE ANDROID SUIT 2014
 * @author Virginia Hernandez
 * @version 1.0
 *
 */
public class SWDDreamReceiver extends BroadcastReceiver{

    private Context ctx;

    public SWDDreamReceiver(){
        ctx = null;
    }

    public SWDDreamReceiver(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        if (action.equals(Intent.ACTION_TIME_CHANGED) ||
                action.equals(Intent.ACTION_TIMEZONE_CHANGED)||
                action.equals(Intent.ACTION_TIME_TICK) ||
                action.equals(Intent.ACTION_DATE_CHANGED)) {

            SWDaydream.setTimeLayout(ctx);

        } else if(action.equals(Intent.ACTION_BATTERY_CHANGED)){

            int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);


            SWDaydream.setBatteryCharge(ctx, currentLevel, scale, plugged);

        }
    }

}
