package com.example.orderingsystem.setting;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.orderingsystem.R;
import com.example.orderingsystem.structure.MyApplication;


public class DishSetting extends Activity {
    ImageView musicbtn = null;
    Button setexit;
    ActivityReceiver activityReceiver;
    private int state;
    private MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MyApplication) getApplication();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogsetting);
        musicbtn = (ImageButton) findViewById(R.id.music);
        state = app.getState();
        // 播放器按钮的实现
        if (state == 1) {
            musicbtn.setImageDrawable(getResources().getDrawable(R.drawable.shengyin));
        }
        if (state == 2) {
            musicbtn.setImageDrawable(getResources().getDrawable(R.drawable.jingyin));
        }
        // 服务动态注册
        activityReceiver = new ActivityReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.orderingsystem.MusicPlayer.update");
        registerReceiver(activityReceiver, filter);
        // 确认按钮退出事件
        setexit = findViewById(R.id.settingexit);
        setexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 图片按钮的监听事件
        ImageButton.OnClickListener buttonListener = new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = app.getState();
                Intent sendIntend = new Intent("com.example.orderingsystem.MusicPlayer.control");
                // 设置服务状态及广播开闭状态
                if (state == 1) {
                    app.setState(2);
                    sendBroadcast(sendIntend);
                }
                if (state == 2) {
                    app.setState(1);
                    sendBroadcast(sendIntend);
                }
            }
        };
        musicbtn.setOnClickListener(buttonListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(activityReceiver);
    }
    // 根据声音播放状态控制图片更改
    public class ActivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int mupdate = app.getState();
            switch (mupdate) {
                case 1:
                    musicbtn.setImageDrawable(getResources().getDrawable(R.drawable.shengyin));
                    break;
                case 2:
                    musicbtn.setImageDrawable(getResources().getDrawable(R.drawable.jingyin));
                    break;
            }
        }
    }
}
