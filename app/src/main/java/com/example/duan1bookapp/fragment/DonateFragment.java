package com.example.duan1bookapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.EditText;

import com.example.duan1bookapp.R;
import com.example.duan1bookapp.activities.PaymentActivity;
import com.example.duan1bookapp.models.Order;
import com.example.duan1bookapp.retrofit.OrderApi;
import com.example.duan1bookapp.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonateFragment extends Fragment {


    RetrofitService retrofitService = new RetrofitService();
    public int userID;

    private EditText edtCoin;
    private RadioGroup radioGroup;
    private Button coin_btn1;
    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coin, container, false);
        edtCoin = view.findViewById(R.id.edtcoin);
        radioGroup = view.findViewById(R.id.Gradiocoin);

        edtCoin.setVisibility(View.INVISIBLE);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = view.findViewById(checkedId);
                String selectedOption = selectedRadioButton.getText().toString();
                showEditText(selectedOption);
            }
        });
        coin_btn1 = (Button) view.findViewById(R.id.coin_btn1);
        coin_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = new Order(false,"nap10k",userID);
                CreateOrder(order);
            }
        });
        return view;
    }

    private void showEditText(String selectedOption) {
        edtCoin.setVisibility(View.VISIBLE);
        edtCoin.setHint("Số tiền muốn nạp (" + selectedOption + ")");
    }
    private void CreateOrder(Order order) {
        OrderApi orderApi =  retrofitService.getRetrofit().create(OrderApi.class);
        orderApi.CreateOrder(order).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                intent.putExtra("VALUE_I_NEED_ORDER_ID",String.valueOf(response.body().getId()));
                intent.putExtra("VALUE_I_NEED_USER_ID",response.body().getCustomer_id());
                intent.putExtra("VALUE_I_NEED_ORDER_AMOUNT",10000);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.d("Tao OrDer that bai", ""+ t);
            }
        });

    }

}
