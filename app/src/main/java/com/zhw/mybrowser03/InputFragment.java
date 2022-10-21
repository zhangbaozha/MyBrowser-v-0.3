package com.zhw.mybrowser03;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class InputFragment extends Fragment {
    private String words[] = {};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_input, container, false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageButton searchButton = getView().findViewById(R.id.KeywordSearchButton);
        AutoCompleteTextView autoCompleteTextView = getView().findViewById(R.id.autoCompleteTextView);
        String Keyword = autoCompleteTextView.toString();
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line, words);
//        autoCompleteTextView.setAdapter(adapter);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Keyword = String.valueOf(autoCompleteTextView.getText());
                String url = "http://www.baidu.com/s?wd=" + Keyword;
                Bundle bundle = new Bundle();
                bundle.putString("url", url);

                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.webFragment, bundle);
            }
        });
        ImageButton btScan;
        btScan = getView().findViewById(R.id.bt_scan);
        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize intent
                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
                // Set prompt text
                intentIntegrator.setPrompt("For flash use volume up key");
                // Set beep
                intentIntegrator.setBeepEnabled(true);
                //Locked orientation
                intentIntegrator.setOrientationLocked(true);
                // Set capture activity
                intentIntegrator.setCaptureActivity(Capture.class);
                // Initiae scan
                intentIntegrator.initiateScan();

            }
        });
        ImageButton Github;
        Github = getView().findViewById(R.id.github);
//        ImageButton TaoBao = getView().findViewById(R.id.Taobao);
//        ImageButton Github = getView().findViewById(R.id.Github);
//        ImageButton Music = getView().findViewById(R.id.QQMusic);
//        ImageButton Stock = getView().findViewById(R.id.stock);
        Github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.github.com/";
                Bundle bundle = new Bundle();
                bundle.putString("url", url);

                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.webFragment, bundle);
            }
        });

        ImageButton Stock;
        Stock = getView().findViewById(R.id.stock);

        Stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://stock.eastmoney.com/";
                Bundle bundle = new Bundle();
                bundle.putString("url", url);

                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.webFragment, bundle);
            }
        });
        ImageButton Music;
        Music = getView().findViewById(R.id.music);

        Music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://y.qq.com/";
                Bundle bundle = new Bundle();
                bundle.putString("url", url);

                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.webFragment, bundle);
            }
        });
        ImageButton Bilibili;
        Bilibili = getView().findViewById(R.id.Bili);

        Bilibili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.bilibili.com/";
                Bundle bundle = new Bundle();
                bundle.putString("url", url);

                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.webFragment, bundle);
            }
        });
        ImageButton Taobao;
        Taobao = getView().findViewById(R.id.tao);

        Taobao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.taobao.com/";
                Bundle bundle = new Bundle();
                bundle.putString("url", url);

                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.webFragment, bundle);
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Initialize intent result
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );
        if (intentResult.getContents() != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    getActivity()
            );
            builder.setTitle("Result");
            builder.setMessage(intentResult.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }else {
            Toast.makeText(getContext(),
                    "抱歉....你没有扫描任何东西！",Toast.LENGTH_SHORT).show();
        }
    }
}