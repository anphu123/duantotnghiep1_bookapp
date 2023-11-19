package com.example.duan1bookapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.duan1bookapp.R;
import com.example.duan1bookapp.databinding.ActivityDashBoardBinding;
import com.example.duan1bookapp.fragment.CategoryFragment;
import com.example.duan1bookapp.fragment.CoinFragment;
import com.example.duan1bookapp.fragment.FavoriteFragment;
import com.example.duan1bookapp.fragment.HomeFragment;
import com.example.duan1bookapp.fragment.MangaFrament;
import com.example.duan1bookapp.fragment.MyPageFragment;
import com.example.duan1bookapp.models.Customer;

public class DashBoard extends AppCompatActivity {

    private Customer customer = null;
    ActivityDashBoardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        customer = (Customer) bundle.get("object_customer");
        int customerid= customer.getId();
        replaceFragment(new HomeFragment());
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_ic_home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.nav_ic_manga) {
                replaceFragment(new CategoryFragment());
            } else if (item.getItemId() == R.id.nav_ic_comment) {
                replaceFragment(new FavoriteFragment());
            } else if (item.getItemId() == R.id.nav_ic_buy_coin) {
                CoinFragment coinFragment = new CoinFragment();
                coinFragment.setUserID(customerid);
                replaceFragment(coinFragment);
            }else if (item.getItemId() == R.id.nav_ic_user) {
                replaceFragment(new MyPageFragment());
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



