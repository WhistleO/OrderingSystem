package com.example.orderingsystem.order;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderingsystem.R;
import com.example.orderingsystem.db.DishMenuDBManager;
import com.example.orderingsystem.structure.DishMenu;
import com.example.orderingsystem.structure.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderedActivity extends AppCompatActivity {

    public ListView mlistView;
    public TextView mtvTotalPrice;
    MyApplication app;
    DishMenuDBManager dhelper;
    DishMenu[] uDishMenus;
    String dishName, dishNum, dishPrice;
    DishMenu dMenu = new DishMenu();
    private List<Map<String, Object>> dishListMenu = new ArrayList<Map<String, Object>>();
    private SimpleAdapter orderadapter;
    private String uid;
    private Button btncancel;
    private Button btnok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered);

        mtvTotalPrice = findViewById(R.id.ordertotalprice);
        mlistView = findViewById(R.id.OrderedListview);
        btncancel = findViewById(R.id.submit_cancel);
        btnok = findViewById(R.id.submit_ok);
        app = (MyApplication) getApplication();
        uid = app.getUid();

        dhelper = new DishMenuDBManager(this);
        dhelper.open();
        uDishMenus = dhelper.querydishByUser(uid);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 110) {
                    UpdateOrderList();
                }
            }
        };
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(app.getUid());
                dhelper.deletedishByUid(app.getUid());
                Toast.makeText(OrderedActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        this.mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int arg2, long arg3) {
                final String dmenu = mlistView.getItemAtPosition(arg2) + "";
                try {
                    JSONObject dishdata = new JSONObject(dmenu);
                    dishName = dishdata.getString("title");
                    dishNum = dishdata.getString("num");
                    dishPrice = dishdata.getString("price");
                    System.out.println(dishName + " " + dishNum + " " + dishPrice);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final OrderOneDialog orderDlg = new OrderOneDialog(OrderedActivity.this, dishNum);
                orderDlg.setTitle(dishName);
                orderDlg.show();

                orderDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (orderDlg.mBtnClicked == OrderOneDialog.ButtonID.BUTTON_OK) {
                            dMenu.uid = uid;
                            uDishMenus = dhelper.querydishByUser(uid);
                            dMenu.dishid = dhelper.querydishIDbyName(uid, dishName);
                            dMenu.dishname = dishName;
                            dMenu.dishnum = orderDlg.mNum;
                            dMenu.dishprice = Double.valueOf(dishPrice);
                            DishMenu dd = dhelper.queryonedish(uid, dMenu.dishid);
                            if (dd != null) {
                                if (orderDlg.mNum == 0) {
                                    dhelper.deleteonedish(dMenu, uid);

                                } else {
                                    dhelper.updateNumemenu(dMenu.uid, dMenu.dishid, dMenu.dishnum);
                                }
                            } else {
                                dhelper.insert(dMenu);
                            }
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Message msg = handler.obtainMessage();
                                        uDishMenus = dhelper.querydishByUser(uid);
                                        msg.what = 110;
                                        handler.sendMessage(msg);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

                        }

                    }
                });

            }
        });


    }

    @Override
    protected void onResume() {

        super.onResume();
        UpdateOrderList();
    }

    private void UpdateOrderList() {
        Double totprice, usumprice;
        if (uDishMenus == null) {
            dishListMenu.clear();
            dishListMenu.removeAll(dishListMenu);
            mlistView.setAdapter(null);
            mtvTotalPrice.setText(Double.toString(0.0));

        } else {
            totprice = 0.0;
            dishListMenu.clear();
            dishListMenu.removeAll(dishListMenu);
            mlistView.setAdapter(null);
            for (int i = 0; i < uDishMenus.length; i++) {
                Map<String, Object> bMap = new HashMap<String, Object>();
                bMap.put("title", uDishMenus[i].dishname);
                bMap.put("price", uDishMenus[i].dishprice);
                bMap.put("num", uDishMenus[i].dishnum);
                usumprice = uDishMenus[i].dishnum * uDishMenus[i].dishprice;
                totprice += usumprice;
                bMap.put("sumprice", usumprice);
                dishListMenu.add(bMap);

            }
            orderadapter = new SimpleAdapter(OrderedActivity.this, dishListMenu, R.layout.ordereditem_layout,
                    new String[]{"title", "price", "num", "sumprice"},
                    new int[]{R.id.orderitemtitle, R.id.orderitemprice, R.id.orderitemnum, R.id.orderitemsumprice}
            );
            mlistView.setAdapter(orderadapter);
            orderadapter.notifyDataSetChanged();
            mtvTotalPrice.setText(String.valueOf(totprice));
        }
    }

}
