package com.example.assignment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnRSP = (Button) findViewById(R.id.btnRSP);
        Button btnLottos = (Button) findViewById(R.id.btnLottos);
        Button btnEnd = (Button) findViewById(R.id.btnEnd);
        Button btnTEST = (Button) findViewById(R.id.btnTEST);

        btnRSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Activity_RSP.class);
                startActivity(intent);
            }
        });

        btnTEST.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, Activity_Test.class);
                startActivity(intent2);
            }
        });

        btnLottos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity.this, Activity_Lottos.class);
                startActivity(intent3);
            }
        });


        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }


}