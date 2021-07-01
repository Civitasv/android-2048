package com.civitasv.page.game;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.civitasv.R;
import com.civitasv.databinding.FragmentGameBinding;
import com.civitasv.page.base.BackHandleFragment;

public class GameFragment extends BackHandleFragment {
    private final String TAG = "FRAGMENT_GAME";

    private FragmentGameBinding binding;
    private GameFragmentViewModel gameFragmentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 数据与视图绑定
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_game, container, false);
        gameFragmentViewModel = new ViewModelProvider(requireActivity()).get(GameFragmentViewModel.class);
        // 定义返回回调

        return binding.getRoot();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
