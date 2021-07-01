package com.civitasv.page.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BackHandleFragment extends Fragment implements OnBackPressed {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (requireActivity() instanceof OnChangeFragment) {
            ((OnChangeFragment) requireActivity()).onChangeFragment(this);
        } else {
            throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
        }
    }

    @Override
    public boolean onBackPressed() {
        // 默认情况返回事件由 activity 处理
        return true;
    }
}
