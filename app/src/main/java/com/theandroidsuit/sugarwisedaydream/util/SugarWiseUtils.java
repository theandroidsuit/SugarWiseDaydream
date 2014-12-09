package com.theandroidsuit.sugarwisedaydream.util;

import java.util.List;
import java.util.Random;

import com.theandroidsuit.sugarwisedaydream.bean.Wisdom;

import android.content.Context;


public class SugarWiseUtils {
    private static final String TAG = "SugarWiseUtils";

	
	static final String WIDGET_UPDATE_ACTION = "com.theandroidsuit.intent.action.UPDATE_WIDGET";
	
	public static final String MODE_ASSET = "asset";
	public static final String MODE_SERVICE = "service";
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
	
	public Wisdom getCurrentWisdomCrystal() {
		return wisdom;
	}
}
