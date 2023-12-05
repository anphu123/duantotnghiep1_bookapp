package com.example.duan1bookapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.duan1bookapp.Constants;
import com.example.duan1bookapp.R;
import com.example.duan1bookapp.activities.LoginActivity;
import com.example.duan1bookapp.activities.ProfileEditActivity;
import com.example.duan1bookapp.models.Customer;
import com.example.duan1bookapp.models.CustomerDataManager;
import com.google.android.material.imageview.ShapeableImageView;

public class ProfileFragment extends Fragment {
    private View view;
    private CustomerDataManager customerDataManager;
    private ImageView imageButtonSetting,imageButtonLogout;
    private ShapeableImageView shapeableImageView;
    private TextView textView1;
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        imageButtonSetting = view.findViewById(R.id.customer_information_update_btn);
        imageButtonLogout = view.findViewById(R.id.customer_information_logout_btn);
        shapeableImageView = view.findViewById(R.id.image_customer_profle_fragment);
        textView1 = view.findViewById(R.id.fragment_customer_tv_name);


        customerDataManager = new CustomerDataManager(getActivity());
        Customer customer = customerDataManager.getUser();
        if(!customer.getAvatar_url().isEmpty()){
            Glide.with(getActivity()).load(Constants.URL_IMAGE+customer.getAvatar_url()).timeout(500).into(shapeableImageView);
        }
        textView1.setText(customer.getCustomerName());
        imageButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileEditActivity.class);
                startActivity(intent);
            }
        });
        imageButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Hen gap lai ban sau !", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        customerDataManager.saveUser(new Customer());
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                }, 1000);
            }
        });
        return view;
    }


}
