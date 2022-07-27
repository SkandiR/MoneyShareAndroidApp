package com.example.moneyshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.moneyshare.ui.JsonConnection.jsonPlaceHolderApi;

public class BorrowRequestInputActivity extends AppCompatActivity implements
        View.OnClickListener{
    EditText borrowRequestInputAmount;
    EditText borrowRequestInputRoI;
    Button borrowRequestInputSubmit;

    public Call<JsonData.BorrowRequest> call;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrow_request_input_layout);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");

        borrowRequestInputAmount = findViewById(R.id.borrow_request_input_amount);
        borrowRequestInputRoI = findViewById(R.id.borrow_request_input_roi);

        borrowRequestInputSubmit = findViewById(R.id.borrow_request_input_submit);
        borrowRequestInputSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Integer amount = Integer.parseInt(borrowRequestInputAmount.getText().toString());
        Integer roi = Integer.parseInt(borrowRequestInputRoI.getText().toString());

        JsonData.BorrowRequest borrowRequest = new JsonData.BorrowRequest();
        borrowRequest.setAmount(Long.valueOf(amount));
        borrowRequest.setRoi(Double.valueOf(roi));
        borrowRequest.setUserId(user_id);
        borrowRequest.setMinCreditScore(0.0);

        call = jsonPlaceHolderApi.addBorrowRequest(borrowRequest);
        call.enqueue(new Callback<JsonData.BorrowRequest>() {
            @Override
            public void onResponse(Call<JsonData.BorrowRequest> call, Response<JsonData.BorrowRequest> response) {
                Toast.makeText(getApplicationContext(), "Successfully added borrow request for amount" + amount + " Roi " + roi , Toast.LENGTH_LONG).show();
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainIntent);
            }

            @Override
            public void onFailure(Call<JsonData.BorrowRequest> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed borrow request for amount" + amount + " Roi " + roi, Toast.LENGTH_LONG).show();
            }
        });
    }
}
