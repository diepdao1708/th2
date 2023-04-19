package com.android.ex.th2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.ex.R;
import com.android.ex.databinding.ActivityManagementBinding;

public class ManagementActivity extends AppCompatActivity {

    ActivityManagementBinding binding;
    ViewPagerAdapter viewPagerAdapter;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManagementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 3);
        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // noop
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        binding.bottomNavigation.getMenu().findItem(R.id.home).setCheckable(true);
                        break;
                    case 1:
                        binding.bottomNavigation.getMenu().findItem(R.id.history).setCheckable(true);
                        break;
                    case 2:
                        binding.bottomNavigation.getMenu().findItem(R.id.search).setCheckable(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // noop
            }
        });
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    binding.viewPager.setCurrentItem(0);
                    break;
                case R.id.history:
                    binding.viewPager.setCurrentItem(1);
                    break;
                case R.id.search:
                    binding.viewPager.setCurrentItem(2);
                    break;
            }
            return true;
        });

        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        });
    }
}