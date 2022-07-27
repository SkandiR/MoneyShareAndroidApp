package com.example.moneyshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.moneyshare.ui.JsonConnection.jsonPlaceHolderApi;

public class BorrowOrderExecutedAdapter extends RecyclerView.Adapter<BorrowOrderExecutedAdapter.MyViewHolder>{
    private List<BorrowOrderExecutedModel> borrowOrderExecutedModelList = new ArrayList<>();
    public Call<JsonData.AcceptLendRequest> call;
    String user_id;
    private FirebaseAuth mAuth;

    public BorrowOrderExecutedAdapter(List<BorrowOrderExecutedModel> borrowOrderExecutedModelList) {
        this.borrowOrderExecutedModelList = borrowOrderExecutedModelList;
        this.mAuth = FirebaseAuth.getInstance();
        this.user_id = this.mAuth.getCurrentUser().getUid();
    }

    @NonNull
    @Override
    public BorrowOrderExecutedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.borrow_order_executed_rc_view_model, parent, false);
        return new BorrowOrderExecutedAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BorrowOrderExecutedAdapter.MyViewHolder holder, int position) {
        BorrowOrderExecutedModel item = borrowOrderExecutedModelList.get(position);
        holder.amount.setText(item.getAmount() + "");
        holder.ROI.setText(item.getPreferredRoI()+"");
    }

    @Override
    public int getItemCount() {
        return borrowOrderExecutedModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView amount, ROI;
        AppCompatButton settle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            amount = itemView.findViewById(R.id.borrowOrderExecutedAmount);
            ROI = itemView.findViewById(R.id.borrowOrderExecutedRoI);

            settle = itemView.findViewById(R.id.borrowOrderExecutedSettle);
            settle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JsonData.AcceptLendRequest acceptLendRequest = new JsonData.AcceptLendRequest(
                            borrowOrderExecutedModelList.get(getAdapterPosition()).getLender_id(),
                            borrowOrderExecutedModelList.get(getAdapterPosition()).getId()
                    );
                    call = jsonPlaceHolderApi.settleBorrowRequest(acceptLendRequest);

                    call.enqueue(new Callback<JsonData.AcceptLendRequest>() {
                        @Override
                        public void onResponse(Call<JsonData.AcceptLendRequest> call, Response<JsonData.AcceptLendRequest> response) {
                            Intent mainIntent = new Intent(view.getContext(), MainActivity.class);
                            Bundle extraData = new Bundle();
                            extraData.putString("user_id", user_id);
                            extraData.putString("first", "Yes");
                            mainIntent.putExtras(extraData);
                            view.getContext().startActivity(mainIntent);
                        }

                        @Override
                        public void onFailure(Call<JsonData.AcceptLendRequest> call, Throwable t) {

                        }
                    });
                    // Toast.makeText(view.getContext(), "not functional at this moment " + getAdapterPosition(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
