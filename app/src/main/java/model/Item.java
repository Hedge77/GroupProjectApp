/**
 * TCSS 450 Mobile Programming
 * Project PhaseI Group 7
 *
 * @author Jisu Shin, Ryan Roe
 * @version 1.0
 */
package model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 5/5/2017.
 */

public class Item {

    private Item() {

    }

    public static List<Item> ebayJsonToItems(String jsonString) {
        ArrayList<Item> result = new ArrayList<Item>();

        try {
            JSONObject json1 = new JSONObject(jsonString);
            JSONArray jsonArr1 = json1.getJSONArray("findItemsByKeywordsResponse");
            JSONObject json2 = jsonArr1.getJSONObject(0);
            JSONArray jsonArr2 = json2.getJSONArray("searchResult");
            JSONObject json3 = jsonArr2.getJSONObject(0);
            int count = json3.getInt("@count");
            JSONArray items = json3.getJSONArray("item");
            int maxDesired = 20;
            if (count < maxDesired) {
                maxDesired = count;
            }
            for (int i = 0; i < maxDesired; i++) {

            }

        } catch (JSONException e) {
            e.printStackTrace();

        }


        return result;
    }
}
