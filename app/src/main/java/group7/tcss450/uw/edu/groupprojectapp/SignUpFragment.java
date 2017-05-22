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
 * SignUpFragment
 *
 * This fragment class is used to display sign-up screen
 * and send username and password to LoginActivity class.
 *
 * @author Jisu Shin
 * @version 1.0
 */
public class SignUpFragment extends Fragment implements View.OnClickListener {

    /** Private OnFragmentInteractionListener for this class  */
    private OnFragmentInteractionListener mListener;

    /** The private object of EditText for username, password and confirming password  */
    private EditText name, pwd1, pwd2;

    /** The private string object for username and password from EditText  */
    private String userName, passWord;

    /**
     * Empty Constructor for SignUpFragment
     *
     */
    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Method that creates a view for SignUpFragment and return the view
     *
     * @param inflater The LayoutInflater for creating view
     * @param container The ViewGroup for creating view
     * @param savedInstanceState The state of saved instance
     * @return The view of the SignUpFragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_signupfragment, container, false);
        Button b = (Button) v.findViewById(R.id.signupButton);
        b.setOnClickListener(this);
        return v;
    }

    /**
     * Method to declare private EditText variables when the view is created.
     *
     * @param view The view of the SignUpFragment
     * @param savedInstanceState The bundle of the view
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        name=(EditText) view.findViewById(R.id.username1);
        pwd1=(EditText) view.findViewById(R.id.password1);
        pwd2=(EditText) view.findViewById(R.id.password2);
    }

    /**
     * Method to attach the context before OnFragmentInteraction.
     *
     * @param context The context of the SignUpFragment
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
     * Method to set onClick to sign-up button.
     *
     * @param view The view of the SignUpFragment
     */
    @Override
    public void onClick(View view) {

        // check if EditText for username is empty
        if(name.getText().toString().equals("")){
            name.setError("Field cannot be left blank.");
            return;
        }
        if(name.getText().toString().length() < 2 || name.getText().toString().length() > 8){
            name.setError("Should be 2-8 characters.");
            return;
        }
        // check if EditText for password is empty
        if(pwd1.getText().toString().equals("")){
            pwd1.setError("Field cannot be left blank.");
            return;
        }
        if(pwd1.getText().toString().length() < 5){
            pwd1.setError("Should be longer than 5 characters.");
            return;
        }
        // check if EditText for confirming password is empty
        if(pwd2.getText().toString().equals("")){
            pwd2.setError("Field cannot be left blank.");
            return;
        }
        // check if EditTexts for both password and confirming password matches
        if(!pwd2.getText().toString().equals(pwd1.getText().toString())){
            pwd2.setError("Passwords have to be matched.");
            return;
        }
        // get username and password from EditText
        userName = name.getText().toString();
        passWord = pwd1.getText().toString();

        if (mListener != null) {
            switch (view.getId()) {
                case R.id.signupButton:
                    mListener.onFragmentInteraction("post", userName, passWord);
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
        void onFragmentInteraction(String purpose, String un, String pw);
    }
}
