package com.example.duan1bookapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.duan1bookapp.R;
import com.example.duan1bookapp.databinding.ActivityDashBoardBinding;
import com.example.duan1bookapp.fragment.CategoryFragment;
import com.example.duan1bookapp.fragment.DonateFragment;
import com.example.duan1bookapp.fragment.FavoriteFragment;
import com.example.duan1bookapp.fragment.HomeFragment;
import com.example.duan1bookapp.fragment.ProfileFragment;
import com.example.duan1bookapp.models.Customer;

public class DashBoard extends AppCompatActivity {

    ActivityDashBoardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_ic_home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.nav_ic_manga) {
                replaceFragment(new CategoryFragment());
            } else if (item.getItemId() == R.id.nav_ic_comment) {
                replaceFragment(new FavoriteFragment());
            } else if (item.getItemId() == R.id.nav_ic_buy_coin) {
                DonateFragment coinFragment = new DonateFragment();
                replaceFragment(coinFragment);
            }else if (item.getItemId() == R.id.nav_ic_user) {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });

    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}



