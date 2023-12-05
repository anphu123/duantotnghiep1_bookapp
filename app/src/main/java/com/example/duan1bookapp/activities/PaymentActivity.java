package com.example.duan1bookapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1bookapp.R;
import com.example.duan1bookapp.models.Coin;
import com.example.duan1bookapp.models.Order;
import com.example.duan1bookapp.retrofit.OrderApi;
import com.example.duan1bookapp.retrofit.RetrofitService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.momo.momo_partner.AppMoMoLib;

public class PaymentActivity extends AppCompatActivity {
    RetrofitService retrofitService = new RetrofitService();

    private int amount ;
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "HoangNgoc";
    private String merchantCode = "MOMOC2IC20220510";
    private String merchantNameLabel = "Nhà cung cấp";
    private String description = "Thanh toán dịch vụ ABC";
    private String orderid;
    private int userid;

    private Button momo2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Bundle extras = getIntent().getExtras();
        orderid = extras.getString("VALUE_I_NEED_ORDER_ID");
        amount = extras.getInt("VALUE_I_NEED_ORDER_AMOUNT");
        userid = extras.getInt("VALUE_I_NEED_USER_ID");
        momo2 = findViewById(R.id.momo_btn2);
        momo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPayment(orderid);
            }
        });

    }

    private void requestPayment(String orderid) {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);

        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", amount); //Kiểu integer
        eventValue.put("orderId", orderid); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", "Mã đơn hàng"); //gán nhãn
        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
        eventValue.put("fee", "0"); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        //Example extra data
        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put("extraData", objExtraData.toString());

        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);


    }
    //Get token callback from MoMo app an submit to server side
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    Log.d("momo",data.getStringExtra("message"));
                    String token = data.getStringExtra("data"); //Token response
                    Log.d("momo-token",token);
                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if(env == null){
                        env = "app";

                    if(token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process PaymentActivity with MoMo server
                        verify(Integer.valueOf(orderid),token);

                        // IF Momo topup success, continue to process your order
                    } else {
                        Log.d("momo",data.getStringExtra("khong thanh cong"));
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                    Log.d("momo",data.getStringExtra("khong thanh cong"));
                } else if(data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                    Log.d("momo",data.getStringExtra("khong thanh cong"));
                } else {
                    //TOKEN FAIL
                    Log.d("momo",data.getStringExtra("khong thanh cong"));                }
            } else {
                Log.d("momo",data.getStringExtra("khong thanh cong"));
            }
        } else {
            Log.d("momo",data.getStringExtra("khong thanh cong"));
        }
    }
}

    private void verify(int id,String momo){
        OrderApi orderApi =  retrofitService.getRetrofit().create(OrderApi.class);
        orderApi.verify(momo, id).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if(!response.isSuccessful()){
                    return;
                }
                orderApi.addcoin(userid,amount).enqueue(new Callback<Coin>() {
                    @Override
                    public void onResponse(Call<Coin> call, Response<Coin> response) {
                        if(!response.isSuccessful()){
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<Coin> call, Throwable t) {

                    }
                });


            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {


            }
        });

    }
}