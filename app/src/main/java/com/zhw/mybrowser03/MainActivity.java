package com.zhw.mybrowser03;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private boolean isFull = false;
    private boolean islandScape = false;
    private boolean noPic = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this, R.id.hostFragment);
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.recordFragment, R.id.starFragment).build();

        NavigationView navigationView = findViewById(R.id.navigationView);

        LayoutInflater factory = LayoutInflater.from(this);

        View layout = factory.inflate(R.layout.fragment_web, null);

        WebView webView = (WebView) layout.findViewById(R.id.webView);
        webView.getSettings().setBlockNetworkImage(true); // 设置无图模式




        Menu menu = navigationView.getMenu();

        MenuItem fullscreen = menu.findItem(R.id.full_screen);
        MenuItem landScape = menu.findItem(R.id.landscape);
        MenuItem noPicture = menu.findItem(R.id.NoPictrue);

        fullscreen.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(!isFull){
                    full(true);
                    isFull = true;
                    Toast.makeText(getApplicationContext(),"全屏显示已打开",Toast.LENGTH_SHORT).show();
                }else{
                    full(false);
                    isFull = false;
                    Toast.makeText(getApplicationContext(),"全屏显示已关闭",Toast.LENGTH_SHORT).show();

                }

                return false;
            }
        });
        landScape.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(!islandScape) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 横屏
                    islandScape = true;
                    full(isFull);
                    Toast.makeText(getApplicationContext(),"已开启横屏模式",Toast.LENGTH_SHORT).show();
                }else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
                    islandScape = false;
                    Toast.makeText(getApplicationContext(),"已开启竖屏模式",Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
        noPicture.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {



                if(!noPic){
                    Toast.makeText(getApplicationContext(),"已开启无图模式",Toast.LENGTH_SHORT).show();
                    noPic = true;
                }
                else{
                    noPic = false;
                    Toast.makeText(getApplicationContext(),"已关闭无图模式",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });




        NavigationUI.setupWithNavController(navigationView, navController);
    }


    public void full(boolean isFull) {//控制是否全屏显示
        if (isFull) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
    public  boolean toValue(){
        return  noPic;
    }








}