package com.hiking.addviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hiking.addviewdemo.R;

import java.util.zip.Inflater;


/**
 * Created by Administrator on 2016/11/21.
 */
public class MyFrg extends Fragment {
    private int mColor;

    private ListView mListView;

    public MyFrg() {
    }

    public MyFrg(int i) {
        mColor = i;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myfrg, container, false);
        mListView = (ListView) view.findViewById(R.id.lv);
        mListView.setAdapter(new ListViewAdapter());
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }

        });
        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        view.setBackgroundColor(mColor);
        return view;
    }

    public class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
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
            TextView textView = new TextView(getContext());
            textView.setText("hello world!");
//            LayoutInflater layoutInflater =
            return textView;
        }
    }
}
