package com.example.moneyshare;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LendOrderPlacedAdapter extends RecyclerView.Adapter<LendOrderPlacedAdapter.MyViewHolder>{
    private List<LendOrderPlacedModel> lendOrderPlacedList = new ArrayList<>();

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView lendOrderPlacedAmount, lendOrderPlacedPreferredCreditScore, lendOrderPlacedOfferedROI;
        ListView lendOrderInterestedListView;
        CustomListViewAdapter adapter;
        List<JsonData.BorrowDetails> borrowLists;
        String lender_id;

        MyViewHolder(View view) {
            super(view);
            lendOrderPlacedAmount = view.findViewById(R.id.lendOrderPlacedAmount);
            lendOrderPlacedPreferredCreditScore = view.findViewById(R.id.lendOrderPlacedPreferredCreditScore);
            lendOrderPlacedOfferedROI = view.findViewById(R.id.lendOrderPlacedOfferedROI);
            lendOrderInterestedListView = view.findViewById(R.id.lendOrderInterestedListView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (lendOrderInterestedListView.getVisibility() == View.GONE) {
                        lendOrderInterestedListView.setVisibility(View.VISIBLE);
                    } else {
                        lendOrderInterestedListView.setVisibility(View.GONE);
                    }
                }
            });
            lendOrderInterestedListView.setOnTouchListener(new ListView.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            // Disallow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(true);
                            break;

                        case MotionEvent.ACTION_UP:
                            // Allow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }

                    // Handle ListView touch events.
                    v.onTouchEvent(event);
                    return true;
                }
            });
        }
    }

    public LendOrderPlacedAdapter(List<LendOrderPlacedModel> lendOrderPlacedList) {
        this.lendOrderPlacedList = lendOrderPlacedList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lend_order_placed_rc_view_model, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LendOrderPlacedModel item = lendOrderPlacedList.get(position);
        holder.lendOrderPlacedAmount.setText(item.getAmount() + "");
        holder.lendOrderPlacedPreferredCreditScore.setText(item.getPreferredCreditScore() + "");
        holder.lendOrderPlacedOfferedROI.setText(item.getOfferedROI()+"");
        holder.lender_id = item.getLender_id();
        holder.borrowLists = item.getBorrowLists();
        //holder.adapter = new ArrayAdapter<String>(holder.itemView.getContext(), R.layout.lend_order_interested_list_view, holder.mobileArray);
        holder.adapter = new CustomListViewAdapter(holder.itemView.getContext(), holder.borrowLists, holder.lender_id);
        holder.lendOrderInterestedListView.setAdapter(holder.adapter);

    }

    @Override
    public int getItemCount() {
        return lendOrderPlacedList.size();
    }
}
