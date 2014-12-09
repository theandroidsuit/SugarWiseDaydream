package com.theandroidsuit.sugarwisedaydream;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 *
 * THE ANDROID SUIT 2014
 * @author Virginia Hernandez
 * @version 1.0
 *
 */

public class SWDreamSettingsActivity extends Activity implements OnItemSelectedListener{
	
    static final String TAG = "SWDreamSettingsActivity";

    private static final String PREFS_NAME = "com..SugarWiseDaydream";
    private static final String PREF_PREFIX_KEY = "prefix_swDreamday";


	private int colorToUse = 0;
	private String themeToUse;
	private int sizeToUSe = -1;
	

	private static int color = 0;
	private static String theme;
	private static int size = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d(TAG, "onCreate");
		
		// Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if they press the back button.
		setResult(RESULT_CANCELED);

		// Set the view layout resource to use.
		setContentView(R.layout.swdaydream_config);
		
	
		// Register Spinners
		registerSpinnerListener();
		
		setPreviousConfiguration();
		
        // Bind the action for the save button.
		findViewById(R.id.buttonConfigure).setOnClickListener(mOnClickListener);
		
	}
	
	

    private void setPreviousConfiguration() {
    	 if (!"".equals(theme)){	
    		Spinner spinner = (Spinner) findViewById(R.id.themeSpinner);
    		ArrayAdapter<CharSequence> myAdap = (ArrayAdapter) spinner.getAdapter(); //cast to an ArrayAdapter

 			int spinnerPosition = myAdap.getPosition(theme);
 			
 			//set the default according to value
 			spinner.setSelection(spinnerPosition);
    	 }
					
	}

	public static void getConfiguration(Context context) {
		Log.d(TAG, "getConfiguration");
		
		theme = SWDreamSettingsActivity.loadTitlePref(context, "theme");
		color = SWDreamSettingsActivity.loadTitlePrefColor(context, "color");
		size = SWDreamSettingsActivity.loadTitlePrefSize(context, "size");
		
		
        Log.d(TAG + "theme", theme);
        Log.d(TAG + "color", String.valueOf(color));
        Log.d(TAG + "size", String.valueOf(size));
	}

	View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = SWDreamSettingsActivity.this;

            // When the button is clicked, save the string in our prefs and return that they
            // clicked OK.
            
            if (0 == colorToUse || -1 == sizeToUSe || null == themeToUse || "".equals(themeToUse)){
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_not_configure), Toast.LENGTH_SHORT).show();
            }else{
            
	            Log.d(TAG, "OnClickListener");
	
	            Log.d(TAG + "theme", themeToUse);
	            Log.d(TAG + "color", String.valueOf(colorToUse));
	            Log.d(TAG + "size", String.valueOf(sizeToUSe));
	            
	            saveTitlePref(context, "theme", themeToUse);
	            saveTitlePref(context, "color", colorToUse);
	            saveTitlePref(context, "size", sizeToUSe);
	            
	            finish();
            }
        }
    };


	
    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, String key, String value) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + key, value);
        prefs.commit();
    }
    
    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, String key, int value) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putInt(PREF_PREFIX_KEY + key, value);
        prefs.commit();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadTitlePref(Context context,  String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String value = prefs.getString(PREF_PREFIX_KEY + key, null);
        if (value != null) {
            return value;
        } else {
            return "";
        }
    }
    
    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static int loadTitlePrefColor(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        Integer value = prefs.getInt(PREF_PREFIX_KEY + key, 0);
        if (value != 0) {
            return value;
        } else {
            return Color.parseColor("#FFFFFF");
        }
    }

    
    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static int loadTitlePrefSize(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        Integer value = prefs.getInt(PREF_PREFIX_KEY + key, 0);
        if (value != 0) {
            return value;
        } else {
            return 0;
        }
    }
	
	private void registerSpinnerListener() {
		Spinner color = (Spinner) findViewById(R.id.colorSpinner);
		ArrayAdapter<CharSequence> colorAp = ArrayAdapter.createFromResource(this, R.array.colors, android.R.layout.simple_spinner_item);
		colorAp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		color.setAdapter(colorAp);
		
		Spinner theme = (Spinner) findViewById(R.id.themeSpinner);
		ArrayAdapter<CharSequence> themeAp = ArrayAdapter.createFromResource(this, R.array.themes, android.R.layout.simple_spinner_item);
		themeAp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		theme.setAdapter(themeAp);
		
		Spinner size = (Spinner) findViewById(R.id.sizeSpinner);
		ArrayAdapter<CharSequence> sizeAp = ArrayAdapter.createFromResource(this, R.array.sizes, android.R.layout.simple_spinner_item);
		sizeAp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		size.setAdapter(sizeAp);
		
		color.setOnItemSelectedListener(this);
		theme.setOnItemSelectedListener(this);
		size.setOnItemSelectedListener(this);
				
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

		int spinnerId = parent.getId();
		

			switch(spinnerId){
				case R.id.themeSpinner:
					if (0 == position) themeToUse = "";
					else setupTheme((String) parent.getItemAtPosition(position));
					break;
				case R.id.colorSpinner:
					if (0 == position) colorToUse = 0;
					else setupColor((String) parent.getItemAtPosition(position));
					break;
					
				case R.id.sizeSpinner:
					if (0 == position) sizeToUSe = -1;
					else setupSize((String) parent.getItemAtPosition(position));
					break;
			}
	}



	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
	}

	

	private void setupColor(String item) {
		switch(item){
			case "Red":
				colorToUse = Color.parseColor("#FF0000");
				break;
			case "Pink":
				colorToUse = Color.parseColor("#FF5CD6");
				break;
			case "White":
				colorToUse = Color.parseColor("#FFFFFF");
				break;
			case "Yellow":
				colorToUse = Color.parseColor("#FFFF4D");
				break;
			case "Grey":
				colorToUse = Color.parseColor("#696969");
				break;
			case "Blue":
				colorToUse = Color.parseColor("#3333FF");
				break;
			case "Green":
				colorToUse = Color.parseColor("#2EB82E");
				break;
			case "Purple":
				colorToUse = Color.parseColor("#7A007A");
				break;
			default:
				colorToUse = Color.parseColor("#000000");
				break;
		};
	}
	
	private void setupTheme(String item) {
		
		themeToUse = item.toLowerCase().replace(" ", "").trim();
		Log.d(TAG + ".theme!", themeToUse);
	}
	
	private void setupSize(String item) {
		// TODO Auto-generated method stub
		switch(item){
			case "Medium":
				sizeToUSe = 24;
				break;
			case "Large":
				sizeToUSe = 32;
				break;
			default: 
				sizeToUSe = 16;
				break;
		}
	}
}
