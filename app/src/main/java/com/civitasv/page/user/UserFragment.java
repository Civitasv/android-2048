package com.civitasv.page.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.civitasv.R;
import com.civitasv.databinding.FragmentUserBinding;
import com.civitasv.page.base.BackHandleFragment;

public class UserFragment extends BackHandleFragment {
    private final String TAG = "FRAGMENT_USER";

    private FragmentUserBinding binding;
    private UserFragmentViewModel userFragmentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 数据与视图绑定
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_user, container, false);
        userFragmentViewModel = new ViewModelProvider(requireActivity()).get(UserFragmentViewModel.class);
        // 定义返回回调

        return binding.getRoot();
    }
}
