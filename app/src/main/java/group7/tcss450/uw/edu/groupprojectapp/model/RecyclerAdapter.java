package group7.tcss450.uw.edu.groupprojectapp.model;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import group7.tcss450.uw.edu.groupprojectapp.ItemFragment;
import group7.tcss450.uw.edu.groupprojectapp.R;

/**
 * Created by Andy on 2017. 5. 26..
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private List<Item> mDataset;
    private FragmentActivity activity;

    public RecyclerAdapter(List<Item> searchDataSet, FragmentActivity act) {
        mDataset = searchDataSet;
        activity = act;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_result, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTextView.setText(mDataset.get(position).getTitle());
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = mDataset.get(position);
                Log.d("TEST", mDataset.get(position).getItemID());
                ItemFragment slf;
                slf = (ItemFragment) activity.getSupportFragmentManager().findFragmentById(R.id.datafrag);
                if (slf != null) {
                    Log.d("WHY", item.getPrice());
                    slf.updateContent(item);
                } else {
                    Log.d("WHAT", item.getPrice());
                    slf = new ItemFragment();
                    //fragment = (DataFragment) activity.getFragmentManager().findFragmentById(R.id.datafrag);
                    Bundle args = new Bundle();
                    args.putSerializable("item", item);
                    slf.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container, slf).addToBackStack(null).commit();

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("ACTIVITY", String.valueOf(mDataset.size()));
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.search_text);
        }
    }


}

