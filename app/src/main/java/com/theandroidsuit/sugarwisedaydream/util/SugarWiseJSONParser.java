package com.theandroidsuit.sugarwisedaydream.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.theandroidsuit.sugarwisedaydream.bean.Wisdom;

public class SugarWiseJSONParser {

    public List<Wisdom> getDatafromAsset(Context context, String theme){
		JSONObject jObject = loadJSON(context, SugarWiseUtils.MODE_ASSET, theme);
		
		return parse(jObject); 
	}

    /** Receives a JSONObject and returns a list */
    private List<Wisdom> parse(JSONObject jObject){
 
        JSONArray jWisdom = null;
        String type = "";
        try {
            /** Retrieves all the elements in the 'wisdom' array */
            jWisdom = jObject.getJSONArray("wisdom");
            type = (String) jObject.getString("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
            
        return getSugarWisdom(jWisdom, type);
    }

	
    private List<Wisdom> getSugarWisdom(JSONArray jWisdom, String type) {
    	
    	int wisdomCount = jWisdom.length();
        List<Wisdom> wisdomList = new ArrayList<Wisdom>();
        Wisdom wisdom = null;
 
        /** Taking each Wisdom, parses and adds to list object */
        for(int i = 0; i < wisdomCount; i++){
            try {
                /** Call getCrystalWisdom with wisdom JSON object to parse the Wisdom */
                wisdom = getCrystalWisdom((JSONObject)jWisdom.get(i));
                wisdom.setType(type);
                wisdomList.add(wisdom);
 
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
 
        return wisdomList;
	}

	private Wisdom getCrystalWisdom(JSONObject jWisdom) {
        Wisdom wisdom = new Wisdom();
        
        try {

            // Extracting id, if available    	
            if(!jWisdom.isNull("id")){
            	wisdom.setId(jWisdom.getString("id"));
            }
        	
            // Extracting title, if available
            if(!jWisdom.isNull("title")){
            	wisdom.setTitle(jWisdom.getString("title"));
            }
            
            // Extracting sentence, if available
            if(!jWisdom.isNull("sentence")){
            	wisdom.setSentence(jWisdom.getString("sentence"));
            }
            
            // Extracting extras, if available
            if(!jWisdom.isNull("extras")){
            	wisdom.setExtras(jWisdom.getString("extras"));
            }
            
            // Extracting author, if available
            if(!jWisdom.isNull("author")){
            	wisdom.setAuthor(jWisdom.getString("author"));
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        
        return wisdom;
	}

	public JSONObject loadJSON(Context context, String mode, String theme){
    	if (mode.equals(SugarWiseUtils.MODE_ASSET)){
    		return loadJSONFromAsset(context, theme);
    	}else if (mode.equals(SugarWiseUtils.MODE_SERVICE)){
    		// Call Sinatra/Google AppEngine and return JSON
    		return null;
    	}
    	
    	return null;
    }
    
    
    private JSONObject loadJSONFromAsset(Context context, String theme) {
        String jsonStr = null;
        try {

        	String fileTheme = theme + ".json";
        	
            InputStream is = context.getAssets().open(fileTheme);
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            jsonStr = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        
        JSONObject json = null;
		try {
			json = new JSONObject(jsonStr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
        
        return json;

    }

}
