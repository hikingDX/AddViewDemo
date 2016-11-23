package com.hiking.addviewdemo;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/21.
 */
public class FragAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "MyFragmentPagerAdapter";

    public FatherView mFatherView;
    private static final int CITY = 1;
    private static final int AREA = 2;

    private ArrayList<Fragment> mList; //Fragment集合
    private Context mContext;
    /**
     * 把全国的省市区的信息以json的格式保存，解析完成后赋值为null
     */
    private JSONObject mJsonObj;
    /**
     * 所有省
     */
    private String[] mProvinceDatas;
    /**
     * key - 省 value - 市s
     */
    private Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区s
     */
    private Map<String, String[]> mAreaDatasMap = new HashMap<String, String[]>();
    /**
     * 当前省的名称
     */
    private String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    private String mCurrentCityName;
    /**
     * 当前区的名称
     */
    private String mCurrentAreaName = "";

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        mContext = context;
        //解析整合数据
        initJsonData();
        initDatas();
        mList = new ArrayList<Fragment>();
        MyFrg myFrg = new MyFrg(this, mProvinceDatas,0);
        mList.add(myFrg);
    }

    public FragAdapter(FragmentManager fm, Context context) {
        super(fm);
        init(context);
    }

    /**
     * 删除Frg
     */
    public void delFrg(int level) {
        for (int i=mList.size()-1;i>level;i--){
            mList.remove(i);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加Frg
     */
    public void addFrg(String[] arr,int level) {
        this.mList.add(new MyFrg(this,arr,level));
        mFatherView.addViewWithName("请选择");
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
        //很重要 不然无法动态添加 删除Frg
        return PagerAdapter.POSITION_NONE;
    }

    /**
     * --------------------------------------------------------获取数据-----------------------------------------------------
     */
    public void update(int level, String title) {
        switch (level){
            case CITY:{
                //设置上一层级 title
                mFatherView.setTitle(title,level-1);

                mCurrentProviceName = title;
                String[] citys = mCitisDatasMap.get(mCurrentProviceName);
                delFrg(level-1);//删除省级以后的层级
                addFrg(citys,level);
                break;
            }
            case AREA:{
                //设置上一层级 title
                mFatherView.setTitle(title,level-1);
                mCurrentCityName = title;
                String[] areas = mAreaDatasMap.get(mCurrentCityName);
                delFrg(level-1);
                if (areas!=null) {
                    addFrg(areas, level);
                }
                break;
            }
            default:
                break;
        }
    }

    /**
     * 解析整个Json对象，完成后释放Json对象的内存
     */
    private void initDatas() {
        try {
            JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
            mProvinceDatas = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonP = jsonArray.getJSONObject(i);// 每个省的json对象
                String province = jsonP.getString("p");// 省名字

                mProvinceDatas[i] = province;

                JSONArray jsonCs = null;
                try {
                    /**
                     * Throws JSONException if the mapping doesn't exist or is
                     * not a JSONArray.
                     */
                    jsonCs = jsonP.getJSONArray("c");
                } catch (Exception e1) {
                    continue;
                }
                String[] mCitiesDatas = new String[jsonCs.length()];
                for (int j = 0; j < jsonCs.length(); j++) {
                    JSONObject jsonCity = jsonCs.getJSONObject(j);
                    String city = jsonCity.getString("n");// 市名字
                    mCitiesDatas[j] = city;
                    JSONArray jsonAreas = null;
                    try {
                        /**
                         * Throws JSONException if the mapping doesn't exist or
                         * is not a JSONArray.
                         */
                        jsonAreas = jsonCity.getJSONArray("a");
                    } catch (Exception e) {
                        continue;
                    }

                    String[] mAreasDatas = new String[jsonAreas.length()];// 当前市的所有区
                    for (int k = 0; k < jsonAreas.length(); k++) {
                        String area = jsonAreas.getJSONObject(k).getString("s");// 区域的名称
                        mAreasDatas[k] = area;
                    }
                    mAreaDatasMap.put(city, mAreasDatas);
                }

                mCitisDatasMap.put(province, mCitiesDatas);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mJsonObj = null;
    }

    /**
     * 从assert文件夹中读取省市区的json文件，然后转化为json对象
     */
    private void initJsonData() {
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = mContext.getResources().getAssets().open("city.json");
            int len = -1;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "gbk"));
            }
            is.close();
            mJsonObj = new JSONObject(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
