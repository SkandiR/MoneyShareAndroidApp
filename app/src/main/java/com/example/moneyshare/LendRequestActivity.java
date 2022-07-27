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

public class LendRequestActivity extends AppCompatActivity implements
        View.OnClickListener
{
    Button lendRequestSubmit;
    EditText lendRequestAmount;
    EditText lendRequestRoI;
    EditText lendRequestCreditScore;

    public Call<JsonData.LendRequest> call;
    String user_id;
    Long wallet_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend_request);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        wallet_amount = intent.getLongExtra("wallet_balance", 0);

        lendRequestAmount = findViewById(R.id.lend_activity_entered_amount);
        lendRequestRoI = findViewById(R.id.lend_activity_preferred_roi);
        lendRequestCreditScore = findViewById(R.id.lend_activity_preferred_credit_score);

        lendRequestSubmit = findViewById(R.id.submit_lend_request);
        lendRequestSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Integer amount = Integer.parseInt(lendRequestAmount.getText().toString());
        Integer roi = Integer.parseInt(lendRequestRoI.getText().toString());
        Integer creditscore = Integer.parseInt(lendRequestCreditScore.getText().toString());

        Toast.makeText(this, "Submitting lend request for amount" + amount + " Roi " + roi + " creditscore " + creditscore, Toast.LENGTH_LONG).show();

        JsonData.LendRequest lendRequest = new JsonData.LendRequest();
        lendRequest.setAmount(Long.valueOf(amount));
        lendRequest.setRoi(Double.valueOf(roi));
        lendRequest.setMinCreditScore(Double.valueOf(creditscore));
        lendRequest.setUserId(user_id);
        if (wallet_amount < Long.valueOf(amount)) {
            Toast.makeText(this, "Insufficient balance!", Toast.LENGTH_LONG).show();
            return;
        }
        call = jsonPlaceHolderApi.addLendRequest(lendRequest);
        call.enqueue(new Callback<JsonData.LendRequest>() {
            @Override
            public void onResponse(Call<JsonData.LendRequest> call, Response<JsonData.LendRequest> response) {
                Toast.makeText(getApplicationContext(), "Succefully added lend request for amount" + amount + " Roi " + roi + " creditscore " + creditscore, Toast.LENGTH_LONG).show();
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);
            }

            @Override
            public void onFailure(Call<JsonData.LendRequest> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed lend request for amount" + amount + " Roi " + roi + " creditscore " + creditscore, Toast.LENGTH_LONG).show();
            }
        });


    }
}
