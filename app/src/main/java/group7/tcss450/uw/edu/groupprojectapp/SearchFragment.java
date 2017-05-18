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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * SearchFragment
 *
 * This fragment class is used to display search screen
 * and send a searched word to MainActivity class.
 *
 * @author Ryan Roe
 * @version 1.0
 */
public class SearchFragment extends Fragment implements View.OnClickListener {

    /** Private OnFragmentInteractionListener for this class  */
    private OnFragmentInteractionListener mListener;

    /** The private object of EditText for searching word  */
    private EditText searchBox;

    /**
     * Empty Constructor for SearchFragment
     *
     */
    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Method that creates a view for SearchFragment and return the view
     *
     * @param inflater The LayoutInflater for creating view
     * @param container The ViewGroup for creating view
     * @param savedInstanceState The state of saved instance
     * @return The view of the SearchFragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        // Set OnclickListner to button
        Button b = (Button) v.findViewById(R.id.searchButton);
        b.setOnClickListener(this);
        return v;
    }

    /**
     * Method to declare private EditText variable when the view is created.
     *
     * @param view The view of the SearchFragment
     * @param savedInstanceState The bundle of the view
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        searchBox = (EditText) view.findViewById(R.id.searchBox);
    }

    /**
     * Method to attach the context before OnFragmentInteraction.
     *
     * @param context The context of the SearchFragment
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * Method to detach the context and set OnFragmentInteractionListener to null.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Method to set onClick to "Search" button.
     *
     * @param v The view of the SignUpFragment
     */
    @Override
    public void onClick(View v) {
        // get text from EditText object
        String word =  searchBox.getText().toString();

        if (mListener != null) {
            switch (v.getId()) {
                case R.id.searchButton:
                    //ProgressDialog mProg = new ProgressDialog(this.getContext());
                    ProgressDialog mProg = ProgressDialog.show(this.getContext(), "Loading", "Wait while loading...");
//                    mProg.setTitle("Loading");
//                    mProg.setMessage("Wait while loading...");
                    // mProg.setCancelable(false); // disable dismiss by tapping outside of the dialog
                    //mProg.show(this.getContext(), "Loading", "Wait while loading...");
                    mListener.onFragmentInteraction(word);
                    searchBox.getText().clear();
                    mProg.dismiss();
                    break;
            }
        }
    }

    /**
     * Interface to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String word);
    }
}
