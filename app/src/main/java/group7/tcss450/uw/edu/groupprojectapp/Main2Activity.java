/**
 * TCSS 450 Mobile Programming
 * Project PhaseI Group 7
 *
 * @author Jisu Shin, Ryan Roe
 * @version 1.0
 */
package group7.tcss450.uw.edu.groupprojectapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import model.Item;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchFragment.OnFragmentInteractionListener{

    private List<Item> mSearchResults;
    private Menu mMenu;

    private String mSearchTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Go Back to Home", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mMenu.getItem(0).setVisible(false);
                loadFragment(new SearchFragment());
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState == null) {
           if (findViewById(R.id.main_container) != null) {
                getSupportFragmentManager().beginTransaction()
                  .add(R.id.main_container, new SearchFragment()).commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, mMenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.ic_menu_delete){
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Deleting Saved History")
                .setMessage("Are you sure you want to delete the contents?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                                    openFileOutput(getString(R.string.searched_words), 0));
                            outputStreamWriter.write("");
                            outputStreamWriter.close();
                            mMenu.getItem(2).setVisible(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mMenu.getItem(0).setVisible(false);
                        loadFragment(new SearchFragment());
                    }
                })
                .show();
    }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_history) {
            loadFragment(new HistoryFragment());
            mMenu.getItem(0).setVisible(true);
        } else if (id == R.id.nav_home) {
            mMenu.getItem(0).setVisible(false);
            loadFragment(new SearchFragment());
        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadFragment(android.support.v4.app.Fragment frag) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, frag)
                .addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }

    private void saveToFile(String word) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    openFileOutput(getString(R.string.searched_words), Context.MODE_APPEND));
            outputStreamWriter.append("Searched Item: ");
            outputStreamWriter.append(word);
            outputStreamWriter.append("\n");
            outputStreamWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFragmentInteraction(String word) {
        //Async tasks call each other to ensure they are all finished before moving to next fragment
        //possibly save search terms here too?
        //Log.d("WORDS", s);
        saveToFile(word);
        AsyncTask<String, Void, String> task = new EbayWebServiceTask();
        task.execute(word);
    }

    private class EbayWebServiceTask extends AsyncTask<String, Void, String> {
        private final String ebayUrl = "http://svcs.ebay.com/services/search/FindingService" +
                "/v1?OPERATION-NAME=findItemsByKeywords&SERVICE-NAME=FindingService" +
                "&SECURITY-APPNAME=RyanRoe-TestAppl-PRD-a09141381-6ccb26aa" +
                "&RESPONSE-DATA-FORMAT=JSON" +
                "&REST-PAYLOAD" +
                "&keywords=";

        @Override
        protected String doInBackground(String... strings) {
            if (strings.length != 1) {
                throw new IllegalArgumentException("One string argument required");
            }
            String response = "";
            HttpURLConnection urlConnection = null;
            String args = replaceSpaces(strings[0]);
            try {
                URL urlObject = new URL(ebayUrl + args);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            //something wrong with network or url
            if (result.startsWith("Unable to")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                return;
            }
            List<Item> ebayItems = Item.ebayJsonToItems(result);

            for(int i = 0; i < ebayItems.size(); i++) {
                mSearchResults.add(ebayItems.get(i));
            }



        }
    }

    /*
    Working, but not yet integrated into the app.  See note in method below.  In the future this will
    be called from EbayWebServiceTask's onPostExecute().
    -Ryan
     */

    private class AmazonWebServiceTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            if (strings.length != 2) {
                throw new IllegalArgumentException("One string argument required");
            }
            String amazonURL = createSignedURL(strings[1]);
//            Log.d("RESULT", amazonURL);
            String response = "";
            HttpURLConnection urlConnection = null;
            try {
                URL urlObject = new URL(amazonURL);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            //something wrong with network or url
            if (result.startsWith("Unable to")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                return;
            }

        }
    }

    private String createSignedURL(String query) {
        /*
        Unlike Ebay, amazon requires a complex signed URL using a timestamp and a MAC,
        and their documentation for what exactly is required is convoluted.
        After a lot of work I got this system working, but it returns XML only so more work
        is required before this can be implemented into the app.
        -Ryan
         */

        final String key = "PqED+3g6+9q2brubA0tH6xfHi3jkT+NMt/EXxGAr";
        String partialURL = "http://webservices.amazon.com/onca/xml?";
        String args = "AWSAccessKeyId=AKIAJ73DRUHHHGAK62PQ" +
                "&AssociateTag=ry04-20" +
                "&Keywords=";
        args += replaceSpaces(query);
        args += "&Operation=ItemSearch" +
                "&ResponseGroup=Small" +
                "&SearchIndex=All" +
                "&Service=AWSECommerceService" +
                "&Timestamp=";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'-07:00'");
        String dateTime = dateFormat.format(new Date());
        //args += dateTime;
        //ex datetime
        try {
//            args += URLEncoder.encode("2017-05-06T01:42:49.000Z", "UTF-8");
            dateTime = URLEncoder.encode(dateTime, "UTF-8");
            args += dateTime;
        } catch(Exception e) {
            e.printStackTrace();
        }

        String stringToSign = "GET\nwebservices.amazon.com\n/onca/xml\n" + args;

        String result = "";
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            final SecretKeySpec secret_key = new javax.crypto.spec.SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
            mac.init(secret_key);
            byte[] bytes = mac.doFinal(stringToSign.getBytes("UTF-8"));
            result = Base64.encodeToString(bytes, Base64.DEFAULT);
            result = URLEncoder.encode(result, "UTF-8");
            result = result.replace("%3D%0A", "%3D");
//            result.replace("=", "%3D");
//            Log.d("RESULT", result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return partialURL + args + "&Signature=" + result;
    }

    private String replaceSpaces(String input) {
        return input.replace(" ", "%20");
    }
}
