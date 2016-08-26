package com.example.vishambar.demodatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void save(View view){
        SmsRemoteDatabaseAdapter main = new SmsRemoteDatabaseAdapter(this);
        long t = main.insert("vis", "9953331065", "Hi","bye");
        System.out.print(t);
    }
}
