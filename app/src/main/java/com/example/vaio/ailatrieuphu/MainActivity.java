package com.example.vaio.ailatrieuphu;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView btnPlay;
    private ProgressFragment progressFragment;
    private GamingFragment gamingFragment = new GamingFragment();
    private FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        btnPlay = (TextView) findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(this);
        layout = (FrameLayout) findViewById(R.id.main_frame);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    public void hideFragemnt(Fragment hide) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.hide(hide);
        fragmentTransaction.commit();
    }


    @Override
    public void onClick(View v) {


        startAnimation();
        btnPlay.setClickable(false);
        progressFragment = new ProgressFragment();
        replaceFragment(progressFragment);
    }

    public void startAnimation() {
        layout.setVisibility(View.VISIBLE);
        TranslateAnimation animation = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_screen);
        layout.startAnimation(animation);

    }

    public ProgressFragment getProgressFragment() {
        return progressFragment;
    }

    public GamingFragment getGamingFragment() {
        return gamingFragment;
    }

    @Override
    public void onBackPressed() {
        if (layout.getVisibility()==View.INVISIBLE) {
            super.onBackPressed();
        } else {
            layout.setVisibility(View.INVISIBLE);
        }
        btnPlay.setVisibility(View.VISIBLE);
        btnPlay.setClickable(true);
    }

}
