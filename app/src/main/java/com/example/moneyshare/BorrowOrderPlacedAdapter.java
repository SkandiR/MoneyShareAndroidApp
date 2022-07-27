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

public class BorrowOrderPlacedAdapter extends RecyclerView.Adapter<BorrowOrderPlacedAdapter.MyViewHolder>{
    private List<BorrowOrderPlacedModel> borrowOrderPlacedModelList = new ArrayList<>();

    public BorrowOrderPlacedAdapter(List<BorrowOrderPlacedModel> borrowOrderPlacedModelList) {
        this.borrowOrderPlacedModelList = borrowOrderPlacedModelList;
    }

    @NonNull
    @Override
    public BorrowOrderPlacedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.borrow_order_placed_rc_view_model, parent, false);
        return new BorrowOrderPlacedAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BorrowOrderPlacedAdapter.MyViewHolder holder, int position) {
        BorrowOrderPlacedModel item = borrowOrderPlacedModelList.get(position);
        holder.amount.setText(item.getAmount() + "");
        holder.ROI.setText(item.getPreferredRoI()+"");
    }

    @Override
    public int getItemCount() {
        return borrowOrderPlacedModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView amount, ROI;
        AppCompatButton cancel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            amount = itemView.findViewById(R.id.borrowOrderPlacedAmount);
            ROI = itemView.findViewById(R.id.borrowOrderPlacedRoI);

            cancel = itemView.findViewById(R.id.borrowOrderPlacedCancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "not functional at this moment " + getAdapterPosition(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
