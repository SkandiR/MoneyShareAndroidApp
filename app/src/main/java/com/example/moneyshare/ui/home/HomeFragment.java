package com.example.moneyshare.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneyshare.AddMoneyActivity;
import com.example.moneyshare.BorrowActivity;
import com.example.moneyshare.BorrowRequestInputActivity;
import com.example.moneyshare.JsonData;
import com.example.moneyshare.LendRequestActivity;
import com.example.moneyshare.LentActivity;
import com.example.moneyshare.R;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.moneyshare.ui.JsonConnection.jsonPlaceHolderApi;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String user_id;
    private String mParam2;

    View mHomeFragmentView=null;

    private FirebaseAuth mAuth;

    private OnFragmentInteractionListener mListener;

    // lent and borrow buttons
    CardView lent_btn;
    CardView borrowed_btn;
    CardView add_money;

    AppCompatButton lend_request;
    AppCompatButton borrow_request;

    TextView wallet_balance;
    TextView lent_amount;
    TextView borrowed_amount;

    Long wallet_balance_long = 100000000L;

    public Call<JsonData.WalletResponse> call;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Home : ","onCreate");
        if (getArguments() != null) {
            user_id = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("OnCreateView","Called");
        if(mHomeFragmentView==null)
            mHomeFragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment

        lent_btn = mHomeFragmentView.findViewById(R.id.lent_clickable);
        lent_btn.setOnClickListener((View.OnClickListener) this);

        borrowed_btn = mHomeFragmentView.findViewById(R.id.borrowed_clickable);
        borrowed_btn.setOnClickListener((View.OnClickListener) this);

        add_money = mHomeFragmentView.findViewById(R.id.add_money);
        add_money.setOnClickListener((View.OnClickListener) this);

        lend_request = mHomeFragmentView.findViewById(R.id.lend_request_button);
        lend_request.setOnClickListener((View.OnClickListener) this);

        borrow_request = mHomeFragmentView.findViewById(R.id.borrow_request_button);
        borrow_request.setOnClickListener((View.OnClickListener) this);

        wallet_balance = mHomeFragmentView.findViewById(R.id.wallet_balance);
        lent_amount = mHomeFragmentView.findViewById(R.id.lent_amount);
        borrowed_amount = mHomeFragmentView.findViewById(R.id.borrowed_amount);

        return mHomeFragmentView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("OnViewCreated","Called");

        if(mListener!=null){
            mListener.onFragmentInteraction("Home");
        }

        call = jsonPlaceHolderApi.getWalletAmount(user_id);
        call.enqueue(new Callback<JsonData.WalletResponse>() {
            @Override
            public void onResponse(Call<JsonData.WalletResponse> call, Response<JsonData.WalletResponse> response) {
                JsonData.WalletResponse walletDetails = response.body();
                if (walletDetails == null) {
                    Toast.makeText(getContext(), "Error fetching details", Toast.LENGTH_LONG).show();
                } else {
                    if (walletDetails.getTotalAmount() != null) {
                        wallet_balance.setText(walletDetails.getTotalAmount().toString()+"$");
                        wallet_balance_long = walletDetails.getTotalAmount();
                    } else {
                        wallet_balance.setText("0");
                    }
                    if (walletDetails.getLentAmount() != null) {
                        lent_amount.setText(walletDetails.getLentAmount().toString()+"$");
                    } else {
                        lent_amount.setText("0");
                    }
                    if (walletDetails.getBorrowedAmount() != null) {
                        borrowed_amount.setText(walletDetails.getBorrowedAmount().toString()+"$");
                    } else {
                        borrowed_amount.setText("0");
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonData.WalletResponse> call, Throwable t) {

            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String st);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lent_clickable:
                Intent lentIntent = new Intent(view.getContext(), LentActivity.class);
                Bundle extraData = new Bundle();
                extraData.putString("user_id",user_id);
                lentIntent.putExtras(extraData);
                startActivity(lentIntent);
                break;
            case R.id.borrowed_clickable:
                Intent borrowedIntent = new Intent(view.getContext(), BorrowActivity.class);
                Bundle extraDataBrwd = new Bundle();
                extraDataBrwd.putString("user_id",user_id);
                borrowedIntent.putExtras(extraDataBrwd);
                startActivity(borrowedIntent);
                break;
            case R.id.lend_request_button:
                Intent lendRequestIntent = new Intent(view.getContext(), LendRequestActivity.class);
                Bundle extraDatalend = new Bundle();
                extraDatalend.putString("user_id",user_id);
                extraDatalend.putLong("wallet_balance", wallet_balance_long);
                lendRequestIntent.putExtras(extraDatalend);
                startActivity(lendRequestIntent);
                break;
            case R.id.borrow_request_button:
                Intent borrowRequestIntent = new Intent(view.getContext(), BorrowRequestInputActivity.class);
                Bundle extraDatabrw = new Bundle();
                extraDatabrw.putString("user_id",user_id);
                borrowRequestIntent.putExtras(extraDatabrw);
                startActivity(borrowRequestIntent);
                break;
            case R.id.add_money:
                //show_add_money_popup();
                Intent addMoneyIntent = new Intent(view.getContext(), AddMoneyActivity.class);
                Bundle extraDataaddm = new Bundle();
                extraDataaddm.putString("user_id",user_id);
                addMoneyIntent.putExtras(extraDataaddm);
                startActivity(addMoneyIntent);
                break;
        }
    }
}