package com.example.duan1bookapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.duan1bookapp.databinding.ActivityRegisterBinding;
import com.example.duan1bookapp.models.Coin;
import com.example.duan1bookapp.models.Customer;
import com.example.duan1bookapp.retrofit.CustomerApi;
import com.example.duan1bookapp.retrofit.RetrofitService;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    private ProgressDialog progressDialog;
    RetrofitService retrofitService = new RetrofitService();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // init firebase auth

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // handle click, begin register
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    private String customerName, customereMail, customerPassword ;
    private String customerbirthDate = "1999-01-01";

    private void validateData() {
        // get data
        customerName = binding.nameEt.getText().toString();
        customereMail = binding.emailEt.getText().toString();
        customerPassword = binding.passwordEt.getText().toString();
        String cPassword = binding.cPasswordEt.getText().toString();


        // validate data
        if (TextUtils.isEmpty(customerName)) {
            Toast.makeText(this, "Enter your name...", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(customereMail).matches()) {
            Toast.makeText(this, "Invalid email pattern...!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(customerPassword)) {
            Toast.makeText(this, "Enter password...", Toast.LENGTH_SHORT).show();
//        } else if (TextUtils.isEmpty(cPassword)) {
//            Toast.makeText(this, "Confirm password...", Toast.LENGTH_SHORT).show();
//        } else if (!password.equals(cPassword)) {
//            Toast.makeText(this, "Password doesn't match...", Toast.LENGTH_SHORT).show();
        } else {
            Coin coin = new Coin(0);
            Customer customer = new Customer(customerName,customerPassword,customereMail,customerbirthDate,coin,true);
            CustomerApi customerApi = retrofitService.getRetrofit().create(CustomerApi.class);
            customerApi.save(customer)
                    .enqueue(new Callback<Customer>() {
                        @Override
                        public void onResponse(Call<Customer> call, Response<Customer> response) {
                            Toast.makeText(getApplicationContext(), "Save successful!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Customer> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Save failded!", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(RegisterActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                        }
                    });

        }
    }



}
