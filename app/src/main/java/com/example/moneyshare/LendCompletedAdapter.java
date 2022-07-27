package com.example.moneyshare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

public class LendCompletedAdapter extends RecyclerView.Adapter<LendCompletedAdapter.MyViewHolder>{
    private List<LendCompletedModel> lendCompletedModelList = new ArrayList<>();

    public LendCompletedAdapter(List<LendCompletedModel> lendCompletedModelList) {
        this.lendCompletedModelList = lendCompletedModelList;
    }

    @NonNull
    @Override
    public LendCompletedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lend_completed_rc_view_model, parent, false);
        return new LendCompletedAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LendCompletedAdapter.MyViewHolder holder, int position) {
        LendCompletedModel item = lendCompletedModelList.get(position);
        holder.amount.setText(item.getAmount() + "");
        holder.ROI.setText(item.getOfferedROI()+"");
        holder.user.setText(item.getUser_id());
    }

    @Override
    public int getItemCount() {
        return lendCompletedModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView amount, ROI, user;
        AppCompatButton cancel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            amount = itemView.findViewById(R.id.lendOrderCompletedAmount);
            ROI = itemView.findViewById(R.id.lendOrderCompletedOfferedROI);
            user = itemView.findViewById(R.id.lendOrderCompletedUser);


        }
    }
}
