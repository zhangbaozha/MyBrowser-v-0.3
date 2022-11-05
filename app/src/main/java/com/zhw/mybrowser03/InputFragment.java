package com.zhw.mybrowser03;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.zhw.mybrowser03.SQLutils.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class InputFragment extends Fragment {
    private List<String> words;
    private MyDatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_input, container, false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageButton searchButton = getView().findViewById(R.id.KeywordSearchButton);
        words = quarryKeyword();
        AutoCompleteTextView autoCompleteTextView = getView().findViewById(R.id.autoCompleteTextView);
        String Keyword = autoCompleteTextView.toString();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_dropdown_item_1line,words );
        autoCompleteTextView.setAdapter(adapter);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line, words);
//        autoCompleteTextView.setAdapter(adapter);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbHelper = new MyDatabaseHelper(getActivity(),"Keyword",null,4);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String url = "";
                String Keyword = String.valueOf(autoCompleteTextView.getText());
                ContentValues values = new ContentValues();
                values.put("keyword",Keyword);
                db.insert("Keyword",null,values);
                values.clear();
                if(Keyword.startsWith("www.")){
                    url = "http://" + Keyword + "/";
                }
                else {
                    url = "http://www.baidu.com/s?wd=" + Keyword;
                }


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
                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.scanActivity);

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


    public List<String> quarryKeyword(){
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getActivity(),"Keyword",null,4);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        List<String> data = new ArrayList<String>();


        Cursor cursor = db.query("Keyword",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                @SuppressLint("Range") String Keyword = cursor.getString(cursor.getColumnIndex("keyword"));


                data.add(Keyword);
            }while(cursor.moveToNext());
        }
        db.close();
        return  data;

    }
}