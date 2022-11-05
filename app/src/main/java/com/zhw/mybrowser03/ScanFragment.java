package com.zhw.mybrowser03;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ScanFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String url = getArguments().getString("url");
        Button button = getView().findViewById(R.id.button);
        TextView textView = getView().findViewById(R.id.textView3);
        textView.setText(url);



    }





}