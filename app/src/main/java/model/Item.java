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

    private String itemID;
    private String title;
    private String itemURL;
    private String location;
    private String price;
    private String bestOfferEnabled;
    private String buyItNow;


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
                Item nextItem = new Item();
                JSONObject next = items.getJSONObject(i);

                JSONArray attribute = next.getJSONArray("itemID");
                nextItem.itemID = attribute.getString(0);

                attribute = next.getJSONArray("title");
                nextItem.title = attribute.getString(0);

                attribute = next.getJSONArray("viewItemURL");
                nextItem.itemURL = attribute.getString(0);

                attribute = next.getJSONArray("location");
                nextItem.location = attribute.getString(0);

                attribute = next.getJSONArray("sellingStatus").getJSONObject(0).getJSONArray("convertedCurrentPrice");
                nextItem.price = attribute.getJSONObject(0).getString("__value__");

                attribute = next.getJSONArray("listingInfo").getJSONObject(0).getJSONArray("bestOfferEnabled");
                nextItem.bestOfferEnabled = attribute.getString(0);

                attribute = next.getJSONArray("listingInfo").getJSONObject(0).getJSONArray("buyItNowAvailable");
                nextItem.buyItNow = attribute.getString(0);

                result.add(nextItem);
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }


        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        return null;
    }

    public String getItemID() {return itemID;}
    public String getTitle() {return title;}
    public String getItemURL() {return itemURL;}
    public String getLocation() {return location;}
    public String getPrice() {return price;}
    public String getBestOfferEnabled() {return bestOfferEnabled;}
    public String getBuyItNow() {return buyItNow;}
}
