package com.example.vaio.ailatrieuphu;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by vaio on 10/28/2016.
 */

public class GamingFragment extends Fragment implements View.OnClickListener {
    public static final int ANSWER_A = 1;
    public static final int ANSWER_B = 2;
    public static final int ANSWER_C = 3;
    public static final int ANSWER_D = 4;

    public static final int PRIZE[] = {200000, 400000, 600000, 1000000, 3000000, 6000000, 10000000, 14000000,
            22000000, 30000000, 400000000, 60000000, 85000000, 150000000};

    private TextView tvAnswerA;
    private TextView tvAnswerB;
    private TextView tvAnswerC;
    private TextView tvAnswerD;
    private TextView tvRecentQuestion;
    private TextView tvQuestion;


    private ImageView btnHelpStop;
    private ImageView btnHelp5050;
    private ImageView btnHelpChange;
    private ImageView btnHelpAudience;
    private ImageView btnHelpCall;


    private TextView tvAnswerAPercent;
    private TextView tvAnswerBPercent;
    private TextView tvAnswerCPercent;
    private TextView tvAnswerDPercent;


    private TextView tvTimeRemaining;
    private TextView tvMoney;
    private int recentLevel = 0;
    private boolean checked = false;
    private MyDatabase database;
    private Question question;
    private ProgressBar progressBar;
    private int timeRemaining = 5;
    private Timer timer = new Timer();
    private int totalMoney = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gaming, container, false);
        initViews(v);
        initData();
        return v;
    }

    private void initViews(View v) {
        tvAnswerA = (TextView) v.findViewById(R.id.answer_a);
        tvAnswerB = (TextView) v.findViewById(R.id.answer_b);
        tvAnswerC = (TextView) v.findViewById(R.id.answer_c);
        tvAnswerD = (TextView) v.findViewById(R.id.answer_d);
        tvRecentQuestion = (TextView) v.findViewById(R.id.tv_recent_question);
        tvQuestion = (TextView) v.findViewById(R.id.tv_question);

        btnHelpStop = (ImageView) v.findViewById(R.id.btn_help_stop);
        btnHelp5050 = (ImageView) v.findViewById(R.id.btn_help_5050);
        btnHelpChange = (ImageView) v.findViewById(R.id.btn_help_change);
        btnHelpAudience = (ImageView) v.findViewById(R.id.btn_help_audience);
        btnHelpCall = (ImageView) v.findViewById(R.id.btn_help_call);

        tvAnswerAPercent = (TextView) v.findViewById(R.id.answer_a_percent);
        tvAnswerBPercent = (TextView) v.findViewById(R.id.answer_b_percent);
        tvAnswerCPercent = (TextView) v.findViewById(R.id.answer_c_percent);
        tvAnswerDPercent = (TextView) v.findViewById(R.id.answer_d_percent);

        tvTimeRemaining = (TextView) v.findViewById(R.id.time_remaining);
        tvMoney = (TextView) v.findViewById(R.id.money);

        tvAnswerA.setOnClickListener(this);
        tvAnswerB.setOnClickListener(this);
        tvAnswerC.setOnClickListener(this);
        tvAnswerD.setOnClickListener(this);

        btnHelpStop.setOnClickListener(this);
        btnHelp5050.setOnClickListener(this);
        btnHelpChange.setOnClickListener(this);
        btnHelpAudience.setOnClickListener(this);
        btnHelpCall.setOnClickListener(this);

        progressBar = (ProgressBar) v.findViewById(R.id.progress_time);

    }


    public void initData() {
        timeRemaining = 30;
        database = new MyDatabase(getActivity().getBaseContext());
        question = database.getQuestion(recentLevel);

        tvQuestion.setText(question.getQuestion());
        tvAnswerA.setText("A : " + question.getCaseA());
        tvAnswerB.setText("B : " + question.getCaseB());
        tvAnswerC.setText("C : " + question.getCaseC());
        tvAnswerD.setText("D : " + question.getCaseD());

        tvAnswerAPercent.setText("");
        tvAnswerBPercent.setText("");
        tvAnswerCPercent.setText("");
        tvAnswerDPercent.setText("");

        tvRecentQuestion.setText("Câu : " + (recentLevel + 1));

        TimerTask timerTask = new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                tvTimeRemaining.post(new Runnable() {
                    @Override
                    public void run() {
                        tvTimeRemaining.setText(timeRemaining + "");
                    }
                });
                timeRemaining--;
                if (timeRemaining == 0) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showDialog();
                        }
                    });

                }
            }
        };
        timer.cancel();
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showDialog() {
        recentLevel = 0;
        final AlertDialog.Builder builder = new AlertDialog.Builder(GamingFragment.this.getContext());
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.onBackPressed();
            }
        });
        builder.setTitle("Bạn đã thua");
        progressBar.setVisibility(View.INVISIBLE);
        timer.cancel();
        builder.setCancelable(false);

        builder.create().show();
    }

    public void checkAnswer(final int answer, final View v) {
        checked = true;
        final TextView tv = (TextView) v;
        tv.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_selected);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                final AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(GamingFragment.this.getContext(), R.anim.anim_answer);
                if (question.getTrueCase() == answer) {
                    tv.startAnimation(animationSet);
                    tv.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                    Handler handler1 = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tv.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_normal);
                            tv.clearAnimation();
                            recentLevel++;
                            tvRecentQuestion.setText("Câu : " + recentLevel);
                            initData();
                            totalMoney += PRIZE[recentLevel-1];
                            tvMoney.setText(totalMoney + "");
                            enableClick();
                        }
                    }, 3000);

                } else {


                    tv.startAnimation(animationSet);
                    tv.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_wrong);
                    switch (question.getTrueCase()) {
                        case 1:
                            tvAnswerA.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                            tvAnswerA.startAnimation(animationSet);
                            break;
                        case 2:
                            tvAnswerB.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                            tvAnswerB.startAnimation(animationSet);
                            break;
                        case 3:
                            tvAnswerC.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                            tvAnswerC.startAnimation(animationSet);
                            break;
                        case 4:
                            tvAnswerD.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                            tvAnswerD.startAnimation(animationSet);
                            break;
                    }


                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showDialog();
                        }
                    }, 4000);

                    recentLevel = 0;
                }
            }
        }, 2000);
    }

    private void disableClick() {
        tvAnswerA.setClickable(false);
        tvAnswerB.setClickable(false);
        tvAnswerC.setClickable(false);
        tvAnswerD.setClickable(false);
//
//        btnHelpStop.setClickable(false);
//        btnHelpChange.setClickable(false);
//        btnHelpChange.setClickable(false);
//        btnHelpAudience.setClickable(false);
//        btnHelp5050.setClickable(false);
    }

    private void enableClick() {
        tvAnswerA.setClickable(true);
        tvAnswerB.setClickable(true);
        tvAnswerC.setClickable(true);
        tvAnswerD.setClickable(true);

//        btnHelpStop.setClickable(true);
//        btnHelpChange.setClickable(true);
//        btnHelpChange.setClickable(true);
//        btnHelpAudience.setClickable(true);
//        btnHelp5050.setClickable(true);
    }

    private void handleBtnHelp5050(int r) {
        switch (r) {
            case ANSWER_A:
                tvAnswerA.setText("");
                tvAnswerA.setClickable(false);
                break;
            case ANSWER_B:
                tvAnswerB.setClickable(false);
                tvAnswerB.setText("");
                break;
            case ANSWER_C:
                tvAnswerC.setClickable(false);
                tvAnswerC.setText("");
                break;
            case ANSWER_D:
                tvAnswerD.setClickable(false);
                tvAnswerD.setText("");
                break;
        }
    }

    private void handleBtnAudience() {
        Random random = new Random();
        switch (question.getTrueCase()) {
            case ANSWER_A:
                int a = random.nextInt(80) + 20;
                int b = random.nextInt(100 - a);
                int c = random.nextInt(100 - a - b);
                int d = 100 - a - b - c;

                tvAnswerAPercent.setText("" + a + " %");
                tvAnswerBPercent.setText("" + b + " %");
                tvAnswerCPercent.setText("" + c + " %");
                tvAnswerDPercent.setText("" + d + " %");
                break;
            case ANSWER_B:

                int b1 = random.nextInt(50) + 50;
                int a1 = random.nextInt(100 - b1);
                int c1 = random.nextInt(100 - a1 - b1);
                int d1 = 100 - a1 - b1 - c1;

                tvAnswerAPercent.setText("" + a1 + " %");
                tvAnswerBPercent.setText("" + b1 + " %");
                tvAnswerCPercent.setText("" + c1 + " %");
                tvAnswerDPercent.setText("" + d1 + " %");
                break;
            case ANSWER_C:
                int c2 = random.nextInt(50) + 50;
                int a2 = random.nextInt(100 - c2);
                int b2 = random.nextInt(100 - a2 - c2);
                int d2 = 100 - a2 - b2 - c2;

                tvAnswerAPercent.setText("" + a2 + " %");
                tvAnswerBPercent.setText("" + b2 + " %");
                tvAnswerCPercent.setText("" + c2 + " %");
                tvAnswerDPercent.setText("" + d2 + " %");
                break;
            case ANSWER_D:
                int d3 = random.nextInt(50) + 50;
                int b3 = random.nextInt(100 - d3);
                int c3 = random.nextInt(100 - b3 - d3);
                int a3 = 100 - d3 - b3 - c3;

                tvAnswerAPercent.setText("" + a3 + " %");
                tvAnswerBPercent.setText("" + b3 + " %");
                tvAnswerCPercent.setText("" + c3 + " %");
                tvAnswerDPercent.setText("" + d3 + " %");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.answer_a:
                checkAnswer(ANSWER_A, v);
                disableClick();
                break;
            case R.id.answer_b:
                disableClick();
                checkAnswer(ANSWER_B, v);
                break;
            case R.id.answer_c:
                disableClick();
                checkAnswer(ANSWER_C, v);
                break;
            case R.id.answer_d:
                disableClick();
                checkAnswer(ANSWER_D, v);
                break;
            case R.id.btn_help_stop:

                break;
            case R.id.btn_help_5050:
                btnHelp5050.setImageResource(R.drawable.atp__activity_player_button_image_help_5050_active);

                final Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnHelp5050.setImageResource(R.drawable.atp__activity_player_button_image_help_5050_x);
                        Random random = new Random();
                        int r = random.nextInt(4) + 1;
                        int r2 = random.nextInt(4) + 1;
                        while (r2 == r || r == question.getTrueCase() || r2 == question.getTrueCase()) {
                            r = random.nextInt(4) + 1;
                            r2 = random.nextInt(4) + 1;
                        }

                        handleBtnHelp5050(r);
                        handleBtnHelp5050(r2);
                        btnHelp5050.setClickable(false);
                    }
                }, 2000);
                btnHelp5050.setClickable(false);

                break;
            case R.id.btn_help_change:
                btnHelpChange.setImageResource(R.drawable.atp__activity_player_button_image_help_change_question_active);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnHelpChange.setImageResource(R.drawable.atp__activity_player_button_image_help_change_question_x);
                        initData();
                        btnHelpChange.setClickable(false);
                    }
                }, 2000);
                btnHelpChange.setClickable(false);

                break;
            case R.id.btn_help_audience:

                btnHelpAudience.setImageResource(R.drawable.atp__activity_player_button_image_help_audience_active);
                Handler handler3 = new Handler();
                handler3.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnHelpAudience.setImageResource(R.drawable.atp__activity_player_button_image_help_audience_x);
                        handleBtnAudience();
                        btnHelpAudience.setClickable(false);
                    }
                }, 2000);
                btnHelpAudience.setClickable(false);

                break;
            case R.id.btn_help_call:
                btnHelpCall.setImageResource(R.drawable.atp__activity_player_button_image_help_call_x);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                startActivity(intent);
                break;
        }

    }
}
