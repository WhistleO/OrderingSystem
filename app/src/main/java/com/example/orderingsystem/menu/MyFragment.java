package com.example.orderingsystem.menu;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.orderingsystem.R;
import com.example.orderingsystem.db.DishDBHelper;
import com.example.orderingsystem.db.DishMenuDBManager;
import com.example.orderingsystem.order.OrderOneDialog;
import com.example.orderingsystem.order.OrderedActivity;
import com.example.orderingsystem.structure.Dish;
import com.example.orderingsystem.structure.DishMenu;
import com.example.orderingsystem.structure.MyApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyFragment extends Fragment {
    public static final String TAG = "MyFragment";
    static List<Map<String, Object>> mfoodinfo = new ArrayList<>();
    private static ListView dishlist;
    MyApplication app;
    String uid;
    DishMenuDBManager dhelper;
    DishMenu[] uDishMenus;
    DishMenu dMenu = new DishMenu();
    String dishname;
    String dishnum;
    private int itemID;
    private Dish dish = new Dish();
    private Dishes dishes = new Dishes();
    private ArrayList<ArrayList<Object>> DishItem = new ArrayList<>();
    private SimpleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dhelper = new DishMenuDBManager(getActivity());
        dhelper.open();

        View view = inflater.inflate(R.layout.dishfragmentright, null);
        dishlist = view.findViewById(R.id.dishdetail);
        // 点击加载
        // 左侧栏
        DishItem = dishes.getReCaiDishes();
        mfoodinfo = dish.getFoodData(DishItem);

        adapter = new SimpleAdapter(getActivity(), mfoodinfo, R.layout.listitemright, new String[]{"dishid", "image", "title", "price"}, new int[]{R.id.dishid, R.id.img, R.id.title, R.id.price});
        adapter.notifyDataSetChanged();
        dishlist.setAdapter(adapter);
        itemID = getArguments().getInt(TAG);
        //System.out.println("左侧栏ID："+itemID);
        switch (itemID) {
            case 0:
                DishItem = dishes.getReCaiDishes();
                break;
            case 1:
                DishItem = dishes.getLiangCaiDishes();
                break;
            case 2:
                DishItem = dishes.getZhushiDishes();
                break;
            case 3:
                DishItem = dishes.getTangDishes();
                break;
            case 4:
                DishItem = dishes.getYinliaoDishes();
                break;
            default:
                break;
        }
        mfoodinfo = dish.getFoodData(DishItem);
        adapter = new SimpleAdapter(getActivity(), mfoodinfo, R.layout.listitemright, new String[]{"dishid", "image", "title", "price"}, new int[]{R.id.dishid, R.id.img, R.id.title, R.id.price});
        adapter.notifyDataSetChanged();
        dishlist.setAdapter(adapter);

        // 右侧栏表格点击
        dishlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("左侧栏id:" + itemID + " 右侧栏id：" + id);
                System.out.println(mfoodinfo.get(position) + " ");
                // 获得标题名字
                // Map<String, Object> tempdata = mfoodinfo.get(position);
                // Object title = tempdata.get("title");
                // myListener.sendValue("传值");

                Intent intent = new Intent(getActivity(), OrderedActivity.class);

                final int dishid = Integer.valueOf(String.valueOf(mfoodinfo.get(position).get("dishid")));
                dishname = mfoodinfo.get(position).get("title").toString();
                final double price = (double) mfoodinfo.get(position).get("price");

                Bundle bundle = new Bundle();
                bundle.putInt("dishid", dishid);
                bundle.putString("dishname", dishname);
                bundle.putDouble("price", price);

                app = (MyApplication) getActivity().getApplication();
                uid = app.getUid();

                DishMenu datamenu = dhelper.queryonedish(uid, dishid);
                if (datamenu != null)
                    dishnum = String.valueOf(datamenu.dishnum);
                else dishnum = "0";

                final OrderOneDialog orderDlg = new OrderOneDialog(getActivity(), dishnum);
                orderDlg.setTitle(dishname);
                orderDlg.show();

                orderDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (orderDlg.mBtnClicked == OrderOneDialog.ButtonID.BUTTON_OK) {
                            dMenu.uid = uid;
                            dMenu.dishid = dishid;
                            dMenu.dishname = dishname;
                            dMenu.dishnum = orderDlg.mNum;
                            dMenu.dishprice = Double.valueOf(price);

                            DishMenu dataMenu = dhelper.queryonedish(uid, dishid);
                            if (dataMenu == null) {
                                dhelper.insert(dMenu);
                            } else {
                                dhelper.updateNumemenu(dataMenu.uid, dataMenu.dishid, orderDlg.mNum);
                            }
                            Toast.makeText(getActivity(), dishname + " " + dMenu.dishnum + "已经加入购物车", Toast.LENGTH_SHORT).show();
                        }

                    }

                });
            }
        });
        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
