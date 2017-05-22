/**
 * TCSS 450 Mobile Programming
 * Project PhaseI Group 7
 *
 * @author Jisu Shin, Ryan Roe, Brandon Lo
 * @version 1.0
 */
package group7.tcss450.uw.edu.groupprojectapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * SignInFragment
 *
 * This fragment class is used to display log-in screen
 * and send username and password to LoginActivity class.
 *
 * @author Jisu Shin
 * @version 1.0
 */
public class SignInFragment extends Fragment {

    /** Private OnFragmentInteractionListener for this class  */
    private OnFragmentInteractionListener mListener;

    /** The private object of EditText for username, password and confirming password  */
    private EditText name, pwd;

    /** The private string object for username and password from EditText  */
    private String userName, passWord;

    /**
     * Empty Constructor for SignIpFragment
     *
     */
    public SignInFragment() {
        // Required empty public constructor
    }

    /**
     * Method that creates a view for SignInFragment and return the view
     *
     * @param inflater The LayoutInflater for creating view
     * @param container The ViewGroup for creating view
     * @param savedInstanceState The state of saved instance
     * @return The view of the SignInFragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_signinfragment, container, false);

        name = (EditText) v.findViewById(R.id.username);
        pwd = (EditText) v.findViewById(R.id.password);

        Button b = (Button) v.findViewById(R.id.loginButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("")){
                    name.setError("Field cannot be left blank.");
                    return;
                }
                if(pwd.getText().toString().equals("")){
                    pwd.setError("Field cannot be left blank.");
                    return;
                }
                userName = name.getText().toString();
                passWord = pwd.getText().toString();
                onButtonPressed(userName, passWord);
            }
        });
        return v;
    }

    /**
     * Method that sends the purpose, username and password from this fragment to LoginActivity
     *
     * @param un The username from this fragment
     * @param pw The password from this fragment
     */
    public void onButtonPressed(String un, String pw) {
        if (mListener != null) {
            mListener.onFragmentInteraction("get", un, pw);
        }
    }

    /**
     * Method to attach the context before OnFragmentInteraction.
     *
     * @param context The context of the SignInFragment
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
     * Interface to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String purpose, String un, String pw);
    }
}
