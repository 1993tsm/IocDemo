package com.example.eventbusdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventbusdemo.annocation.Subscriber;
import com.example.library.InjectManager;
import com.example.library.annotations.ContentView;
import com.example.library.annotations.InjectView;


@ContentView(R.layout.activity_event_bus_main)
public class EventBusMainActivity extends AppCompatActivity {

    private final String TAG = "==EventBusMainActivity";
    @InjectView(R.id.jumpToSecondActivity)
    private TextView mJumpToSeond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        InjectManager.inject(this);
        findViewById(R.id.jumpToSecondActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(EventBusMainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
    }



    @Subscriber(threadMode = ThreadMode.MAIN)
    public void getMessage(EventBean eventBean){
        Log.e(TAG,eventBean.toString() + "线程名为：" + Thread.currentThread().getName());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑EventBus
        EventBus.getDefault().unRegister(this);
    }
}
