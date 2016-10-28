package com.example.vaio.ailatrieuphu;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by vaio on 10/28/2016.
 */

public class GamingFragment extends Fragment implements View.OnClickListener {
    public static final int ANSWER_A = 1;
    public static final int ANSWER_B = 2;
    public static final int ANSWER_C = 3;
    public static final int ANSWER_D = 4;
    private TextView tvAnswerA;
    private TextView tvAnswerB;
    private TextView tvAnswerC;
    private TextView tvAnswerD;
    private TextView tvRecentQuestion;
    private TextView tvQuestion;
    private int recentLevel = 0;
    private boolean checked = false;
    private MyDatabase database;
    private Question question;
    private ProgressBar progressBar;
    private int timeRemaining = 30;

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
//        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

    }

    private void initData() {
        database = new MyDatabase(getActivity().getBaseContext());
        question = database.getQuestion(recentLevel);

        tvQuestion.setText(question.getQuestion());
        tvAnswerA.setText("A : " + question.getCaseA());
        tvAnswerB.setText("B : " + question.getCaseB());
        tvAnswerC.setText("C : " + question.getCaseC());
        tvAnswerD.setText("D : " + question.getCaseD());


        tvAnswerA.setOnClickListener(this);
        tvAnswerB.setOnClickListener(this);
        tvAnswerC.setOnClickListener(this);
        tvAnswerD.setOnClickListener(this);

        tvRecentQuestion.setText("Câu : "+(recentLevel+1));
    }

    public void checkAnswer(final int answer, final View v) {
        checked = true;
        final TextView tv = (TextView) v;
        tv.setBackgroundColor(Color.BLUE);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(GamingFragment.this.getContext(),R.anim.anim_answer);
                if (question.getTrueCase() == answer) {
                    tv.startAnimation(animationSet);
                    Handler handler1 = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tv.setBackgroundColor(0);
                            tv.clearAnimation();
                            recentLevel++;
                            initData();
                        }
                    },3000);

                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(GamingFragment.this.getContext());
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity mainActivity = (MainActivity) getActivity();
                            mainActivity.onBackPressed();
                        }
                    });
                    builder.setTitle("Bạn đã thua");
                    builder.setCancelable(false);

                    tv.startAnimation(animationSet);
                    tv.setBackgroundColor(Color.RED);
                    switch (question.getTrueCase()){
                        case 1:
                            tvAnswerA.setBackgroundColor(Color.BLUE);
                            tvAnswerA.startAnimation(animationSet);
                            break;
                        case 2:
                            tvAnswerB.setBackgroundColor(Color.BLUE);
                            tvAnswerB.startAnimation(animationSet);
                            break;
                        case 3:
                            tvAnswerC.setBackgroundColor(Color.BLUE);
                            tvAnswerC.startAnimation(animationSet);
                            break;
                        case 4:
                            tvAnswerD.setBackgroundColor(Color.BLUE);
                            tvAnswerD.startAnimation(animationSet);
                            break;
                    }




                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            builder.create().show();
                        }
                    },4000);

                    recentLevel = 0;
                }
            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.answer_a:
                checkAnswer(ANSWER_A, v);
                break;
            case R.id.answer_b:
                checkAnswer(ANSWER_B, v);
                break;
            case R.id.answer_c:
                checkAnswer(ANSWER_C, v);
                break;
            case R.id.answer_d:
                checkAnswer(ANSWER_D, v);
                break;

        }
    }
}
