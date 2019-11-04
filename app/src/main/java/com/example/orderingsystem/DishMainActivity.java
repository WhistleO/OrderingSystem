package com.example.orderingsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderingsystem.loginreg.LoginActivity;
import com.example.orderingsystem.menu.DishListActivity;
import com.example.orderingsystem.order.OrderedActivity;
import com.example.orderingsystem.person.PersonActivity;
import com.example.orderingsystem.setting.DishSetting;
import com.example.orderingsystem.setting.HelpActivity;
import com.example.orderingsystem.setting.MusicService;
import com.example.orderingsystem.structure.MyApplication;

import java.lang.reflect.Method;

public class DishMainActivity extends AppCompatActivity {
    public static MyApplication app = new MyApplication();
    private ImageView menuImageView;
    private ImageView diancanImageView;
    private ImageView grzxImageView;
    private ImageView loginImageView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (MyApplication) getApplication();
        app.setState(1);

        intent = new Intent(this, MusicService.class);
        startService(intent);

        grzxImageView = findViewById(R.id.grzx);
        menuImageView = findViewById(R.id.caidan);
        diancanImageView = findViewById(R.id.diancan);
        loginImageView = findViewById(R.id.login);
        grzxImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (app.getLoginState()) {
                    Intent intent = new Intent();
                    intent.setClass(DishMainActivity.this, PersonActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(DishMainActivity.this, "未登录", Toast.LENGTH_SHORT).show();
                }
            }
        });
        menuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (app.getLoginState()) {
                    Intent intent = new Intent();
                    intent.setClass(DishMainActivity.this, OrderedActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(DishMainActivity.this, "未登录", Toast.LENGTH_SHORT).show();
                }
            }
        });
        diancanImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (app.getLoginState()) {
                    Intent intent = new Intent();
                    intent.setClass(DishMainActivity.this, DishListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(DishMainActivity.this, "未登录", Toast.LENGTH_SHORT).show();
                }
            }
        });
        loginImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (app.getLoginState() == false) {

                    Intent intent = new Intent();
                    intent.setClass(DishMainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, 0);
                } else {
                    loginImageView.setImageDrawable(getResources().getDrawable(R.drawable.login));
                    loginImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    //loginImageView.getBackground().setAlpha(100);
                    app.setLoginstatus(false);
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem sItem = menu.findItem(R.id.action_search);
        sItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });
        SearchView searchView = (SearchView) sItem.getActionView();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {

                }
            }
        }
        return super.onMenuOpened(featureId, menu);

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.exit:
                AlertDialog.Builder exitDialog = new AlertDialog.Builder(DishMainActivity.this);
                exitDialog.setTitle("温馨提示:").setIcon(R.drawable.title).setMessage("是否确定退出？");
                exitDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                exitDialog.setNegativeButton("取消", null);
                exitDialog.create();
                exitDialog.show();
                break;
            case R.id.abut:
                Intent aboutintent = new Intent(DishMainActivity.this, HelpActivity.class);
                startActivity(aboutintent);
                break;
            case android.R.id.home:
                finish();
                break;
            case R.id.action_settings:
                Intent intent = new Intent(DishMainActivity.this, DishSetting.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                if (app.getLoginState()) {
                    loginImageView.setImageDrawable(getResources().getDrawable(R.drawable.exit));
                    loginImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    //loginImageView.getBackground().setAlpha(0);
                }
            }
        }
    }
}
