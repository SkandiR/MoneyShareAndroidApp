package com.example.moneyshare;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import static com.example.moneyshare.ui.JsonConnection.jsonPlaceHolderApi;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LendOrderPlacedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LendOrderPlacedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Call<JsonData.lentDetailsList> call;
    String user_id;

    private FirebaseAuth mAuth;

    private List<LendOrderPlacedModel> lendOrderPlacedList = new ArrayList<>();
    private LendOrderPlacedAdapter mLendOrderPlacedAdapter;

    public LendOrderPlacedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LendOrderPlacedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LendOrderPlacedFragment newInstance(String param1, String param2) {
        LendOrderPlacedFragment fragment = new LendOrderPlacedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lend_order_placed, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewLendPlaced);
        mLendOrderPlacedAdapter = new LendOrderPlacedAdapter(lendOrderPlacedList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mLendOrderPlacedAdapter);

        // insert data here
        prepareLendOrderPlacedData();

        return view;
    }

    private void prepareLendOrderPlacedData() {
        call = jsonPlaceHolderApi.getLentRequests(user_id);
        call.enqueue(new Callback<JsonData.lentDetailsList>() {
            @Override
            public void onResponse(Call<JsonData.lentDetailsList> call, Response<JsonData.lentDetailsList> response) {
                JsonData.lentDetailsList lentDetailsList = new JsonData.lentDetailsList();
                lentDetailsList = response.body();

                if (lentDetailsList != null && lentDetailsList.lentDetailsList != null) {
                    for (int i=0; i < lentDetailsList.lentDetailsList.size(); i++) {
                        if (lentDetailsList.lentDetailsList.get(i).getStatus() == JsonData.LentStatus.PENDING) {
                            LendOrderPlacedModel item = new LendOrderPlacedModel(lentDetailsList.lentDetailsList.get(i).getAmount(),
                                                        lentDetailsList.lentDetailsList.get(i).getRoi(),
                                                        lentDetailsList.lentDetailsList.get(i).getCreditScore(),
                                                        lentDetailsList.lentDetailsList.get(i).getBorrowLists(),
                                                        lentDetailsList.lentDetailsList.get(i).getId());
                            lendOrderPlacedList.add(item);
                        }
                    }
                    mLendOrderPlacedAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonData.lentDetailsList> call, Throwable t) {

            }
        });
    }
}