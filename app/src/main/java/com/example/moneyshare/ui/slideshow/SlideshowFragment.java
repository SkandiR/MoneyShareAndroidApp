package com.example.moneyshare.ui.slideshow;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.moneyshare.BorrowCompletedAdapter;
import com.example.moneyshare.BorrowCompletedModel;
import com.example.moneyshare.BorrowOrderPlacedModel;
import com.example.moneyshare.JsonData;
import com.example.moneyshare.LendCompletedAdapter;
import com.example.moneyshare.LendCompletedModel;
import com.example.moneyshare.LendOrderExecutedModel;
import com.example.moneyshare.R;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;

import java.util.List;

import static com.example.moneyshare.ui.JsonConnection.jsonPlaceHolderApi;

public class SlideshowFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Call<JsonData.BorrowRequests> call;
    String user_id;

    private FirebaseAuth mAuth;

    private List<BorrowCompletedModel> borrowCompletedModelList = new ArrayList<>();
    private BorrowCompletedAdapter mBorrowCompletedAdapter;

    View mSlideshowFragmentView=null;
    private SlideshowFragment.OnFragmentInteractionListener mListener;

    public SlideshowFragment() {
        // Required empty public constructor
    }

    public static SlideshowFragment newInstance(String param1, String param2) {
        SlideshowFragment fragment = new SlideshowFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Gallery : ","onCreate");
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

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerLendOrderCompleted);
        mBorrowCompletedAdapter = new BorrowCompletedAdapter(borrowCompletedModelList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mBorrowCompletedAdapter);

        borrowCompletedModelList.clear();
        prepareBorrowOrderPlacedData();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SlideshowFragment.OnFragmentInteractionListener) {
            mListener = (SlideshowFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
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
            mListener.onFragmentInteraction("Gallery");
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String st);
    }

    private void prepareBorrowOrderPlacedData() {
        call = jsonPlaceHolderApi.getBorrowRequest(user_id);
        call.enqueue(new Callback<JsonData.BorrowRequests>() {
            @Override
            public void onResponse(Call<JsonData.BorrowRequests> call, Response<JsonData.BorrowRequests> response) {
                JsonData.BorrowRequests borrowRequests = new JsonData.BorrowRequests();
                borrowRequests = response.body();
                if (borrowRequests != null && borrowRequests.borrowDetailsList != null) {
                    for (int i = 0; i < borrowRequests.borrowDetailsList.size(); i++) {
                        if (borrowRequests.borrowDetailsList.get(i).getStatus() == JsonData.BorrowStatus.COMPLETED) {
                            BorrowCompletedModel borrowOrderPlacedModel = new BorrowCompletedModel(
                                    borrowRequests.borrowDetailsList.get(i).getAmount(),
                                    borrowRequests.borrowDetailsList.get(i).getRoi(),
                                    borrowRequests.borrowDetailsList.get(i).getLentId()
                            );
                            borrowCompletedModelList.add(borrowOrderPlacedModel);
                        }
                    }
                    mBorrowCompletedAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonData.BorrowRequests> call, Throwable t) {

            }
        });

    }
}