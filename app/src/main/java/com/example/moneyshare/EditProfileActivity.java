package com.example.moneyshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moneyshare.ui.JsonConnection;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    private Button saveProfileButton;
    private EditText user_name;
    private EditText credit_score;
    private EditText ssn;

    public Call<JsonData.SaveUser> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user_id");

        Toast.makeText(this, "user id ffff" + user_id, Toast.LENGTH_LONG).show();

        user_name = findViewById(R.id.user_name_input);
        credit_score = findViewById(R.id.user_credit_score_input);
        ssn = findViewById(R.id.user_ssn_input);

        saveProfileButton = (Button) findViewById(R.id.button_save_profile);

        JsonPlaceHolderApi jsonPlaceHolderApi = new JsonConnection().getJsonPlaceHolderApi();


        saveProfileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String name = user_name.getText().toString();
                String ssn_inp = ssn.getText().toString();
                double creditScore_inp = Double.parseDouble(credit_score.getText().toString());

                JsonData.SaveUser saveUser = new JsonData.SaveUser(user_id, name, ssn_inp, creditScore_inp);

                call = jsonPlaceHolderApi.saveUser(saveUser);

                call.enqueue(new Callback<JsonData.SaveUser>() {
                    @Override
                    public void onResponse(Call<JsonData.SaveUser> call, Response<JsonData.SaveUser> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "User registered successfuly", Toast.LENGTH_LONG).show();
                            Intent mainIntent = new Intent(EditProfileActivity.this, MainActivity.class);
                            Bundle extraData = new Bundle();
                            extraData.putString("user_id",user_id);
                            extraData.putString("first","Yes");
                            mainIntent.putExtras(extraData);
                            startActivity(mainIntent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Cannot register. Please try again. " + response.errorBody(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonData.SaveUser> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Cannot register. Please try again. "  + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
