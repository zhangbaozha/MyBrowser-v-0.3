package com.zhw.mybrowser03;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Initialize intent result
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );
        if (intentResult.getContents() != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    this
            );
            builder.setTitle("Result");
            builder.setMessage(intentResult.getContents());
            builder.setPositiveButton("搜索", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    NavController controller = Navigation.findNavController(findViewById(R.id.scanActivity));
                    Bundle bundle = new Bundle();
                    String url = intentResult.getContents();
                    bundle.putString("url", url);
                    controller.navigate(R.id.scanFragment);



                }
            });
            builder.show();
        }else {
            Toast.makeText(getApplicationContext(),
                    "抱歉....你没有扫描任何东西！",Toast.LENGTH_SHORT).show();
        }
    }

}