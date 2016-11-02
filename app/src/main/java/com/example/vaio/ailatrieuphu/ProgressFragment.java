package com.example.vaio.ailatrieuphu;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

/**
 * Created by vaio on 10/28/2016.
 */

public class ProgressFragment extends Fragment implements View.OnClickListener {
    public static final int RULE = R.raw.luatchoi_c;
    private TextView tvContinuos;
    public static final int GO_FIND = R.raw.gofind;
    private MediaPlayer gofindAudio;
    private boolean check_click ;
    private MediaPlayer ruleAudio;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_progress, container, false);
        initViews(v);
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initViews(View v) {
        check_click = false;
        tvContinuos = (TextView) v.findViewById(R.id.tv_continuos);
        tvContinuos.setOnClickListener(this);
        gofindAudio = MediaPlayer.create(ProgressFragment.this.getContext(), GO_FIND);
        ruleAudio  = MediaPlayer.create(ProgressFragment.this.getContext(),RULE);
        ruleAudio.start();
    }

    @Override
    public void onClick(View v) {
        if (check_click) {
            return;
        }
        check_click = true;
        ruleAudio.stop();
        gofindAudio.start();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.hideAll();
                mainActivity.initViews();
                mainActivity.background(R.drawable.progress);
                mainActivity.startAnimation();
                mainActivity.replaceFragment(mainActivity.getGamingFragment());
            }
        }, 6000);
    }
}
