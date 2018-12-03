package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

     public static final String NAME = "name";
     public static final String MAIN_NAME="mainName";
    public static final String ALSO_KNOWN_AS="alsoKnownAs";
    public static final String PLACE_OF_ORIGIN="placeOfOrigin";
    public static final String DESCRIPTION="description";
    public static final String IMAGE="image";
    public static final String INGREDIENTS="ingredients";

    /**
     * Parse the json string and form a sandwich object with the key:value pairs obtained from the json.
     */

    public static Sandwich parseSandwichJson(String json)  {

        try {
            // extract object
            JSONObject sandwichJson = new JSONObject(json);
            //extract name
            JSONObject name = sandwichJson.getJSONObject(NAME);
            //extract main_name
            String mainName = name.getString(MAIN_NAME);
            //extract alsoKnownAs
            JSONArray alsoKnownAs = name.getJSONArray(ALSO_KNOWN_AS);
            // convert jsonarray into list
            List<String> alsoKnowAsList = jsonArrayToList(alsoKnownAs);
            //extract place of origin
            String placeOfOrigin = sandwichJson.getString(PLACE_OF_ORIGIN);
            //extract description
            String description = sandwichJson.getString(DESCRIPTION);
            //extract image
            String image = sandwichJson.getString(IMAGE);
            //extract ingrredients
            JSONArray ingredients = sandwichJson.getJSONArray(INGREDIENTS);
            // convert jsonarray into list
            List<String> ingredientsList = jsonArrayToList(ingredients);
            // create a sandwich object
            Sandwich sandwich = new Sandwich(mainName,alsoKnowAsList,placeOfOrigin,description,image,ingredientsList);
            return sandwich;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
    /**
     * Method to convert JSONArray into List<String>
     */
    public static List<String> jsonArrayToList(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<String>();
        for(int i=0;i<jsonArray.length();i++){
            list.add(jsonArray.getString(i));
        }
        return list;
    }
}
