package com.theandroidsuit.sugarwisedaydream;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.service.dreams.DreamService;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.theandroidsuit.sugarwisedaydream.bean.Wisdom;
import com.theandroidsuit.sugarwisedaydream.util.SugarWiseUtils;

import java.util.Calendar;

/**
 *
 * THE ANDROID SUIT 2014
 * @author Virginia Hernandez
 * @version 1.0
 *
 */

public class SWDaydream extends DreamService implements OnClickListener{

    private static final String TAG = "SWDaydream";

    private static IntentFilter s_intentFilter;
    private final BroadcastReceiver m_timeChangedReceiver = new SWDDreamReceiver(this);
    private static RelativeLayout layout;

    private static String theme = "fortune";
	private static int color = Color.parseColor("#FFFFFF");
	private static int size = 10;
    private static String nightMode = "false";
    private static boolean is24HoursFormat = false;

    private static long initTime = 0;

    private static SugarWiseUtils utils;


    @Override
	public void onClick(View v) {

        Log.d(TAG, "onClick");

        restoreBrightnessInDevice();
		//stop button
		this.finish();
	}
	
	@Override
	public void onAttachedToWindow() {

        Log.d(TAG, "onAttachedToWindow");

		super.onAttachedToWindow();
		
		// Set the Daydream to be interactive and occupy the full screen, hiding the status bar.
		setInteractive(true);
		setFullscreen(true);

        setIntentAndReceiver();
		getConfiguration(this);

        utils = new SugarWiseUtils(this, theme);

        if("true".equals(nightMode)){
            turnDownBrightness();
        }

        // Our content view will take care of animating its children.
		setContentView(R.layout.activity_main);

        layout = (RelativeLayout) findViewById(R.id.layoutDream);

        setNewSWLayout(this);
        setTimeLayout(this);

        layout.setOnClickListener(this);
        
	}


    private void setIntentAndReceiver() {
        // LOG
        Log.d(TAG, "setIntentAndReceiver");

        s_intentFilter = new IntentFilter();
        s_intentFilter.addAction(Intent.ACTION_TIME_TICK); 			// Minute change
        s_intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);	// Timezone change
        s_intentFilter.addAction(Intent.ACTION_TIME_CHANGED);		// Time set
        s_intentFilter.addAction(Intent.ACTION_DATE_CHANGED);		// Date change
        s_intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED); 	// Battery Level change
        s_intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);	// Plugged
        s_intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);	// Unplugged

        registerReceiver(m_timeChangedReceiver, s_intentFilter);
    }

    public static void setNewSWLayout(Context ctx) {

        Log.d(TAG, "setSWLayout");

        TextView tvSentence = (TextView) layout.findViewById(R.id.sentence);
        TextView tvAuthor = (TextView) layout.findViewById(R.id.author);

        Wisdom wisdom = utils.getCurrentWisdomCrystal();

        tvSentence.setText(wisdom.getSentence());
        tvSentence.setTextColor(color);
        tvSentence.setTextSize(size);

        String author = wisdom.getAuthor();
        if (!"".equals(author))
            author = "-- " + author;

        tvAuthor.setText(author);
        tvAuthor.setTextColor(color);
        tvAuthor.setTextSize(size - 5);

    }


    public static void setNextSWLayout(Context ctx) {

        Log.d(TAG, "setSWLayout");

        TextView tvSentence = (TextView) layout.findViewById(R.id.sentence);
        TextView tvAuthor = (TextView) layout.findViewById(R.id.author);

        Wisdom wisdom = utils.getNextWisdomCrystal();

        tvSentence.setText(wisdom.getSentence());
        tvSentence.setTextColor(color);
        tvSentence.setTextSize(size);

        String author = wisdom.getAuthor();
        if (!"".equals(author))
            author = "-- " + author;

        tvAuthor.setText(author);
        tvAuthor.setTextColor(color);
        tvAuthor.setTextSize(size - 5);

    }

    public static void setTimeLayout(Context ctx){
        // LOG
        Log.d(TAG, "setTimeLayout");


        TextView horaV = (TextView) layout.findViewById(R.id.horaView);
        TextView minutoV = (TextView) layout.findViewById(R.id.minutoView);
        TextView timeFormatV = (TextView) layout.findViewById(R.id.timeFormatView);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        if (initTime == 0) // First Time
            initTime = calendar.getTimeInMillis();

        int hora = 0;
        if (is24HoursFormat) {
            hora = calendar.get(Calendar.HOUR_OF_DAY);
            timeFormatV.setText("");
        }else{
            hora = calendar.get(Calendar.HOUR);
            if (Calendar.AM == calendar.get(Calendar.AM_PM))
                timeFormatV.setText(ctx.getString(R.string.am));
            else
                timeFormatV.setText(ctx.getString(R.string.pm));
        }

        int minuto = calendar.get(Calendar.MINUTE);

        horaV.setText(String.valueOf(hora));
        if (minuto < 10)
            minutoV.setText("0" + minuto);
        else
            minutoV.setText(String.valueOf(minuto));

        setNextSWLayout(ctx);

    }


    public static void setBatteryCharge(Context ctx, int currentLevel, int scale, int plugged) {

        Log.d(TAG, "setBatteryCharge");

        int level = -1;
        if (currentLevel >= 0 && scale > 0) {
            level = (currentLevel * 100) / scale;
        }

        TextView percentSymbolV = (TextView) layout.findViewById(R.id.percentageView);
        TextView battStatusV = (TextView) layout.findViewById(R.id.battStatusView);

        if(plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB)
            battStatusV.setText(ctx.getString(R.string.battStatus_connected));
        else
            battStatusV.setText(ctx.getString(R.string.battStatus_unconnected));

        TextView batteryV = (TextView) layout.findViewById(R.id.batteryView);
        batteryV.setText(String.valueOf(level));

        //battStatusV.setTextColor(color);
        //batteryV.setTextColor(color);
        //percentSymbolV.setTextColor(color);

    }


    public static void getConfiguration(Context context) {

		Log.d(TAG, "getConfiguration");
		
		theme = SWDreamSettingsActivity.loadTitlePref(context, "theme");
		color = SWDreamSettingsActivity.loadTitlePrefColor(context, "color");
		size = SWDreamSettingsActivity.loadTitlePrefSize(context, "size");
        nightMode = SWDreamSettingsActivity.loadTitlePref(context, "nightMode");
        String format = SWDreamSettingsActivity.loadTitlePref(context, "format");


        if (null == theme || "".equals(theme)){
			theme = "fortunes";
		}

        if (null == nightMode || "".equals(nightMode)){
            nightMode = "false";
        }
		
		if (-1 == color){
			color = Color.parseColor("#FFFFFF");
		}
		
		if (0 == size){
			size = 30;
		}

        if (SugarWiseUtils.HOURS_FORMAT_12.equals(format)) is24HoursFormat = false;
        else is24HoursFormat = true;


        Log.d(TAG + "theme", theme);
        Log.d(TAG + "color", String.valueOf(color));
        Log.d(TAG + "size", String.valueOf(size));
        Log.d(TAG + "nightMode", nightMode);
        Log.d(TAG + "format", format);


    }

    private void turnDownBrightness() {

        Log.d(TAG, "turnDownBrightness");

        android.provider.Settings.System.putInt(getContentResolver(),
                android.provider.Settings.System.SCREEN_BRIGHTNESS,
                0);

        Log.d(TAG + "brightness", "Turned down: 0");
    }

    private void restoreBrightnessInDevice(){

        Log.d(TAG, "restoreBrightnessInDevice");

        int brightness = SWDreamSettingsActivity.loadIntPref(this,"brightnessInDevice");

        android.provider.Settings.System.putInt(getContentResolver(),
                android.provider.Settings.System.SCREEN_BRIGHTNESS,
                brightness);

        Log.d(TAG + "brightness", "Turned up: " + brightness);

    }


	@Override
	public void onDreamingStopped() {

        Log.d(TAG, "onDreamingStopped");

        restoreBrightnessInDevice();
		
		super.onDreamingStopped();
	}


    @Override
    public void onDetachedFromWindow() {

        Log.d(TAG, "onDetachedFromWindow");

        // Unregister Broadcast Receiver
        unregisterReceiver(m_timeChangedReceiver);

        super.onDetachedFromWindow();
    }
}
