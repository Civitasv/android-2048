package com.civitasv.page.home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.civitasv.R;
import com.civitasv.databinding.ActivityHomeBinding;
import com.civitasv.page.base.BackHandleActivity;

/**
 * 本次开发遵循单Activity多Fragment模式
 */
public class HomeActivity extends BackHandleActivity {
    private final String TAG = "ACTIVITY_HOME";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        HomeActivityViewModel homeActivityViewModel = new ViewModelProvider(this).get(HomeActivityViewModel.class);
    }
}
