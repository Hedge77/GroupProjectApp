package group7.tcss450.uw.edu.groupprojectapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SignInFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private EditText name, pwd;
    private String userName, passWord;

    public SignInFragment() {
        // Required empty public constructor
    }

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

    public void onButtonPressed(String un, String pw) {
        if (mListener != null) {
            Log.d("ACTIVITY", "Red: " + un);
            mListener.onFragmentInteraction("display1", un, pw);
        }
    }

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String fragment, String un, String pw);
    }
}
