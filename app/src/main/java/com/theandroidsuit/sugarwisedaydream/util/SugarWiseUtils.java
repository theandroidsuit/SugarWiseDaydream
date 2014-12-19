package com.theandroidsuit.sugarwisedaydream.util;

import java.util.List;
import java.util.Random;

import com.theandroidsuit.sugarwisedaydream.bean.Wisdom;

import android.content.Context;
import android.graphics.Color;


public class SugarWiseUtils {
    private static final String TAG = "SugarWiseUtils";

	
	static final String WIDGET_UPDATE_ACTION = "com.theandroidsuit.intent.action.UPDATE_WIDGET";
	
	public static final String MODE_ASSET = "asset";
	public static final String MODE_SERVICE = "service";

    public static final long MIN_INTERVAL = 60000l; // 1 minute

    public static final int COLOR_RED = Color.parseColor("#FF0000");
    public static final int COLOR_PINK = Color.parseColor("#DF0174");
    public static final int COLOR_BLUE = Color.parseColor("#3333FF");
    public static final int COLOR_GREEN = Color.parseColor("#0B6138");
    public static final int COLOR_GREY = Color.parseColor("#696969");
    public static final int COLOR_PURPLE = Color.parseColor("#7A007A");
    public static final int COLOR_WHITE = Color.parseColor("#FFFFFF");
    public static final int COLOR_YELLOW = Color.parseColor("#FFFF4D");
    public static final int COLOR_BLACK = Color.parseColor("#000000");


    public static final String HOURS_FORMAT_12 = "12 hours";
    public static final String HOURS_FORMAT_24 = "24 hours";

    private  List<Wisdom> listWisdom = null;
	private static Wisdom wisdom = null;
	
	private Context context;
	private String theme;
	
	public SugarWiseUtils( Context context, String theme) {
		this.context = context;
		this.theme = theme;
		getNewWisdomCrystal();
	}

	private  void getNewWisdomCrystal() {
		if ((null != theme && !"".equals(theme) && null != context) ||  null != listWisdom){

			if (null == listWisdom){
				SugarWiseJSONParser jParser = new SugarWiseJSONParser();
				listWisdom = jParser.getDatafromAsset(context, theme);
			}
			
			Random rnd = new Random(System.currentTimeMillis());
			int randomWisdomLocation = rnd.nextInt(listWisdom.size());
			
			wisdom = listWisdom.get(randomWisdomLocation);
			
			
		}else{
			wisdom = new Wisdom();
		}
	}

    public  Wisdom getNextWisdomCrystal() {

        if (null != listWisdom) {
            Random rnd = new Random(System.currentTimeMillis());
            int randomWisdomLocation = rnd.nextInt(listWisdom.size());

            return listWisdom.get(randomWisdomLocation);
        }else{
            return new Wisdom();
        }
    }
	
	public Wisdom getCurrentWisdomCrystal() {
		return wisdom;
	}
}
