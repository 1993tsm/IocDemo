package com.example.administrator.iocdemo;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library.annotations.ContentView;
import com.example.library.annotations.InjectView;
import com.example.library.annotations.OnClick;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @InjectView(R.id.tv)
    private TextView mTv;
    @InjectView(R.id.bt)
    private Button mBt;



    @Override
    protected void onResume() {
        super.onResume();


        Toast.makeText(this,mTv.getText().toString(),Toast.LENGTH_LONG).show();
    }
    @OnClick({R.id.tv,R.id.bt})
    public void click(View view){
        switch (view.getId()){
            case R.id.tv:
                Toast.makeText(this,"tv点击",Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt:
                Toast.makeText(this,"bt点击",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
