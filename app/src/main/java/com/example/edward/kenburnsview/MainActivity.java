package com.example.edward.kenburnsview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    MyKenBurnsView kenBurnsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kenBurnsView = (MyKenBurnsView) findViewById(R.id.kenburn_view);

        kenBurnsView.setDrawable(R.drawable.kenburn);

        kenBurnsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kenBurnsView.burn(20000);
            }
        });
    }
}
