package com.example.vaio.ailatrieuphu;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView btnPlay;
    private ProgressFragment progressFragment;
    private GamingFragment gamingFragment = new GamingFragment();
    private FrameLayout layout;
    private RelativeLayout startLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        btnPlay = (ImageView) findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(this);
        layout = (FrameLayout) findViewById(R.id.main_frame);
        startLayout = (RelativeLayout) findViewById(R.id.activity_main);
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
//        TranslateAnimation animation1 = (TranslateAnimation) AnimationUtils.loadAnimation(this,R.anim.anim_start_screen);
//        startLayout.startAnimation(animation1);
    }

    public ProgressFragment getProgressFragment() {
        return progressFragment;
    }

    public GamingFragment getGamingFragment() {
        return gamingFragment;
    }
    public void setInvisibleLayout(){
        layout.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onBackPressed() {


        if (layout.getVisibility()==View.INVISIBLE) {
            super.onBackPressed();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Bạn có muốn thoát không ?");
            dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    layout.setVisibility(View.INVISIBLE);
                    gamingFragment = new GamingFragment();
                    gamingFragment.stopFragemnt();
                }
            });
            dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.create().show();

        }
        btnPlay.setVisibility(View.VISIBLE);
        btnPlay.setClickable(true);
    }

}
