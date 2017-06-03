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
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * DisplayResultsFragment
 *
 * This fragment class is used to display the results from the web services.
 *
 * @author Ryan Roe
 * @version 1.0
 */
public class DisplayResultsFragment extends Fragment{

    private List<String> mResults;

    private List<String> mFilters;
    /**
     * Empty Constructor for DisplayResultsFragment
     *
     */
    public DisplayResultsFragment() {
        // Required empty public constructor
    }

    /**
     * Method that creates a view for DisplayResultsFragment and return the view
     *
     * @param inflater The LayoutInflater for creating view
     * @param container The ViewGroup for creating view
     * @param savedInstanceState The state of saved instance
     * @return The view of the DisplayResultsFragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflated = inflater.inflate(R.layout.fragment_display_results, container, false);
        return inflated;
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayList<String> items = null;
        if(getArguments() != null) {
            Bundle args = getArguments();
            items = args.getStringArrayList(getString(R.string.items_key));
            mResults = items;
            mFilters = args.getStringArrayList(getString(R.string.filter_key));
        }

        showResults();
    }

    /**
     * Display the results in a scrolling text view
     */
    public void showResults() {
        if (mFilters.size() > 0) {
            TextView tv = (TextView) getView().findViewById(R.id.resultsTextView);
            String s = getString(R.string.results_text_view_text);
            s += "(" + mFilters.get(0);
            for (int i = 1; i < mFilters.size(); i++) {
                s += ", " + mFilters.get(i);
            }
            s += ")";
            tv.setText(s);
        }

        if (mResults != null) {
            StringBuilder sb = new StringBuilder();
            for (String i : mResults) {
                for (String j : mFilters) {
                    if (i.contains(j)) {
                        sb.append(i);
                        sb.append("\n\n\n");
                    }
                }
            }
            TextView v = (TextView) getView().findViewById(R.id.displayResults);
            v.setText(sb.toString());
            //Extremely convenient class that makes all web urls clickable
            Linkify.addLinks(v, Linkify.WEB_URLS);
        }
    }

}
