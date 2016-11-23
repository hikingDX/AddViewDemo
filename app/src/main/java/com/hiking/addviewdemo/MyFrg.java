package com.hiking.addviewdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/11/21.
 */
public class MyFrg extends Fragment {
    private int mLevel;//层级
    private FragAdapter mFragAdapter;

    private ListView mListView;
    private String[] mData;//数据源

    private LayoutInflater mLayoutInflater;

    public MyFrg() {
    }

    public MyFrg(FragAdapter fragAdapter, String[] data,int level) {
        mFragAdapter = fragAdapter;
        mData = data;
        mLevel = level;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayoutInflater = inflater;

        View view = inflater.inflate(R.layout.myfrg, container, false);

        mListView = (ListView) view.findViewById(R.id.lv);

        mListView.setAdapter(new ListViewAdapter());
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view.findViewById(R.id.tv);
textView.setTextColor(Color.RED);
        String title = textView.getText().toString();
        mFragAdapter.update(mLevel+1,title);//更新下一层级,如果是省+1就是市
        }

        });
        return view;
        }

public class ListViewAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = mLayoutInflater.inflate(R.layout.item, null);
        TextView textView = (TextView) view.findViewById(R.id.tv);
        textView.setText(mData[i]);
        return view;
    }
}
}
