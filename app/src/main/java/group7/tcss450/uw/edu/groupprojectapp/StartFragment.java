package group7.tcss450.uw.edu.groupprojectapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * StartFragment
 *
 * This fragment class is used to display start-up screen
 * and send button name to MainActivity class.
 *
 * @author Jisu Shin
 * @version 1.0
 */
public class StartFragment extends Fragment implements View.OnClickListener {

    /** Private OnFragmentInteractionListener for this class  */
    private OnFragmentInteractionListener mListener;

    /**
     * Empty Constructor for StartFragment
     *
     */
    public StartFragment() {
        // Required empty public constructor
    }

    /**
     * Method that creates a view for StartFragment and return the view
     *
     * @param inflater The LayoutInflater for creating view
     * @param container The ViewGroup for creating view
     * @param savedInstanceState The state of saved instance
     * @return The view of the StartFragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_startfragment, container, false);
        Button b = (Button) v.findViewById(R.id.signin);
        b.setOnClickListener(this);
        b = (Button) v.findViewById(R.id.signup);
        b.setOnClickListener(this);
        return v;
    }

    /**
     * Method to attach the context before OnFragmentInteraction.
     *
     * @param context The context of the StartFragment
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
     * Method to set onClick to sign-in and sign-up buttons.
     *
     * @param view The view of the StartFragment
     */
    @Override
    public void onClick(View view) {
        if (mListener != null) {
            switch (view.getId()) {
                case R.id.signin:
                    // no username and password yet
                    mListener.onFragmentInteraction("signin","","");
                    break;
                case R.id.signup:
                    mListener.onFragmentInteraction("signup","","");
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
        // send button name, username and password
        void onFragmentInteraction(String button, String username, String pwd);
    }
}
