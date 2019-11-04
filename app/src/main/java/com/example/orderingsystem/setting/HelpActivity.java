package com.example.orderingsystem.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.example.orderingsystem.R;

public class HelpActivity extends Activity {

    EditText txtFlag;
    Button btnflagSaveButton;
    Button btnflagCancelButton;
    Button aboutexitbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.help_activity);
        Button aboutexitbtn = findViewById(R.id.aboutexit);

        aboutexitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        WebView webView = findViewById(R.id.webView1);
        StringBuilder sb = new StringBuilder();
        sb.append("<div>《点餐系统》使用帮助：</div>");
        sb.append("<ul>");
        sb.append("<li>音乐开关：选择通过顶部菜单的“设置”选项来设置开关音乐，项目运行时，自动播放音乐.</li>");

        sb.append("<li>选择“点餐”模块：可以按菜品的分类浏览菜品，并选择想要的菜品</li>");

        sb.append("<li>选择“菜单”模块：可以查看自己点的菜单，并浏览所有菜品，修改想要点的菜品</li>");


        sb.append("<li>选择“个人中心”模块：可以修改用户的密码信息和配送地址</li>");
        sb.append("<li>选择“登陆退出”模块：可以随时登陆系统或退出系统</li>");
        sb.append("<li>顶部菜单：可以查看本系统的帮助文件，设置音乐开关，退出《点餐系统》</li>");
        sb.append("</ul>");
        webView.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null);

    }
}
