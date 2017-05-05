package group7.tcss450.uw.edu.groupprojectapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class SignUpFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private EditText name, pwd1, pwd2;
    private String userName, passWord;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_signupfragment, container, false);
        Button b = (Button) v.findViewById(R.id.signupButton);
        b.setOnClickListener(this);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        name=(EditText) view.findViewById(R.id.username1);
        pwd1=(EditText) view.findViewById(R.id.password1);
        pwd2=(EditText) view.findViewById(R.id.password2);
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

    @Override
    public void onClick(View view) {
        if(name.getText().toString().equals("")){
            name.setError("Field cannot be left blank.");
            return;
        }
        if(pwd1.getText().toString().equals("")){
            pwd1.setError("Field cannot be left blank.");
            return;
        }
        if(pwd2.getText().toString().equals("")){
            pwd2.setError("Field cannot be left blank.");
            return;
        }
        if(!pwd2.getText().toString().equals(pwd1.getText().toString())){
            pwd2.setError("Passwords have to be matched.");
            return;
        }
        userName = name.getText().toString();
        passWord = pwd1.getText().toString();
        if (mListener != null) {
            switch (view.getId()) {
                case R.id.signupButton:
                    mListener.onFragmentInteraction("display2", userName, passWord);
                    break;
            }
        }
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
