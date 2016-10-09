package com.owen.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.owen.statuslayout.StatusLayout;

public class MainActivity extends AppCompatActivity {

    private StatusLayout mStatusLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStatusLayout = (StatusLayout) findViewById(R.id.statusLayout);
        mStatusLayout.setMode(StatusLayout.MODE.PROGRESS);

        findViewById(R.id.btnProgress).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mStatusLayout.setMode(StatusLayout.MODE.PROGRESS);
            }
        });
        findViewById(R.id.btnEmpty).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mStatusLayout.setMode(StatusLayout.MODE.EMPTY);
            }
        });
        findViewById(R.id.btnContent).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mStatusLayout.setMode(StatusLayout.MODE.CONTENT);
            }
        });
        findViewById(R.id.btnError).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mStatusLayout.setMode(StatusLayout.MODE.ERROR);
            }
        });
    }
}
