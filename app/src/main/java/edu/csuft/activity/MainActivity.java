package edu.csuft.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import edu.csuft.bean.ResponseBody;
import edu.csuft.bean.Result;
import edu.csuft.bean.SimpleCity;
import edu.csuft.fragment.ContentFragment;
import edu.csuft.interfaces.IActivity;
import edu.csuft.interfaces.IFragment;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {

    /**
     * Fragment集合
     */
    private ArrayList<ContentFragment> fragmentList = null;

    /**
     * ViewPager，用来切换城市的天气
     */
    private ViewPager viewPager = null;

    /**
     * 天气数据集合
     */
    private ArrayList<ResponseBody> responseBodyList = null;

    /**
     * 滑动条
     */
    private ScrollView scrollView = null;

    /**
     * 标题显示
     */
    private TextView tv_main_title;
    /** */
    private ImageView iv_main_more;
    /** */
    private ListView lv_main_more;

    //目前处于的城市项
    private SimpleCity chooseSimpleCity = null;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    /**
     * 初始化控件操作
     */
    @Override
    public void initView() {
        tv_main_title = findView(R.id.tv_main_title);

        viewPager = findView(R.id.viewPager);
        //设置保留页数，在该数量范围内，Fragment不会被销毁
        viewPager.setOffscreenPageLimit(3);

        scrollView = findView(R.id.sv);

        iv_main_more = findView(R.id.iv_main_more);
        lv_main_more = findView(R.id.lv_main_more);
        ArrayList<String> lvMoreList = new ArrayList<>();
        lvMoreList.add("管理城市");
        lvMoreList.add("历史记录");
        ArrayAdapter lvMoreAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, lvMoreList);
        lv_main_more.setAdapter(lvMoreAdapter);
    }

    @Override
    public void initData() {
        responseBodyList = (ArrayList<ResponseBody>) getIntent().getSerializableExtra(IFragment.WEATHERDATA);
        //取出默认城市
        SharedPreferences sharedPreferences = getSharedPreferences(IActivity.DEFAULT_CITY, MODE_PRIVATE);
        String defaultCity = sharedPreferences.getString(IActivity.CITY, responseBodyList.get(0).getResult().getCity());
        String defaultCitycode = sharedPreferences.getString(IActivity.CITYCODE, responseBodyList.get(0).getResult().getCitycode());
        /*
         * 将默认显示城市放在第一个
         */
        //交换位置
        for (int i = 0; i < responseBodyList.size(); i++) {
            ResponseBody responseBody = responseBodyList.get(i);
            if (defaultCity.equals(responseBody.getResult().getCity()) &&
                    defaultCitycode.equals(responseBody.getResult().getCitycode())) {
                ResponseBody rb = responseBodyList.get(0);
                responseBodyList.set(0, responseBody);
                responseBodyList.set(i, rb);
            }
        }

        fragmentList = new ArrayList<>();

        for (int i = 0; i < responseBodyList.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(IFragment.FRAGMENTDATA, responseBodyList.get(i));

            ContentFragment instance = ContentFragment.getInstance(bundle);
            fragmentList.add(instance);
        }

        setCurrentViewPagerItem();
    }

    /**
     * 设置当前显示的选项卡
     */
    private void setCurrentViewPagerItem() {

        SharedPreferences sharedPreferences = getSharedPreferences(IActivity.CURRENT_ITEM, MODE_PRIVATE);

        /**
         * 获得默认的城市和城市编码
         */
        SharedPreferences defaultItem = getSharedPreferences(IActivity.DEFAULT_CITY, MODE_PRIVATE);
        String defaultCity = defaultItem.getString(IActivity.CITY, responseBodyList.get(0).getResult().getCity());
        String defaultCitycode = defaultItem.getString(IActivity.CITYCODE, responseBodyList.get(0).getResult().getCitycode());

        String city = sharedPreferences.getString(IActivity.CURRENT_ITEM_CITY, defaultCity);
        String citycode = sharedPreferences.getString(IActivity.CURRENT_ITEM_CITYCODE, defaultCitycode);

        for (int i = 0; i < responseBodyList.size(); i++) {
            Result result = responseBodyList.get(i).getResult();
            if (result.getCity().equals(city) &&
                    result.getCitycode().equals(citycode)) {
                viewPager.setCurrentItem(i);
                break;
            }
        }
    }

    /**
     * 携带数据进入另一个界面
     */
    private void enterManagerCityActivityWithData() {
        Intent intent = new Intent(MainActivity.this, ManagerCityActivity.class);
        intent.putExtra(IActivity.SAVE_DATA, responseBodyList);

        startActivity(intent);
        MainActivity.this.finish();
    }

    @Override
    public void initListener() {
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);

        iv_main_more.setOnClickListener(onClickListener);
        lv_main_more.setOnItemClickListener(onItemClickListener);
    }

    /**
     * ViewPager的适配器对象
     */
    FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {

            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    };

    /**
     * ViewPager改变事件
     */
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            tv_main_title.setText(responseBodyList.get(position).getResult().getCity());
            chooseSimpleCity = new SimpleCity(responseBodyList.get(position).getResult().getCity(),
                    responseBodyList.get(position).getResult().getCitycode());
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    /**
     * 点击事件
     */
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.iv_main_more) {
                //如果是显示的，那么隐藏
                if (lv_main_more.getVisibility() == View.VISIBLE) {
                    lv_main_more.setVisibility(View.GONE);
                } else { //如果是隐藏的，那么显示，并且放到顶层
                    lv_main_more.setVisibility(View.VISIBLE);
                    lv_main_more.bringToFront();
                }
            }
        }
    };

    /**
     * 选项点击事件
     */
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //如果点击的是管理城市项，则进入管理界面
            if (position == 0) {
                enterManagerCityActivityWithData();
            } else if (position == 1) {    //否则，查看该城市的历史记录

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, onDateSetListener, year, month, day);
                datePickerDialog.show();
            }

            lv_main_more.setVisibility(View.GONE);
        }
    };

    /**
     * DatePickerDailog的监听器
     */
    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date = String.valueOf(year) + String.format("%02d", monthOfYear + 1)
                    + String.format("%02d", dayOfMonth);
            //传递数据到HistoryActivity中
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            intent.putExtra(IActivity.SIMPLECITY, chooseSimpleCity);
            intent.putExtra(IActivity.DATE, date);
            startActivity(intent);
        }
    };
}