package com.hiking.addviewdemo;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/21.
 */
public class FragAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "MyFragmentPagerAdapter";
    // private ArrayList<OpilistItem> lists;
    private ArrayList<Fragment> mList;
    private Context mContext;

    public FragAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
        mList = new ArrayList<Fragment>();
    }

    public void setLists(ArrayList<Fragment> lists) {
        this.mList = lists;
    }

    public void UpdateList() {
        //        this.mList.clear();
        //        this.mList.addAll(arrayList);
        this.mList.remove(0);
        //        this.mList.addAll(arrayList);

        notifyDataSetChanged();
    }
    public void addFrg(int color) {
        this.mList.add(new MyFrg(color));
        notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int arg0) {
        return mList.get(arg0);
    }

    @Override
    public int getCount() {

        return mList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return PagerAdapter.POSITION_NONE;
    }



}
