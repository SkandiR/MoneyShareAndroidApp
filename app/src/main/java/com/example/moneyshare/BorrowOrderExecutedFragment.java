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
 * Use the {@link BorrowOrderExecutedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BorrowOrderExecutedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Call<JsonData.BorrowRequests> call;
    String user_id;

    private FirebaseAuth mAuth;

    private List<BorrowOrderExecutedModel> borrowOrderExecutedModelList = new ArrayList<>();
    private BorrowOrderExecutedAdapter mBorrowOrderExecutedAdapter;

    public BorrowOrderExecutedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BorrowOrderExecutedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BorrowOrderExecutedFragment newInstance(String param1, String param2) {
        BorrowOrderExecutedFragment fragment = new BorrowOrderExecutedFragment();
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
        View view = inflater.inflate(R.layout.fragment_borrow_order_executed, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerBorrowOrderExecuted);
        mBorrowOrderExecutedAdapter = new BorrowOrderExecutedAdapter(borrowOrderExecutedModelList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mBorrowOrderExecutedAdapter);

        prepareBorrowExecutedData();

        return view;
    }
    private void prepareBorrowExecutedData() {
        call = jsonPlaceHolderApi.getBorrowRequest(user_id);
        call.enqueue(new Callback<JsonData.BorrowRequests>() {
            @Override
            public void onResponse(Call<JsonData.BorrowRequests> call, Response<JsonData.BorrowRequests> response) {
                JsonData.BorrowRequests borrowRequests = new JsonData.BorrowRequests();
                borrowRequests = response.body();
                if (borrowRequests != null && borrowRequests.borrowDetailsList != null) {
                    for (int i = 0; i < borrowRequests.borrowDetailsList.size(); i++) {
                        if (borrowRequests.borrowDetailsList.get(i).getStatus() == JsonData.BorrowStatus.EXECUTED) {
                            BorrowOrderExecutedModel borrowOrderExecutedModel = new BorrowOrderExecutedModel(
                                    borrowRequests.borrowDetailsList.get(i).getAmount(),
                                    borrowRequests.borrowDetailsList.get(i).getRoi(),
                                    borrowRequests.borrowDetailsList.get(i).getLentId(),
                                    borrowRequests.borrowDetailsList.get(i).getId()
                            );
                            borrowOrderExecutedModelList.add(borrowOrderExecutedModel);
                        }
                    }
                    mBorrowOrderExecutedAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonData.BorrowRequests> call, Throwable t) {

            }
        });
    }
}