package com.example.moneyshare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

public class LendOrderExecutedAdapter extends RecyclerView.Adapter<LendOrderExecutedAdapter.MyViewHolder>{
    private List<LendOrderExecutedModel> lendOrderExecutedList = new ArrayList<>();

    public LendOrderExecutedAdapter(List<LendOrderExecutedModel> lendOrderExecutedList) {
        this.lendOrderExecutedList = lendOrderExecutedList;
    }

    @NonNull
    @Override
    public LendOrderExecutedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lend_order_executed_rc_view_model, parent, false);
        return new LendOrderExecutedAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LendOrderExecutedAdapter.MyViewHolder holder, int position) {
        LendOrderExecutedModel item = lendOrderExecutedList.get(position);
        holder.amount.setText(item.getAmount() + "");
        holder.credit_Score.setText(item.getCredit_score()+"");
        holder.ROI.setText(item.getRoI()+"");
        holder.user_name.setText(item.getUser_name()+"");
    }

    @Override
    public int getItemCount() {
        return lendOrderExecutedList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView user_name, amount, ROI, credit_Score;
        AppCompatButton notify;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.lendOrderExecutedUserName);
            amount = itemView.findViewById(R.id.lendOrderExecutedAmount);
            ROI = itemView.findViewById(R.id.lendOrderExecutedROI);
            credit_Score = itemView.findViewById(R.id.lendOrderExecutedUserCreditScore);

            notify = itemView.findViewById(R.id.lendOrderExecutedNotify);
            notify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "clicked " + getAdapterPosition(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
