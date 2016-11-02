package com.example.vaio.ailatrieuphu;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView btnPlay;
    private ProgressFragment progressFragment;
    private GamingFragment gamingFragment;
    private FrameLayout layout;
    private RelativeLayout startLayout;
    private ImageView tvLogo;
    private MediaPlayer backgroundStartAudio;

    private boolean check_start_audio = false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void initViews() {

        btnPlay = (ImageView) findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(this);
        layout = (FrameLayout) findViewById(R.id.main_frame);
        startLayout = (RelativeLayout) findViewById(R.id.activity_main);
        tvLogo = (ImageView) findViewById(R.id.tv_logo);
        gamingFragment = new GamingFragment();

        backgroundStartAudio = MediaPlayer.create(this,R.raw.bgmusic);
        backgroundStartAudio.setLooping(true);
        if(!check_start_audio) {
            backgroundStartAudio.start();
            check_start_audio = true;
        }

    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        startAnimation();
        backgroundStartAudio.stop();
        btnPlay.setClickable(false);
        progressFragment = new ProgressFragment();
        replaceFragment(progressFragment);
    }

    public void startAnimation() {
        layout.setVisibility(View.VISIBLE);
        TranslateAnimation animation = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_screen);
        layout.startAnimation(animation);
    }

    public GamingFragment getGamingFragment() {
        return gamingFragment;
    }

    public void hideAll() {
        tvLogo.setVisibility(View.INVISIBLE);
        btnPlay.setVisibility(View.INVISIBLE);
    }

    public void showAll() {
        tvLogo.setVisibility(View.VISIBLE);
        btnPlay.setVisibility(View.VISIBLE);
    }

    public void background(int drawable) {
        startLayout.setBackgroundResource(drawable);
    }

    @Override
    public void onBackPressed() {


        if (layout.getVisibility() == View.INVISIBLE) {
            super.onBackPressed();
        } else {

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Bạn có muốn thoát không ?");
            dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showAll();
                    backgroundStartAudio.start();
                    background(R.drawable.atp__activity_player_background);
                    layout.setVisibility(View.INVISIBLE);
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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
