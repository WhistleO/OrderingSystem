package com.example.orderingsystem.loginreg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderingsystem.R;
import com.example.orderingsystem.db.UserDBManager;
import com.example.orderingsystem.structure.User;

import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "RegisterActivity";
    // 数据库
    UserDBManager dhelper;
    private TextView tv_note;
    private EditText et_userName;
    private EditText et_password;
    private EditText et_confirm;
    private EditText et_trueName;
    private EditText et_age;
    private ImageView iv_headPhoto;
    private Button btn_takePhoto;
    private Button btn_register, btn_back;
    private String ufile = "logindata";
    private int result;
    private Resources res;
    //定义文件名
    private String file = "userinfo.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        res = getResources();
        //获取布局中的各组件
        tv_note = findViewById(R.id.reg_note);
        et_userName = findViewById(R.id.reg_userName);
        et_password = findViewById(R.id.reg_passowrd);
        et_confirm = findViewById(R.id.reg_confirm);
        et_trueName = findViewById(R.id.reg_trueName);
        et_age = findViewById(R.id.reg_age);
        btn_back = findViewById(R.id.reg_btn_login);
        btn_register = findViewById(R.id.reg_btn_register);
        btn_back.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        // 打开数据库
        dhelper = new UserDBManager(RegisterActivity.this);
        dhelper.open();
    }

    // 采用Activity自身作为监听器的方式，点击注册按钮后，提示信息“注册成功！”
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reg_btn_register: {
                // 获得控件的数据
                String userName = et_userName.getText().toString();
                String password = et_password.getText().toString();
                String confirm = et_confirm.getText().toString();
                String trueName = et_trueName.getText().toString();
                String age = et_age.getText().toString();
                // 填入数据合法判断
                if (userName != null && "".equals(userName)) {
                    tv_note.setText(res.getString(R.string.reg_error_userNameEmpty));
                    et_userName.setFocusable(true);
                    et_userName.requestFocus();
                    return;
                } else if (password != null && "".equals(password)) {
                    tv_note.setText(res.getString(R.string.reg_error_passwordEmpty));
                    et_password.setFocusable(true);
                    et_password.requestFocus();
                    return;
                } else if (confirm != null && "".equals(confirm)) {
                    tv_note.setText(res.getString(R.string.reg_error_confirmEmpty));
                    et_confirm.setFocusable(true);
                    et_confirm.requestFocus();
                    return;
                } else if (!confirm.equals(password)) {
                    tv_note.setText(res.getString(R.string.reg_error_confirmError));
                    et_password.setText("");
                    et_confirm.setText("");
                    et_password.setFocusable(true);
                    et_password.requestFocus();
                    return;
                }else if(dhelper.haveUid(userName)){// 数据库查询是否存在用户
                    tv_note.setText("已存在用户"+userName);
                    return;
                }

                tv_note.setText("注册成功");
                // 数据写入bundle中
                Bundle bundle = new Bundle();
                bundle.putString("userName", userName);
                bundle.putString("password", password);
                // 设置intent
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.putExtras(bundle);// 设置传回数据
                setResult(RESULT_OK, intent);// 设置传回状态及携带数据
                result = RESULT_OK;

                int mode = Activity.MODE_PRIVATE;
                SharedPreferences uSetting = getSharedPreferences(ufile, mode);
                SharedPreferences.Editor editor = uSetting.edit();
                editor.putString("userName", userName);
                editor.putString("password", password);
                editor.commit();

                //获取文件输出流，操作模式是私有
                try {
                    FileOutputStream fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE);
                    //将内容写入文件
                    JSONObject json = new JSONObject();// JSON格式
                    try {
                        json.put("userName", userName);
                        json.put("password", password);
                        json.put("trueName", trueName);
                        json.put("age", age);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    fileOutputStream.write(json.toString().getBytes());
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 数据填入
                User data=new User();
                data.uid=userName;
                data.name=userName;
                data.ukey=password;
                data.truename=trueName;
                data.age=age;
                // 写入数据库
                dhelper.insert(data);
                break;
            }
            case R.id.reg_btn_login: {
                if (result != RESULT_OK)
                    setResult(RESULT_CANCELED);
                finish();
                break;
            }
            default:
                break;
        }
    }
}


