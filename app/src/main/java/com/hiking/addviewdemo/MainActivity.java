package com.hiking.addviewdemo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private FragAdapter mFragAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FatherView fatherView= (FatherView) findViewById(R.id.father);
        initFragment();
        ViewPager vp = (ViewPager) findViewById(R.id.vp);
        vp.setAdapter(mFragAdapter);
        fatherView.setViewPager(vp,0);
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fatherView.addViewWithName("hello world");
//                mFragAdapter.addFrg();
            }
        });
        findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fatherView.removeViewAt(fatherView.getChildCount()-1);
                mFragAdapter.delFrg();
            }
        });

    }
    private void initFragment() {
        mFragAdapter = new FragAdapter(getSupportFragmentManager(), MainActivity.this);
    }

}
