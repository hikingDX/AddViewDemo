package com.hiking.addviewdemo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private FragAdapter mFragAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FatherView fatherView= (FatherView) findViewById(R.id.father);
        initFragment();
        ViewPager vp = (ViewPager) findViewById(R.id.vp);
        vp.setAdapter(mFragAdapter);
        fatherView.setViewPager(vp,0);
        mFragAdapter.mFatherView = fatherView;

    }
    private void initFragment() {
        mFragAdapter = new FragAdapter(getSupportFragmentManager(), MainActivity.this);
    }

}
