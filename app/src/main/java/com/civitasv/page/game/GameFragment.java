package com.civitasv.page.game;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
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
        GameView gameView = new GameView(requireContext());
        GameManager gameManager = new GameManager(4);
        gameManager.setOnScoreChangeListener(score -> binding.scoreDetail.setText(String.valueOf(score)));
        gameManager.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onWon(int score) {
                showDialog("您胜利了，分数为" + score + "，是否继续游戏？", () -> {
                    gameManager.keepPlaying();
                }, () -> {
                    gameManager.restart();
                });
            }

            @Override
            public void onFail(int score) {
                showDialog("游戏结束，您的分数为" + score + "，是否立即重新开始下一局游戏？", () -> {
                    gameManager.restart();
                }, () -> {
                });
            }
        });
        gameView.setGameManager(gameManager);
        binding.newGame.setOnClickListener(v -> {
            gameManager.restart();
        });
        binding.gameContainer.addView(gameView);
        return binding.getRoot();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }


    private void showDialog(String msg, Runnable ok, Runnable cancel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(msg);
        // Add the buttons
        builder.setPositiveButton(R.string.ok, (dialog, id) -> {
            // User clicked OK button
            ok.run();
        });
        builder.setNegativeButton(R.string.cancel, (dialog, id) -> {
            // User cancelled the dialog
            cancel.run();
        });
        builder.create().show();
    }
}
