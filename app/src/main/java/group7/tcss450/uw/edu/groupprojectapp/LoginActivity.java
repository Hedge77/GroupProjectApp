/**
 * TCSS 450 Mobile Programming
 * Project PhaseI Group 7
 *
 * @author Jisu Shin, Ryan Roe, Brandon Lo
 * @version 1.0
 */
package group7.tcss450.uw.edu.groupprojectapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * LoginActivity
 *
 * This activity class is used to display startup, registration and log-in screens.
 *
 * @author Jisu Shin
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity
                            implements SignInFragment.OnFragmentInteractionListener,
                                        SignUpFragment.OnFragmentInteractionListener,
                                            StartFragment.OnFragmentInteractionListener{

    /** Partial URL string for connecting to SQLite */
    private static final String PARTIAL_URL
            = "http://cssgate.insttech.washington.edu/" + "~jisus/login";

     /**
      * onCreate method to create main activity
      *
      * @param savedInstanceState The state of saved instance
      */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(savedInstanceState == null) {
            if (findViewById(R.id.fragmentContainer) != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragmentContainer, new StartFragment())
                        .commit();
            }
        }
    }

    /**
     * onFragmentInteraction method that enable the interaction
     * between StartFragment,SignInFragment and SignUpFragment.
     *
     * @param button The name of button that clicked
     * @param username The username from the fragment
     * @param pwd The password from the fragment
     */
    @Override
    public void onFragmentInteraction(String button, String username, String pwd) {

        if(button.equals("signin")){
            SignInFragment frag2;
            frag2 =  new SignInFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, frag2)
                    .addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        }

        if(button.equals("signup")){
            SignUpFragment frag3;
            frag3 =  new SignUpFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, frag3)
                    .addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        }

        if(button.equals("get")) {
            AsyncTask<String, Void, String> task = null;
            task = new GetWebServiceTask();
            task.execute(PARTIAL_URL, username, pwd);
        }

        if(button.equals("post")) {
            AsyncTask<String, Void, String> task = null;
            task = new PostWebServiceTask();
            task.execute(PARTIAL_URL, username, pwd);
        }
    }

    /**
     * GetWebServiceTask
     *
     * This private class is used to "get" web service using AsyncTask.
     *
     * @author Jisu Shin
     * @version 1.0
     */
    private class GetWebServiceTask extends AsyncTask<String, Void, String> {

        /** The URL string to put after PARTIAL_URL */
        private final String SERVICE = "_get.php";

        /**
         * doInBackground method to connect to webservice
         *
         * @param strings The string to connect to webservice
         * @return The response string from the webservice
         */
        @Override
        protected String doInBackground(String... strings) {

            if (strings.length != 3) {
                throw new IllegalArgumentException("Three String arguments required.");
            }
            String response = "";
            HttpURLConnection urlConnection = null;
            String url = strings[0];
            String args = "?my_id=" + strings[1] + "&my_pwd=" + strings[2];
            try {
                URL urlObject = new URL(url + SERVICE + args);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
            } catch (Exception e) {
                response = "Unable to connect, Reason: " + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return response;
        }

        /**
         * onPostExecute method that brings the result from "get" webservice
         *
         * @param result The result of getting username and password from webservice
         */
        @Override
        protected void onPostExecute(String result) {

            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }
            // if the username or password don't match with the records from SQLite,
            // display message and return.
            if (result.equals("")) {
                Toast.makeText(getApplicationContext(),
                        "Wrong username or password", Toast.LENGTH_LONG).show();
                return;
            }
            // if it successfully logs in, start MainActivity
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    /**
     * PostWebServiceTask
     *
     * This private class is used to "post" web service using AsyncTask.
     *
     * @author Jisu Shin
     * @version 1.0
     */
    private class PostWebServiceTask extends AsyncTask<String, Void, String> {

        /** The URL string to put after PARTIAL_URL */
        private final String SERVICE = "_post.php";

        /**
         * doInBackground method to connect to webservice
         *
         * @param strings The string to connect to webservice
         * @return The response string from the webservice
         */
        @Override
        protected String doInBackground(String... strings) {
            if (strings.length != 3) {
                throw new IllegalArgumentException("Three String arguments required.");
            }
            String response = "";
            HttpURLConnection urlConnection = null;
            String url = strings[0];
            try {
                URL urlObject = new URL(url + SERVICE);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                String data = URLEncoder.encode("my_id", "UTF-8")
                        + "=" + URLEncoder.encode(strings[1], "UTF-8")
                        + "&" + URLEncoder.encode("my_pwd", "UTF-8")
                        + "=" + URLEncoder.encode(strings[2], "UTF-8");
                wr.write(data);
                wr.flush();
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
            } catch (Exception e) {
                response = "Unable to connect, Reason: "
                        + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return response;
        }

        /**
         * onPostExecute method that brings the result from "post" webservice
         *
         * @param result The result of posting username and password from webservice
         */
        @Override
        protected void onPostExecute(String result) {

            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }
            // if the username already exists, display message and return
            if (result.startsWith("INSERT")) {
                Toast.makeText(getApplicationContext(), "Already exist!", Toast.LENGTH_LONG)
                        .show();
                return;
            }
            // if it successfully signs up, start MainActivity
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

}