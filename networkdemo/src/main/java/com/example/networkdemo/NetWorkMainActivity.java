package com.example.networkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.networkdemo.annotation.NetWork;
import com.example.networkdemo.utils.Constants;
import com.example.networkdemo.utils.NetWorkManager;

public class NetWorkMainActivity extends AppCompatActivity
//        implements NetChangeObserver
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work_main);
//        NetWorkManager.getDefault().initApplication(getApplication());
//        NetWorkManager.getDefault().setNetChangeListener(this);
        NetWorkManager.getDefault().registerReceiver(this);
    }


    @NetWork(netType = NetType.WIFI)
    public void netWork(NetType netType){
        switch (netType){
            case WIFI:
                Log.e(Constants.LOG_TAG,netType + "");
                break;
            case CMNET:
            case CMWAP:
                Log.e(Constants.LOG_TAG,"流量");
                break;
            case NONE:
                break;
        }
    }

//    @Override
//    public void onConnect(NetType netType) {
//        Log.e(Constants.LOG_TAG,netType + "");
//    }
//
//    @Override
//    public void disOnConnect() {
//        Log.e(Constants.LOG_TAG, "网络断开");
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        NetWorkManager.getDefault().unRegisterReceiver(this);
        NetWorkManager.getDefault().unRegisterAllReceiver(this);
    }
}

