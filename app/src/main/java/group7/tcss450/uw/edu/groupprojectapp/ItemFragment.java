package group7.tcss450.uw.edu.groupprojectapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import group7.tcss450.uw.edu.groupprojectapp.R;
import group7.tcss450.uw.edu.groupprojectapp.model.Item;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends Fragment {

    public ItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item, container, false);
    }

    public void updateContent(Item item) {
        TextView tv = (TextView) getActivity().findViewById(R.id.dataview);
//        tv.setText("Item: " + item.getTitle() + "\n" + "Price: " + item.getPrice() + "\n" +
//                "" + item.getitem.getItemURL());
        tv.setText(item.toString());
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getArguments() != null) {
            Item item = (Item) getArguments().getSerializable("item");
            updateContent(item);
        }
    }


}
