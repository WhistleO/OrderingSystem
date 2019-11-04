package com.example.orderingsystem.menu;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orderingsystem.R;

public class DishListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static int mPosition;
    private String[] strs = {"热菜", "凉菜", "主食", "汤", "饮料"};
    private ListView listView;
    private MyAdapter adapter;
    private MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menulist);
        initView();
    }


    private void initView() {
        listView = findViewById(R.id.categorylistleft);
        ArrayAdapter<String> adapterList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strs);
        listView.setAdapter(adapterList);
        listView.setOnItemClickListener(this);

        myFragment = new MyFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, myFragment);
        Bundle bundle = new Bundle();
        bundle.putInt(MyFragment.TAG, mPosition);
        myFragment.setArguments(bundle);
        fragmentTransaction.commit();
    }

    // 更换菜单列表内容
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mPosition = position;
        MyFragment dishFragment = new MyFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, dishFragment);
        Bundle bundle = new Bundle();
        bundle.putInt(MyFragment.TAG, mPosition);
        dishFragment.setArguments(bundle);
        fragmentTransaction.commit();
        //System.out.println("ssss" + position);
    }
}
