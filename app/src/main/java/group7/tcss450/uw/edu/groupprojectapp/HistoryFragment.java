/**
 * TCSS 450 Mobile Programming
 * Project PhaseI Group 7
 *
 * @author Jisu Shin, Ryan Roe, Brandon Lo
 * @version 1.0
 */
package group7.tcss450.uw.edu.groupprojectapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * HistoryFragment
 *
 * This fragment class is to get the searched words from SearchFragment and display the words.
 *
 * @author Jisu Shin
 * @version 1.0
 */
public class HistoryFragment extends Fragment {

    /** The TextView to display the searched words  */
    private TextView mTV;

    /** The Layout for displaying the searched words  */
    private LinearLayout mView;

    /**
     * Empty Constructor for HistoryFragment
     *
     */
    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Method that creates a view for HistoryFragment and return the view
     *
     * @param inflater The LayoutInflater for creating view
     * @param container The ViewGroup for creating view
     * @param savedInstanceState The state of saved instance
     * @return The view of the HistoryFragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = (LinearLayout) inflater.inflate(R.layout.fragment_history, container, false);
        try {
            // Get words from the file and put them to TextView
            InputStream inputStream = getActivity()
                    .openFileInput(getString(R.string.searched_words));
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                while ((receiveString = bufferedReader.readLine()) != null) {
                    mTV = new TextView(getActivity());
                    mTV.setText(receiveString);
                    mView.addView(mTV);
                }
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mView;
    }
}
