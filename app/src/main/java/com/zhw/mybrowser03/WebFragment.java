package com.zhw.mybrowser03;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zhw.mybrowser03.SQLutils.MyDatabaseHelper;

public class WebFragment extends Fragment {
    private boolean noPic = false;
    private MyDatabaseHelper dbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        dbHelper = new MyDatabaseHelper(getActivity(),"Record",null,4);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        ImageButton star = getView().findViewById(R.id.star);
        ImageButton back = getView().findViewById(R.id.back);
        ImageButton forward = getView().findViewById(R.id.forward);
        super.onActivityCreated(savedInstanceState);
        String url = getArguments().getString("url");
        WebView webView = getView().findViewById(R.id.webView);
        // 设置无图模式
        webView.loadUrl(url);

        if(noPic){
            webView.getSettings().setBlockNetworkImage(true); // 设置无图模式
        }
        else{
            webView.getSettings().setBlockNetworkImage(false); // 设置无图模式
        }

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // WebView不加载该Url
                String title = view.getTitle();

                String str = "http://m.baidu.com/";
                boolean b = url.startsWith(str);
                if(!b){
//                    System.out.println("标题"+title);
//                    System.out.println(url);
                    ContentValues values = new ContentValues();
                    values.put("url",url);


                    db.insert("Record",null,values);

                    values.clear();
                }


                return false;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                delay(1000);
                super.onPageFinished(view, url);
                String title = view.getTitle();
                if (!TextUtils.isEmpty(title)) {



                }
            }

        });
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    //按返回键操作并且能回退网页
                    if (i == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                        //后退
                        webView.goBack();
                        return true;
                    }
                }

                return false;
            }


        });

        ImageButton fresh = getView().findViewById(R.id.fresh);
        fresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = webView.getUrl();
                webView.loadUrl(url);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.goBack();
            }
        });
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.goForward();
            }
        });
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = new MyDatabaseHelper(getActivity(),"StarCategoryItem",null,1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String url = webView.getUrl();
                ContentValues values = new ContentValues();
                values.put("category", "默认收藏夹");
                values.put("url",url);

                db.insert("StarCategoryItem",null,values);
                Toast.makeText(getContext(),"收藏成功",Toast.LENGTH_SHORT).show();
                values.clear();
            }
        });

    }
    private void delay(int ms){
        try {
            Thread.currentThread();
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        boolean value = ((MainActivity)context).toValue();
        noPic = value;
    }


}