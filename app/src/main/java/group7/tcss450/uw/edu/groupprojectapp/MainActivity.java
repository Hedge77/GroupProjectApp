package group7.tcss450.uw.edu.groupprojectapp;

import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity
                            implements SignInFragment.OnFragmentInteractionListener,
                                        SignUpFragment.OnFragmentInteractionListener,
                                            StartFragment.OnFragmentInteractionListener{

    private static final String PARTIAL_URL
            = "http://cssgate.insttech.washington.edu/" +
            "~jisus/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            if (findViewById(R.id.fragmentContainer) != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragmentContainer, new StartFragment())
                        .commit();
            }
        }
    }

    @Override
    public void onFragmentInteraction(String button, String username, String pwd) {
        Log.d("ACTIVITY", button);
        if(button == "signin"){
            SignInFragment frag2;
            frag2 =  new SignInFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, frag2)
                    .addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        }
        if(button == "signup"){
            SignUpFragment frag3;
            frag3 =  new SignUpFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, frag3)
                    .addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        }
        if(button == "display1") {
            AsyncTask<String, Void, String> task = null;
            task = new GetWebServiceTask();
            task.execute(PARTIAL_URL, username, pwd);
        }
        if(button == "display2") {
            AsyncTask<String, Void, String> task = null;
            task = new PostWebServiceTask();
            task.execute(PARTIAL_URL, username, pwd);
        }
    }

    private class GetWebServiceTask extends AsyncTask<String, Void, String> {
        private final String SERVICE = "_get.php";
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
                response = "Unable to connect, Reason: "
                        + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }
            if (result.equals("")) {
                Toast.makeText(getApplicationContext(),
                        "Wrong username or password", Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    private class PostWebServiceTask extends AsyncTask<String, Void, String> {
        private final String SERVICE = "_post.php";
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
        @Override
        protected void onPostExecute(String result) {
            if (result.startsWith("Unable to")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }
            if (result.startsWith("INSERT")) {
                Toast.makeText(getApplicationContext(), "Already exist!", Toast.LENGTH_LONG)
                        .show();
                return;
            }
        }
    }

}