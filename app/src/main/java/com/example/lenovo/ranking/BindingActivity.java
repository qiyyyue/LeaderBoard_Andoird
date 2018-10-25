package com.example.lenovo.ranking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BindingActivity extends AppCompatActivity {
    private Button duolingo_btn;
    private Button memrise_btn;
    private Button visitor_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding);
        duolingo_btn = (Button)findViewById(R.id.btn_duolingo);
        memrise_btn = (Button)findViewById(R.id.btn_memrise);
        visitor_btn = (Button)findViewById(R.id.b_visitor);
        duolingo_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(BindingActivity.this,DuolingoActivity.class);
                startActivity(intent);
            }
        });
//        memrise_btn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//            }
//        });
        visitor_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(BindingActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
