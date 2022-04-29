package com.example.assignment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Activity_RSP extends Activity {

    int set = 0;
    int score = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsp);

        Button btnEndA1 = (Button) findViewById(R.id.btnEndA1);
        Button btnGo = (Button) findViewById(R.id.btnGo);
        Button btnR = (Button) findViewById(R.id.btnR);
        Button btnS = (Button) findViewById(R.id.btnS);
        Button btnP = (Button) findViewById(R.id.btnP);

        btnR.setBackground(getDrawable(R.drawable.btn_red));
        btnS.setBackground(getDrawable(R.drawable.btn_green));
        btnP.setBackground(getDrawable(R.drawable.btn_yellow));

        TextView computer = (TextView) findViewById(R.id.textView1);
        TextView mine = (TextView) findViewById(R.id.textView2);
        TextView result = (TextView) findViewById(R.id.textView3);
        TextView scoreTv = (TextView) findViewById(R.id.textView4);

        computer.setText("");
        mine.setText("");

        scoreTv.setText("Score: 0");

        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mine.setText("바위");
                mine.setTextColor(Color.RED);
                set = 1;
            }
        });

        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mine.setText("가위");
                mine.setTextColor(Color.GREEN);
                set = 2;
            }
        });

        btnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mine.setText("보");
                mine.setTextColor(Color.YELLOW);
                set = 3;
            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (set == 0) {
                    Toast.makeText(getApplicationContext(), "가위바위보를 선택해주세요!", Toast.LENGTH_SHORT).show();
                } else{
                    int rsp_com = ((int) (Math.random() * 3) + 1);

                if (rsp_com == 1) {
                    computer.setText("바위");
                    computer.setTextColor(Color.RED);
                    if (set == 1) {
                        Toast.makeText(getApplicationContext(), "비겼습니다!", Toast.LENGTH_SHORT).show();
                        result.setText("비김");
                    } else if (set == 2) {
                        Toast.makeText(getApplicationContext(), "졌습니다!", Toast.LENGTH_SHORT).show();
                        result.setText("패배");
                        score -= 10;
                        if (score < 0)
                            score = 0;
                        scoreTv.setText("Score:" + Integer.toString(score));
                    } else if (set == 3) {
                        Toast.makeText(getApplicationContext(), "이겼습니다!", Toast.LENGTH_SHORT).show();
                        result.setText("승리");
                        score += 10;
                        scoreTv.setText("Score:" + Integer.toString(score));
                    }
                } else if (rsp_com == 2) {
                    computer.setText("가위");
                    computer.setTextColor(Color.GREEN);
                    if (set == 1) {
                        Toast.makeText(getApplicationContext(), "이겼습니다!", Toast.LENGTH_SHORT).show();
                        result.setText("승리");
                        score += 10;
                        scoreTv.setText("Score:" + Integer.toString(score));
                    } else if (set == 2) {
                        Toast.makeText(getApplicationContext(), "비겼습니다!", Toast.LENGTH_SHORT).show();
                        result.setText("비김");
                    } else if (set == 3) {
                        Toast.makeText(getApplicationContext(), "졌습니다!", Toast.LENGTH_SHORT).show();
                        result.setText("패배");
                        score -= 10;
                        if (score < 0)
                            score = 0;
                        scoreTv.setText("Score:" + Integer.toString(score));
                    }
                } else if (rsp_com == 3) {
                    computer.setText("보");
                    computer.setTextColor(Color.YELLOW);
                    if (set == 1) {
                        Toast.makeText(getApplicationContext(), "졌습니다!", Toast.LENGTH_SHORT).show();
                        result.setText("패배");
                        score -= 10;
                        if (score < 0)
                            score = 0;
                        scoreTv.setText("Score:" + Integer.toString(score));
                    } else if (set == 2) {
                        Toast.makeText(getApplicationContext(), "이겼습니다!", Toast.LENGTH_SHORT).show();
                        result.setText("승리");
                        score += 10;
                        scoreTv.setText("Score:" + Integer.toString(score));
                    } else if (set == 3) {
                        Toast.makeText(getApplicationContext(), "비겼습니다!", Toast.LENGTH_SHORT).show();
                        result.setText("비김");
                    }

                }

            }

            }
        });

        btnEndA1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
