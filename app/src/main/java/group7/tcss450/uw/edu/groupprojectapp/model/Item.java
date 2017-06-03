/**
 * TCSS 450 Mobile Programming
 * Project PhaseI Group 7
 *
 * @author Jisu Shin, Ryan Roe, Brandon Lo
 * @version 1.0
 */
package group7.tcss450.uw.edu.groupprojectapp.model;

//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;

import android.support.annotation.NonNull;

import group7.tcss450.uw.edu.groupprojectapp.XMLtoJSON.JSONArray;
import group7.tcss450.uw.edu.groupprojectapp.XMLtoJSON.JSONException;
import group7.tcss450.uw.edu.groupprojectapp.XMLtoJSON.JSONObject;
import group7.tcss450.uw.edu.groupprojectapp.XMLtoJSON.XML;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Comparable<Item>, Serializable {

    /**
     * Number of items collected from the large JSON
     */
    private static int MAX_DESIRED = 20;

    private String itemID;
    private String title;
    private String itemURL;
    private String location;
    private String price;
    private String bestOfferEnabled;
    private String buyItNow;
    private String source;


    private Item() {
        super();
    }

    public static List<Item> ebayJsonToItems(String jsonString) {
        ArrayList<Item> result = new ArrayList<>();
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

            if (count > MAX_DESIRED) {
                count = MAX_DESIRED;
            }
            for (int i = 0; i < count; i++) {
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

                nextItem.source = "(" + "eBay" + ")";

                result.add(nextItem);
            }
//            Log.d("LIST", (result == null ? "Is null" : "not null"));

        } catch (JSONException e) {
            e.printStackTrace();

        }
//        Log.d("LIST", (result == null ? "Is null" : "not null"));

        return result;
    }

    public static List<Item> amazonXMLtoItems(String xmlString) {
        ArrayList<Item> result = new ArrayList<>();
        Item nextItem;
        try {
            JSONObject json1 = XML.toJSONObject(xmlString, true);
            JSONObject json2 = json1.getJSONObject("ItemSearchResponse");
            JSONObject json3 = json2.getJSONObject("Items");
            JSONArray items = json3.getJSONArray("Item");

            //amazon only returns 10 items max per call
            int count = 10;

            for (int i = 0; i < count; i++) {
                nextItem = new Item();
                JSONObject next = items.getJSONObject(i);

                JSONObject summary = next.getJSONObject("OfferSummary");
                JSONObject lowestPrice = summary.getJSONObject("LowestNewPrice");
                nextItem.price = lowestPrice.getString("FormattedPrice");
                if (nextItem.price.charAt(0) == '$') {
                    nextItem.price = nextItem.price.substring(1, nextItem.price.length());
                }

                nextItem.itemID = next.getString("ASIN");

                JSONObject attributes = next.getJSONObject("ItemAttributes");
                nextItem.title = attributes.getString("Title");

                nextItem.itemURL = next.getString("DetailPageURL");

                nextItem.source = "(" + "Amazon" + ")";
                result.add(nextItem);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(title);
        sb.append('\n');
        if (price.charAt(0) != '$') {
            sb.append('$');
        }
        sb.append(price);
        sb.append('\n');
        if (location != null) {
            sb.append(location);
            sb.append('\n');
        }
        if (buyItNow != null) {
            sb.append("BuyItNow: ");
            sb.append(buyItNow);
            sb.append('\n');
        }
        sb.append(itemURL);
        sb.append('\n');
        sb.append(source);


        return sb.toString();
    }

    @Override
    public int compareTo(@NonNull Item other) {

        double thisPrice;
        double otherPrice;

        try {
            thisPrice = Double.parseDouble(this.price);
        } catch (NumberFormatException e) {
            return 1;
        }

        try {
            otherPrice = Double.parseDouble(other.price);
        } catch (NumberFormatException e) {
            return -1;
        }

//        thisPrice = Double.parseDouble(this.price);
//        otherPrice = Double.parseDouble(other.price);

        double result = thisPrice - otherPrice;
        if (result > 0) {
            return 1;
        } else if (result < 0) {
            return -1;
        }

        return 0;
    }

    public String getItemID() {return itemID;}
    public String getTitle() {return title;}
    public String getItemURL() {return itemURL;}
    public String getLocation() {return location;}
    public String getPrice() {return price;}
    public String getBestOfferEnabled() {return bestOfferEnabled;}
    public String getBuyItNow() {return buyItNow;}
    public Item getItem() {return this;}
    public String getSource() {return source;}
}
