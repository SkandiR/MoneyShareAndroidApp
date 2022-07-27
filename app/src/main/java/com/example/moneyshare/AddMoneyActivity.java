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

public class AddMoneyActivity extends AppCompatActivity implements
    View.OnClickListener {

    Button addMoney;
    EditText enteredAmount;

    String user_id;

    public Call<JsonData.addMoney> call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_id = extras.getString("user_id");
        }

        addMoney = findViewById(R.id.add_money_final);
        addMoney.setOnClickListener(this);

        enteredAmount = findViewById(R.id.add_money_entered_amount);

    }

    @Override
    public void onClick(View view) {
        // get money value and do post
        Integer add_amount = Integer.parseInt(enteredAmount.getText().toString());
        JsonData.addMoney addMoney = new JsonData.addMoney();
        addMoney.setAmount(Long.valueOf(add_amount));
        addMoney.setId(user_id);
        call = jsonPlaceHolderApi.addMoney(addMoney);

        call.enqueue(new Callback<JsonData.addMoney>() {
            @Override
            public void onResponse(Call<JsonData.addMoney> call, Response<JsonData.addMoney> response) {
                Toast.makeText(getApplicationContext(), "Added money to wallet" + add_amount, Toast.LENGTH_LONG).show();
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);
            }

            @Override
            public void onFailure(Call<JsonData.addMoney> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed adding money to wallet" + add_amount, Toast.LENGTH_LONG).show();
            }
        });

    }

}
