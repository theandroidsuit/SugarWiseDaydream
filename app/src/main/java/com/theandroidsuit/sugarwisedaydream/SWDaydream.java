package com.theandroidsuit.sugarwisedaydream;

import android.content.Context;
import android.graphics.Color;
import android.service.dreams.DreamService;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.theandroidsuit.sugarwisedaydream.bean.Wisdom;
import com.theandroidsuit.sugarwisedaydream.util.SugarWiseUtils;

/**
 *
 * THE ANDROID SUIT 2014
 * @author Virginia Hernandez
 * @version 1.0
 *
 */

public class SWDaydream extends DreamService implements OnClickListener{

    private static final String TAG = "SWDaydream";
    
	private static String theme = "fortune";
	private static int color = Color.parseColor("#FFFFFF");
	private static int size = 10;
    private static String nightMode = "false";
	
	private TextView tvSentence;
	private TextView tvAuthor;
//	private AnimatorSet textSet;
	
	@Override
	public void onClick(View v) {

        restoreBrightnessInDevice();
		//stop button
		this.finish();
	}
	
	@Override
	public void onAttachedToWindow() {

		super.onAttachedToWindow();
		
		// Set the Daydream to be interactive and occupy the full screen, hiding the status bar.
		setInteractive(true);
		setFullscreen(true);
		
		getConfiguration(this);

        if("true".equals(nightMode)){
            turnDownBrightness();
        }

        // Our content view will take care of animating its children.
		setContentView(R.layout.activity_main);
        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.layoutDream);

//        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
//   		params1.addRule(RelativeLayout.CENTER_IN_PARENT);
//   		
//        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
//   		params2.addRule(RelativeLayout.BELOW);
//        
//        tvSentence = new TextView(this);
//        tvAuthor = new TextView(this);
        
        tvSentence = (TextView) findViewById(R.id.sentence);
        tvAuthor = (TextView) findViewById(R.id.author);
        
		SugarWiseUtils utils = new SugarWiseUtils(this, theme);	
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
                
        //layout.addView(tvSentence, params1);        
        //layout.addView(tvAuthor, params2);        

        
        //textSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.sw_spin);
        //textSet.setTarget(textView);
        
        layout.setOnClickListener(this);
        
	}




    public static void getConfiguration(Context context) {
		Log.d(TAG, "getConfiguration");
		
		theme = SWDreamSettingsActivity.loadTitlePref(context, "theme");
		color = SWDreamSettingsActivity.loadTitlePrefColor(context, "color");
		size = SWDreamSettingsActivity.loadTitlePrefSize(context, "size");
        nightMode = SWDreamSettingsActivity.loadTitlePref(context, "nightMode");

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
		
        Log.d(TAG + "theme", theme);
        Log.d(TAG + "color", String.valueOf(color));
        Log.d(TAG + "size", String.valueOf(size));
        Log.d(TAG + "nightMode", nightMode);

    }

    private void turnDownBrightness() {
        android.provider.Settings.System.putInt(getContentResolver(),
                android.provider.Settings.System.SCREEN_BRIGHTNESS,
                0);

        Log.d(TAG + "brightness", "Turned down: 0");
    }

    private void restoreBrightnessInDevice(){
        int brightness = SWDreamSettingsActivity.loadIntPref(this,"brightnessInDevice");

        android.provider.Settings.System.putInt(getContentResolver(),
                android.provider.Settings.System.SCREEN_BRIGHTNESS,
                brightness);

        Log.d(TAG + "brightness", "Turned up: " + brightness);

    }


    @Override
	public void onDreamingStarted() {
		super.onDreamingStarted();
		
		//textSet.start();		
	}
	
	@Override
	public void onDreamingStopped() {
		
		//textSet.cancel();
        restoreBrightnessInDevice();
		
		super.onDreamingStopped();
	}
	
	@Override
	public void onDetachedFromWindow() {	
		super.onDetachedFromWindow();
	}
}
