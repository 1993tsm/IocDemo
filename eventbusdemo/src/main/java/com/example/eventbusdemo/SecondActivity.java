package com.example.eventbusdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library.InjectManager;
import com.example.library.annotations.ContentView;
import com.example.library.annotations.InjectView;

@ContentView(R.layout.activity_second)
public class SecondActivity extends AppCompatActivity {

    private final String TAG = "==SecondActivity";

    @InjectView(R.id.sendMessage)
    private TextView mSendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectManager.inject(this);
        mSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postMessage(new EventBean("阿毛","河边"));
            }
        });


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑EventBus
    }
}
