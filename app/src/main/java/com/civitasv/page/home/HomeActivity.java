package com.civitasv.page.home;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.civitasv.R;
import com.civitasv.databinding.ActivityHomeBinding;
import com.civitasv.page.base.BackHandleFragment;
import com.civitasv.page.base.OnChangeFragment;

/**
 * 本次开发遵循单Activity多Fragment模式
 */
public class HomeActivity extends AppCompatActivity implements OnChangeFragment {
    private final String TAG = "ACTIVITY_HOME";
    private BackHandleFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        HomeActivityViewModel homeActivityViewModel = new ViewModelProvider(this).get(HomeActivityViewModel.class);
        // 将 bottom menu 与 nav graph 建立链接
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(binding.bottom, navController);
        }
    }

    @Override
    public void onChangeFragment(BackHandleFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onBackPressed() {
        if (fragment == null || fragment.onBackPressed()) { // 如果fragment不存在或者fragment允许后退
            super.onBackPressed();
        }
    }
}
