/**
 * TCSS 450 Mobile Programming
 * Project PhaseI Group 7
 *
 * @author Jisu Shin, Ryan Roe, Brandon Lo
 * @version 1.0
 */
package group7.tcss450.uw.edu.groupprojectapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import model.Item;


/**
 * DisplayResultsFragment
 *
 * This fragment class is used to display the results from the web services.
 *
 * @author Ryan Roe
 * @version 1.0
 */
public class DisplayResultsFragment extends Fragment {

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
        }
        if (items != null) {
            StringBuilder sb = new StringBuilder();
            for (String i : items) {
                sb.append(i);
                sb.append("\n\n\n");
            }
            TextView v = (TextView) getView().findViewById(R.id.displayResults);
            v.setText(sb.toString());
        }
    }

    /**
     * Method to attach the context before OnFragmentInteraction.
     *
     * @param context The context of the DisplayResultsFragment
     */
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    /**
//     * Method to detach the context and set OnFragmentInteractionListener to null.
//     */
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * Interface to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     *
//     */
//    public interface OnFragmentInteractionListener {
//        void onFragmentInteraction(Uri uri);
//    }
}
