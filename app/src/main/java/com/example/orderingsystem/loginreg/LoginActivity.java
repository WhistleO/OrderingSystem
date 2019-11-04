package com.example.orderingsystem.loginreg;

        import android.app.Activity;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.Toast;

        import androidx.appcompat.app.AppCompatActivity;

        import com.example.orderingsystem.DishMainActivity;
        import com.example.orderingsystem.R;
        import com.example.orderingsystem.db.UserDBManager;
        import com.example.orderingsystem.structure.MyApplication;

public class LoginActivity extends AppCompatActivity {

    public MyApplication app;
    private Button btnReg, btnLogin;
    private EditText et_userName, et_password;
    private CheckBox remuser;
    private String ufile = "logindata";
    private SharedPreferences message;
    private String username, ukey;
    UserDBManager dhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_userName = findViewById(R.id.editUid);
        et_password = findViewById(R.id.editPwd);
        remuser = findViewById(R.id.cbRemember);

        app = (MyApplication) getApplication();

        btnReg = findViewById(R.id.btnReg);
        btnLogin = findViewById(R.id.btnLogin);
        // 打开数据库
        dhelper=new UserDBManager(LoginActivity.this);
        dhelper.open();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.setLoginstatus(false);
                Intent intent = new Intent("com.example.orderingsystem.Regist");
                // 隐式启动
                startActivityForResult(intent, 1);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = et_userName.getText().toString().trim();
                ukey = et_password.getText().toString().trim();
                if (username.isEmpty() || ukey.isEmpty()) {
                    // 输入判断
                    Toast.makeText(LoginActivity.this, "账号密码不能为空！", Toast.LENGTH_SHORT).show();
                    et_userName.setFocusable(true);
                    et_userName.requestFocus();
                    return;
                }else if(dhelper.haveUid(username)==false){// 判断是否存在用户
                    Toast.makeText(LoginActivity.this, "不存在该用户", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    // 旧代码：用SharePreference核验密码
                    /*{
                        // 载入SharedPreference进行核验
                        message = LoadUserPreference();
                        String uname = message.getString("userName", "");
                        String uukey = message.getString("password", "");
                        // 合法校验
                        if (!username.equalsIgnoreCase(uname) || !ukey.equalsIgnoreCase(uukey)) {
                            Toast.makeText(LoginActivity.this, "账号或密码输入错误！", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }*/

                    // 检查数据库用户名密码是否正确
                    if (dhelper.checkKey(username,ukey)==false) {
                        Toast.makeText(LoginActivity.this, "账号或密码输入错误！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 是否记住账号密码写入SharePreference
                    if (remuser.isChecked()) {
                        WriteUserPreference(username, ukey, 1);
                    } else {
                        WriteUserPreference(null, null, 0);
                    }

                    // 修改状态
                    app.setLoginstatus(true);
                    app.setUid(username);
                    // 跳转
                    Intent intent = new Intent(LoginActivity.this, DishMainActivity.class);
                    setResult(RESULT_OK, intent);
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        // 文件获取
        message = LoadUserPreference();
        username = message.getString("userName", "");
        ukey = message.getString("password", "");
        int ifrem = message.getInt("ifrem", 0);
        // 获取值填充，记住我、账号、密码
        if (ifrem == 1) {
            et_userName.setText(username);
            et_password.setText(ukey);
            remuser.setChecked(true);
        } else {
            et_userName.setText("");
            et_password.setText("");
            remuser.setChecked(false);
        }
    }
    // 跳转的界面结束，将返回值传回
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bundle udata = data.getExtras();
                String username = udata.getString("userName");
                String password = udata.getString("password");
                et_userName.setText(username);
                et_password.setText(password);
            }
        }
    }
    // SharedPreferences文件读取
    private SharedPreferences LoadUserPreference() {
        int mode = Activity.MODE_PRIVATE;
        SharedPreferences uSetting = getSharedPreferences(ufile, mode);
        return uSetting;
    }
    // SharedPreferences文件写入
    private void WriteUserPreference(String name, String key, int ifrem) {
        int mode = Activity.MODE_PRIVATE;
        SharedPreferences uSetting = getSharedPreferences(ufile, mode);
        SharedPreferences.Editor editor = uSetting.edit();
        editor.putString("userName", name);
        editor.putString("password", key);
        editor.putInt("ifrem", ifrem);
        editor.commit();
    }
}
