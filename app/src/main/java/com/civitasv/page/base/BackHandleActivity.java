package com.civitasv.page.base;

import androidx.appcompat.app.AppCompatActivity;

public class BackHandleActivity extends AppCompatActivity implements OnChangeFragment {
    private BackHandleFragment fragment;

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
