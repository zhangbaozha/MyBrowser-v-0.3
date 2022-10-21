package com.zhw.mybrowser03;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.zhw.mybrowser03.SQLutils.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class RecordFragment extends Fragment {
    ListView listView;
    List<String> urls;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        show();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String url = urls.get(i);
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.webFragment, bundle);

            }
        });

        Button button = getView().findViewById(R.id.deleteAll);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAllRecord();
                Toast.makeText(getContext(),"已清除所有历史记录",Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void addRecord(String url){
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getActivity(),"Record",null,4);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("url", url);
        //insert（）方法中第一个参数是表名，第二个参数是表示给表中未指定数据的自动赋值为NULL。第三个参数是一个ContentValues对象
        db.insert("Record",null,values);
        db.close();
    }

    public List<String> quarryRecord(){
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getActivity(),"Record",null,4);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        List<String> data = new ArrayList<String>();


        Cursor cursor = db.query("Record",null,null,null,null,null,"timestamp DESC");
        if(cursor.moveToFirst()){
            do{
                @SuppressLint("Range") String Url = cursor.getString(cursor.getColumnIndex("url"));
                @SuppressLint("Range") String timestamp = cursor.getString(cursor.getColumnIndex("timestamp"));
                System.out.println(timestamp);
                data.add(Url);
            }while(cursor.moveToNext());
        }
        db.close();
        return  data;

    }

    public void deleteRecord(int position){
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getActivity(),"Record",null,4);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList arrayList =new ArrayList();

        db.delete("Record","id=?",new String[] {String.valueOf(arrayList.get(position))});
        db.close();

    }
    public void deleteAllRecord(){
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getActivity(),"Record",null,4);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from Record where id > 0");
        List<String> urls = quarryRecord();
        System.out.println(urls);
        show();
        db.close();


    }

    public void show(){

        urls = quarryRecord();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,urls);
        listView = (ListView) getView().findViewById(R.id.RecordsView);
        listView.setAdapter(adapter);

    }

}