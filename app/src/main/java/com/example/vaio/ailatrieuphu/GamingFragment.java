package com.example.vaio.ailatrieuphu;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

/**
 * Created by vaio on 10/28/2016.
 */

public class GamingFragment extends Fragment implements View.OnClickListener {
    private TextView tvAnswerA;
    private TextView tvAnswerB;
    private TextView tvAnswerC;
    private TextView tvAnswerD;
    private TextView tvRecentQuestion;
    private TextView tvQuestion;
    private int recentLevel = 0;

    private MyDatabase database;
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
    }
    private void initData(){
        database = new MyDatabase(getActivity().getBaseContext());
        Question question = database.getQuestion(2);

        tvQuestion.setText(question.getQuestion());
        tvAnswerA.setText("A : "+question.getCaseA());
        tvAnswerB.setText("B : "+question.getCaseB());
        tvAnswerC.setText("C : "+question.getCaseC());
        tvAnswerD.setText("D : "+question.getCaseD());


        tvAnswerA.setOnClickListener(this);
        tvAnswerB.setOnClickListener(this);
        tvAnswerC.setOnClickListener(this);
        tvAnswerD.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TextView tv = (TextView) v;
        tv.setBackgroundColor(Color.BLUE);
    }
}
