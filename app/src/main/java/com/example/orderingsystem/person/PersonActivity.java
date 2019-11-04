package com.example.orderingsystem.person;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderingsystem.R;
import com.example.orderingsystem.db.UserDBManager;
import com.example.orderingsystem.structure.MyApplication;
import com.example.orderingsystem.structure.User;

import org.apache.http.util.EncodingUtils;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PersonActivity extends AppCompatActivity {
    private TextView textUid, textOld, textNew, textAgain, textPhone, textAddress;
    private Button btnModify, btnBack;
    //定义文件名
    private String file = "userinfo.txt";
    private String ofile = "peopleinfo.txt";
    private String ufile = "logindata";
    private String password,userName,age,trueName;// 真正密码和用户名
    private UserDBManager dhelper;
    MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        textUid = findViewById(R.id.textUid);
        textNew = findViewById(R.id.textNew);
        textOld = findViewById(R.id.textOld);
        textAgain = findViewById(R.id.textAgain);
        textAddress = findViewById(R.id.textAddress);
        textPhone = findViewById(R.id.textPhone);
        btnBack = findViewById(R.id.btnBack);
        btnModify = findViewById(R.id.btnModify);

        app = (MyApplication) getApplication();

        dhelper=new UserDBManager(PersonActivity.this);
        dhelper.open();

        // 旧代码：文件方式读取信息
        /*
        try {
            //获取指定文件的文件输入流
            FileInputStream fileInputStream = openFileInput(file);
            //定义一个字节缓存数组
            byte[] buffer = new byte[fileInputStream.available()];
            //将数据读到缓存区
            fileInputStream.read(buffer);
            // 字符串生成防止编码错误
            String data = EncodingUtils.getString(buffer, "UTF-8");
            // 写入json
            try {
                JSONObject json = new JSONObject(data);
                // 读取信息
                userName = json.getString("userName");
                password = json.getString("password");
                age = json.getString("age");
                trueName = json.getString("trueName");
                // 放入控件中
                textUid.setText(userName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //关闭文件输入流
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        // 数据库读取信息
        User data=dhelper.getInfo(app.getUid());
        if(data!=null){
            // 设置控件
            textUid.setText(data.uid);
            textAddress.setText(data.address);
            textPhone.setText(data.phone);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 旧代码：监听器方式+文件读取
        /*
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textNew.getText().toString().equals("") || textOld.getText().toString().equals("") || textAgain.getText().toString().equals("") ||
                        textAddress.getText().toString().equals("") || textPhone.getText().toString().equals("")) {
                    Toast.makeText(PersonActivity.this, "提交失败，有空白项！", Toast.LENGTH_SHORT).show();
                }else if(!textOld.getText().toString().equals(password)){
                    Toast.makeText(PersonActivity.this, "原密码输入错误！", Toast.LENGTH_SHORT).show();
                    textOld.setText("");
                    textOld.setFocusable(true);
                }
                else if (!textNew.getText().toString().equals(textAgain.getText().toString())) {
                    Toast.makeText(PersonActivity.this, "提交失败，两次密码输入不一致！", Toast.LENGTH_SHORT).show();
                    textNew.setText("");
                    textAgain.setText("");
                    textNew.setFocusable(true);
                } else {
                    //获取文件输出流
                    try{
                        // 修改userinfo ,主要考虑到密码修改问题
                        FileOutputStream fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE);
                        JSONObject json = new JSONObject();// JSON格式
                        try{
                            json.put("userName",userName);
                            json.put("password",textNew.getText().toString());
                            json.put("trueName",trueName);
                            json.put("age",age);
                            json.put("phone",textPhone.getText().toString());
                            json.put("address",textAddress.getText().toString());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        //将内容写入文件
                        fileOutputStream.write (json.toString().getBytes());
                        fileOutputStream.flush();
                        fileOutputStream.close();

                        // 创建peopleinfo
                        fileOutputStream = openFileOutput(ofile, Context.MODE_PRIVATE);
                        //将内容写入文件
                        fileOutputStream.write (json.toString().getBytes());
                        fileOutputStream.flush();
                        fileOutputStream.close();

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    finish();
                }
                // SharedPreferences文件写入
                SharedPreferences uSetting = getSharedPreferences(ufile, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = uSetting.edit();
                editor.putString("userName", userName);
                editor.putString("password", textNew.getText().toString());
                editor.putInt("ifrem", 0);
                editor.commit();
                Toast.makeText(PersonActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
            }
        });
        */

    }

    // 用布局文件中修改按钮标签绑定的方式实现
    public void modify(View view) {
        if (textNew.getText().toString().equals("") || textOld.getText().toString().equals("") || textAgain.getText().toString().equals("") ||
                textAddress.getText().toString().equals("") || textPhone.getText().toString().equals("")) {
            Toast.makeText(PersonActivity.this, "提交失败，有空白项！", Toast.LENGTH_SHORT).show();
        } else if (!textNew.getText().toString().equals(textAgain.getText().toString())) {
            Toast.makeText(PersonActivity.this, "提交失败，两次密码输入不一致！", Toast.LENGTH_SHORT).show();
            textNew.setText("");
            textAgain.setText("");
        }else if(dhelper.checkKey(app.getUid(),textOld.getText().toString())==false) {// 密码校验
            Toast.makeText(PersonActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
        } else {
            User data=new User();
            data.uid=textUid.getText().toString();
            data.ukey=textNew.getText().toString();
            data.address=textAddress.getText().toString();
            data.phone=textPhone.getText().toString();
            if(dhelper.modifyInfo(data)!=0){
                Toast.makeText(PersonActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(PersonActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }
}
