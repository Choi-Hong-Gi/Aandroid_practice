package com.example.assignment;

import static android.graphics.Color.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class Activity_Lottos extends Activity {

    public static int[] distRandNum(int x) {
        int[] arr;
        final int N = x;
        arr = new int[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random()*45+1);
            for(int j=0; j < i; j++)
                if(arr[i] == arr[j])
                    i--;
        }
        return arr;
    }

    public static int[] sorting(int[] arr) {    // 오름차순 정리
        for(int i = 0; i < arr.length-1; i++) {
            int m = i;
            for(int j = i+1; j < arr.length; j++)
                if(arr[m] > arr[j])
                    m = j;
            int tmp = arr[i];
            arr[i] = arr[m];
            arr[m] = tmp;
        }
        return arr;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottos);

        Button btnReset = (Button) findViewById(R.id.btnReset);
        Button btnCreateNum = (Button) findViewById(R.id.bntCreateNum);
        Button btnBack = (Button) findViewById(R.id.bntBack);

        Button btns[][] = new Button[5][6];

        Integer [][] btnID;
        btnID = new Integer[][]{{R.id.btn1_1, R.id.btn1_2, R.id.btn1_3, R.id.btn1_4, R.id.btn1_5, R.id.btn1_6},
                {R.id.btn2_1, R.id.btn2_2, R.id.btn2_3, R.id.btn2_4, R.id.btn2_5, R.id.btn2_6},
                {R.id.btn3_1, R.id.btn3_2, R.id.btn3_3, R.id.btn3_4, R.id.btn3_5, R.id.btn3_6},
                {R.id.btn4_1, R.id.btn4_2, R.id.btn4_3, R.id.btn4_4, R.id.btn4_5, R.id.btn4_6},
                {R.id.btn5_1, R.id.btn5_2, R.id.btn5_3, R.id.btn5_4, R.id.btn5_5, R.id.btn5_6}};

        for(int i = 0; i < 5; i++){
            final int index1;
            index1 = i;
            for(int j = 0; j < 6; j++){
                final int index2;
                index2 = j;
                btns[i][j] = (Button) findViewById(btnID[i][j]);
                btns[i][j].setBackground(getDrawable(R.drawable.btn_gray));
            }
        }


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < 5; i++){
                    final int index1;
                    index1 = i;
                    for(int j = 0; j < 6; j++){
                        final int index2;
                        index2 = j;
                        btns[i][j] = (Button) findViewById(btnID[i][j]);
                        btns[i][j].setText("");
                        btns[i][j].setBackground(getDrawable(R.drawable.btn_gray));
                    }
                }

            }
        });

        btnCreateNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i = 0; i < 5; i++){
                    final int index1;
                    index1 = i;
                    int arr1[] = distRandNum(6);
                    int arr2[] = sorting(arr1);
                    for(int j = 0; j < 6; j++){
                        final int index2;
                        index2 = j;
                        btns[i][j] = (Button) findViewById(btnID[i][j]);
                        btns[i][j].setText(Integer.toString(arr2[j]));
                        btns[i][j].setTextColor(WHITE);
                        if(arr2[j]<10){
                            btns[i][j].setBackground(getDrawable(R.drawable.btn_yellow));
                        }
                        else if(arr2[j]<20){
                            btns[i][j].setBackground(getDrawable(R.drawable.btn_blue));
                        }
                        else if(arr2[j]<30){
                            btns[i][j].setBackground(getDrawable(R.drawable.btn_red));
                        }
                        else if(arr2[j]<40){
                            btns[i][j].setBackground(getDrawable(R.drawable.btn_gray));
                        }
                        else{
                            btns[i][j].setBackground(getDrawable(R.drawable.btn_green));
                        }

                    }
                }

            }
        });



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }
}
