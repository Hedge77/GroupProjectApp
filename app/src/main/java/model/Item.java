/**
 * TCSS 450 Mobile Programming
 * Project PhaseI Group 7
 *
 * @author Jisu Shin, Ryan Roe, Brandon Lo
 * @version 1.0
 */
package model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private String itemID;
    private String title;
    private String itemURL;
    private String location;
    private String price;
    private String bestOfferEnabled;
    private String buyItNow;
    private String json;


    private Item() {
        super();
    }

    public static List<Item> ebayJsonToItems(String jsonString) {
        ArrayList<Item> result = new ArrayList<Item>();
        Item nextItem;
        try {
            JSONObject json1 = new JSONObject(jsonString);
            JSONArray jsonArr1 = json1.getJSONArray("findItemsByKeywordsResponse");
            JSONObject json2 = jsonArr1.getJSONObject(0);
            JSONArray jsonArr2 = json2.getJSONArray("searchResult");
            JSONObject json3 = jsonArr2.getJSONObject(0);
            int count = json3.getInt("@count");
            JSONArray items = json3.getJSONArray("item");
//            json = items.toString();
            int maxDesired = 20;
            if (count < maxDesired) {
                maxDesired = count;
            }
            for (int i = 0; i < maxDesired; i++) {
                nextItem = new Item();
                JSONObject next = items.getJSONObject(i);
//                Log.d("ITEM", (nextItem == null ? "Is null" : "not null"));
                JSONArray attribute = next.getJSONArray("itemId");
                nextItem.itemID = attribute.getString(0);
//                Log.d("ITEM", nextItem.toString());

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
//                Log.d("ITEM", nextItem.toString());

                result.add(nextItem);
            }
//            Log.d("LIST", (result == null ? "Is null" : "not null"));

        } catch (JSONException e) {
            e.printStackTrace();

        }
//        Log.d("LIST", (result == null ? "Is null" : "not null"));

        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(title);
        sb.append('\n');
        sb.append('$');
        sb.append(price);
        sb.append('\n');
        sb.append(location);
        sb.append('\n');
        sb.append(itemURL);
        sb.append('\n');
        sb.append("BuyItNow: ");
        sb.append(buyItNow);

        return sb.toString();
    }

    public String getItemID() {return itemID;}
    public String getTitle() {return title;}
    public String getItemURL() {return itemURL;}
    public String getLocation() {return location;}
    public String getPrice() {return price;}
    public String getBestOfferEnabled() {return bestOfferEnabled;}
    public String getBuyItNow() {return buyItNow;}
}
