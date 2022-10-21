package com.zhw.mybrowser03;

import android.annotation.SuppressLint;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.zhw.mybrowser03.SQLutils.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;


public class StarFragment extends Fragment {

    ListView listView;
    List<String> urls;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_star, container, false);
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
                Toast.makeText(getContext(),"已清除所有书签",Toast.LENGTH_SHORT).show();
                deleteAllStar();
            }
        });


    }
    public List<String> quarryStar(){
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getActivity(),"StarCategoryItem",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        List<String> data = new ArrayList<String>();


        Cursor cursor = db.query("StarCategoryItem",null,null,null,null,null,"timestamp DESC");
        if(cursor.moveToFirst()){
            do{
                @SuppressLint("Range") String Url = cursor.getString(cursor.getColumnIndex("url"));
                @SuppressLint("Range") String timestamp = cursor.getString(cursor.getColumnIndex("timestamp"));

                data.add(Url);
            }while(cursor.moveToNext());
        }
        db.close();
        return  data;

    }
    public void deleteAllStar(){
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getActivity(),"StarCategoryItem",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from StarCategoryItem where id > 0");

        show();
        db.close();


    }

    public void show(){

        urls = quarryStar();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,urls);
        listView = (ListView) getView().findViewById(R.id.Stars);
        listView.setAdapter(adapter);

    }


}