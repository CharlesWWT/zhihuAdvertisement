package com.imooc.rvimageads;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getScreenSize();
        mRecyclerView = findViewById(R.id.id_recyclerview);

        List<String> mockDatas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mockDatas.add(i + "");
        }

        mRecyclerView.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(this));

        mRecyclerView.setAdapter(new CommonAdapter<String>(MainActivity.this,
                R.layout.item,
                mockDatas) {
            @Override
            protected void convert(ViewHolder holder, String o, int position) {
                if (position > 0 && position % 7 == 0) {
                    holder.setVisible(R.id.id_tv_title, false);
                    holder.setVisible(R.id.id_tv_desc, false);
                    holder.setVisible(R.id.id_iv_ad, true);
                } else {
                    holder.setVisible(R.id.id_tv_title, true);
                    holder.setVisible(R.id.id_tv_desc, true);
                    holder.setVisible(R.id.id_iv_ad, false);
                }
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int fPos = mLinearLayoutManager.findFirstVisibleItemPosition();
                int lPos = mLinearLayoutManager.findLastVisibleItemPosition();
                for (int i = fPos; i <= lPos; i++) {
                    View view = mLinearLayoutManager.findViewByPosition(i);
                    AdImageViewVersion1 adImageView = view.findViewById(R.id.id_iv_ad);
//                    Log.e("visible",adImageView.getVisibility()+"");
                    if (adImageView.getVisibility() == View.VISIBLE) {
//                        Log.e("getHeight", mLinearLayoutManager.getHeight() + "");
//                        Log.e("getTop", view.getTop() + "");
                        //mLinearLayoutManager.getHeight 整个recycleView的高度
                        //mLinearLayoutManager.getHeight() - view.getTop() 图片空间顶部到屏幕底部的距离
                        adImageView.setDy(mLinearLayoutManager.getHeight() - view.getTop());
                    }
                }
            }
        });
    }

    private void getScreenSize() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
// 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
//        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
//        int screenHeight = (int) (height / density);// 屏幕高度(dp)
        Log.e("width=======>", width + "");
        Log.e("height=======>", height + "");
    }


}
