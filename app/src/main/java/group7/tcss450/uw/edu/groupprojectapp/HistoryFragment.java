/**
 * TCSS 450 Mobile Programming
 * Project PhaseI Group 7
 *
 * @author Jisu Shin, Ryan Roe
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

public class HistoryFragment extends Fragment {

    private TextView mTV;
    private LinearLayout mView;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = (LinearLayout) inflater.inflate(R.layout.fragment_history, container, false);
        try {
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
