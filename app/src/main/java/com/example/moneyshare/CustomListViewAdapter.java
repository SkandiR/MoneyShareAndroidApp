package com.example.moneyshare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import androidx.appcompat.widget.AppCompatButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.moneyshare.ui.JsonConnection.jsonPlaceHolderApi;

public class CustomListViewAdapter extends BaseAdapter {

    List<JsonData.BorrowDetails> borrowLists;
    String lender_id;
    private Context mContext;
    private static LayoutInflater inflater = null;

    public Call<JsonData.AcceptLendRequest> call;
    String user_id;

    private FirebaseAuth mAuth;

    public CustomListViewAdapter(Context context, List<JsonData.BorrowDetails> borrowLists, String lender_id){
        this.borrowLists = borrowLists;
        mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mAuth = FirebaseAuth.getInstance();
        this.user_id = this.mAuth.getCurrentUser().getUid();
        this.lender_id = lender_id;
    }

    public int getCount() {
        return borrowLists.size();
    }

    public JsonData.BorrowDetails getItem(int position) {
        return borrowLists.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.lend_order_interested_list_view, parent, false);

        TextView tx1 = vi.findViewById(R.id.lendOrderInterestedUserName);
        TextView tx2 = vi.findViewById(R.id.lendOrderInterestedProposedROI);
        TextView tx3 = vi.findViewById(R.id.lendOrderInterestedUserCreditScore);

        JsonData.BorrowDetails borrowDetails = borrowLists.get(position);
        tx1.setText(borrowDetails.getUserId());
        tx2.setText(borrowDetails.getRoi().toString());
        tx3.setText(borrowDetails.getCreditScore().toString());

        AppCompatButton lendOrderInterestedApprove = vi.findViewById(R.id.lendOrderInterestedApprove);
        lendOrderInterestedApprove.setTag(position);
        lendOrderInterestedApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonData.AcceptLendRequest acceptLendRequest = new JsonData.AcceptLendRequest(
                        lender_id,
                        borrowDetails.getId()
                );
                call = jsonPlaceHolderApi.acceptLentRequest(acceptLendRequest);

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
            }
        });

        return vi;
    }
}