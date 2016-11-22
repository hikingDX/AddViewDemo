package com.hiking.addviewdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FragAdapter mFragAdapter;
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
    private String mCurrentAreaName ="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initJsonData();
        initDatas();
        Log.e("1",mProvinceDatas[0]);

        final FatherView fatherView= (FatherView) findViewById(R.id.father);
        initFragment();
        ViewPager vp = (ViewPager) findViewById(R.id.vp);
        vp.setAdapter(mFragAdapter);
        fatherView.setViewPager(vp,0);
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fatherView.addViewWithName(mProvinceDatas[1]);
                fatherView.addViewWithName(mProvinceDatas[2]);
                fatherView.addViewWithName("川大学哔哔哔哔哔");

            }
        });
        findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fatherView.removeViewAt(fatherView.getChildCount()-1);
            }
        });

    }
    private void addItem(){

    }
    private void delItem(){

    }
    private void initFragment() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        MyFrg myFrg1 = new MyFrg(Color.GREEN);
        MyFrg myFrg2 = new MyFrg(Color.RED);
        MyFrg myFrg3 = new MyFrg(Color.YELLOW);
        fragments.add(myFrg1);
        fragments.add(myFrg2);
        fragments.add(myFrg3);

        mFragAdapter = new FragAdapter(getSupportFragmentManager(), MainActivity.this);
        mFragAdapter.setLists(fragments);
    }

    /**
     * 解析整个Json对象，完成后释放Json对象的内存
     */
    private void initDatas()
    {
        try
        {
            JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
            mProvinceDatas = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonP = jsonArray.getJSONObject(i);// 每个省的json对象
                String province = jsonP.getString("p");// 省名字

                mProvinceDatas[i] = province;

                JSONArray jsonCs = null;
                try
                {
                    /**
                     * Throws JSONException if the mapping doesn't exist or is
                     * not a JSONArray.
                     */
                    jsonCs = jsonP.getJSONArray("c");
                } catch (Exception e1)
                {
                    continue;
                }
                String[] mCitiesDatas = new String[jsonCs.length()];
                for (int j = 0; j < jsonCs.length(); j++)
                {
                    JSONObject jsonCity = jsonCs.getJSONObject(j);
                    String city = jsonCity.getString("n");// 市名字
                    mCitiesDatas[j] = city;
                    JSONArray jsonAreas = null;
                    try
                    {
                        /**
                         * Throws JSONException if the mapping doesn't exist or
                         * is not a JSONArray.
                         */
                        jsonAreas = jsonCity.getJSONArray("a");
                    } catch (Exception e)
                    {
                        continue;
                    }

                    String[] mAreasDatas = new String[jsonAreas.length()];// 当前市的所有区
                    for (int k = 0; k < jsonAreas.length(); k++)
                    {
                        String area = jsonAreas.getJSONObject(k).getString("s");// 区域的名称
                        mAreasDatas[k] = area;
                    }
                    mAreaDatasMap.put(city, mAreasDatas);
                }

                mCitisDatasMap.put(province, mCitiesDatas);
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        mJsonObj = null;
    }

    /**
     * 从assert文件夹中读取省市区的json文件，然后转化为json对象
     */
    private void initJsonData()
    {
        try
        {
            StringBuffer sb = new StringBuffer();
            InputStream is = getAssets().open("city.json");
            int len = -1;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf)) != -1)
            {
                sb.append(new String(buf, 0, len, "gbk"));
            }
            is.close();
            mJsonObj = new JSONObject(sb.toString());
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
