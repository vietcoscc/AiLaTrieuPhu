package com.example.vaio.ailatrieuphu;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

/**
 * Created by vaio on 10/28/2016.
 */

public class ProgressFragment extends Fragment implements View.OnClickListener{
    private TextView tvContinuos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_progress,container,false);
        initViews(v);
//        TranslateAnimation animation = (TranslateAnimation) AnimationUtils.loadAnimation(getActivity().getBaseContext(),R.anim.anim_screen);
//        v.startAnimation(animation);
        return v;
    }
    private void initViews(View v) {
        tvContinuos = (TextView) v.findViewById(R.id.tv_continuos);
        tvContinuos.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.startAnimation();
        mainActivity.replaceFragment(mainActivity.getGamingFragment());
    }
    public void onBackPressed(){
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.hideFragemnt(mainActivity.getProgressFragment());
    }
}
