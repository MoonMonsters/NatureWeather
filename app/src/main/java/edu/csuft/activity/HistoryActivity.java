package edu.csuft.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import edu.csuft.adapter.IndexAdapter;
import edu.csuft.bean.Aqi;
import edu.csuft.bean.AqiInfo;
import edu.csuft.bean.Index;
import edu.csuft.bean.SimpleCity;
import edu.csuft.db.SqlDbHelper;
import edu.csuft.interfaces.IActivity;
import edu.csuft.utils.Logger;
import edu.csuft.utils.WeatherUtil;
import edu.csuft.view.AqiDetailDialog;
import edu.csuft.view.IndexDetailsDialog;

public class HistoryActivity extends BaseActivity {

    private SimpleCity simpleCity = null;
    private String date = null;
    private SqlDbHelper helper = null;

    /**
     * aqi级别代表图片
     */
    private ImageView iv_today_aqi;
    /**
     * aqi的数值
     */
    private TextView tv_today_aqi;
    /**
     * 当前温度
     */
    private TextView tv_today_cur_temp;
    /**
     * 更新时间
     */
    private TextView tv_today_update_time;
    /**
     * 天气类型
     */
    private TextView tv_today_type;
    /**
     * 最高温度
     */
    private TextView tv_today_max_temp;
    /**
     * 最低温度
     */
    private TextView tv_today_min_temp;
    /**
     * 风向
     */
    private TextView tv_today_wind_direct;
    /**
     * 风力
     */
    private TextView tv_today_wind_power;
    /**
     * 湿度
     */
    private TextView tv_today_humidity;
    //布局，设置点击事件打开aqi的详细信息
    private LinearLayout layout_aqi;

    //GridView的对象,用来显示指数
    private GridView gv_weather_index = null;

    //布局，用来设置背景图片
    private LinearLayout layout_fragment_bg = null;

    private TextView tv_today_show_time = null;

    private Aqi aqi = null;
    private ArrayList<Index> indexList = null;
    private String pressure = null;

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 初始化组件信息
     *
     */
    @Override
    public void initView() {
        iv_today_aqi = (ImageView) findViewById(R.id.iv_today_aqi);
        tv_today_aqi = (TextView) findViewById(R.id.tv_today_aqi);
        tv_today_cur_temp = (TextView) findViewById(R.id.tv_today_cur_temp);
        tv_today_update_time = (TextView) findViewById(R.id.tv_today_update_time);
        tv_today_type = (TextView) findViewById(R.id.tv_today_type);
        tv_today_max_temp = (TextView) findViewById(R.id.tv_today_max_temp);
        tv_today_min_temp = (TextView) findViewById(R.id.tv_today_min_temp);
        tv_today_wind_direct = (TextView) findViewById(R.id.tv_today_wind_direct);
        tv_today_wind_power = (TextView) findViewById(R.id.tv_today_wind_power);
        tv_today_humidity = (TextView) findViewById(R.id.tv_today_humidity);
        layout_aqi = (LinearLayout) findViewById(R.id.layout_aqi);

        gv_weather_index = (GridView) findViewById(R.id.gv_weather_index);
        layout_fragment_bg = (LinearLayout) findViewById(R.id.layout_fragment_bg);
        tv_today_show_time = (TextView) findViewById(R.id.tv_today_show_time);

        Intent intent = getIntent();
        date = intent.getStringExtra(IActivity.DATE);
        simpleCity = (SimpleCity) intent.getSerializableExtra(IActivity.SIMPLECITY);

        helper = new SqlDbHelper(this);
    }

    @Override
    public void initData() {
        //显示时间
        tv_today_show_time.setText(getTopShowTime());

        SQLiteDatabase readableDatabase = helper.getReadableDatabase();

        /*
        读取aqi中的数据
         */
        Cursor cursorAqi = readableDatabase.query("weather_aqi", null, "city=? and citycode=? and date=?",
                new String[]{simpleCity.getCity(), simpleCity.getCitycode(), date}, null, null, null);
        if(cursorAqi.moveToNext()){
            String aqiValue = cursorAqi.getString(cursorAqi.getColumnIndex("aqi"));
            String quality = cursorAqi.getString(cursorAqi.getColumnIndex("quality"));
            String affect = cursorAqi.getString(cursorAqi.getColumnIndex("affect"));
            String measure = cursorAqi.getString(cursorAqi.getColumnIndex("measure"));
            pressure = cursorAqi.getString(cursorAqi.getColumnIndex("pressure"));

            aqi = new Aqi();
            AqiInfo aqiInfo = new AqiInfo();
            aqi.setAqiinfo(aqiInfo);
            aqi.setAqi(aqiValue);
            aqi.setQuality(quality);
            aqi.getAqiinfo().setMeasure(measure);
            aqi.getAqiinfo().setAffect(affect);
        }

        /*
         读取index相关数据
         */
        indexList = new ArrayList<>();
        Cursor cursorIndex = readableDatabase.query("weather_index", null, "city=? and citycode=? and date=?", new String[]{simpleCity.getCity(),simpleCity.getCitycode(),date}, null, null, null);
        while(cursorIndex.moveToNext()){
            String iname = cursorIndex.getString(cursorIndex.getColumnIndex("iname"));
            String ivalue = cursorIndex.getString(cursorIndex.getColumnIndex("ivalue"));
            String detail = cursorIndex.getString(cursorIndex.getColumnIndex("detail"));

            Index index = new Index();
            index.setIname(iname);
            index.setIvalue(ivalue);
            index.setDetail(detail);

            indexList.add(index);
        }

        /*
        获得顶部数据
         */
        Cursor cursorMain = readableDatabase.query("weather_daily", null, "city=? and citycode=? and date=?",
                new String[]{simpleCity.getCity(),simpleCity.getCitycode(),date}, null, null, null);

        setMainData(cursorMain);

        setAqiData();
        setIndexData();

        cursorAqi.close();
        cursorIndex.close();
        cursorMain.close();
        readableDatabase.close();
    }

    /**
     * 设置顶部的数据
     * @param cursorMain 游标
     */
    private void setMainData(Cursor cursorMain) {
        if(cursorMain.moveToNext()){
            Logger.i("TAG",cursorMain.getCount()+"");
            String weather = cursorMain.getString(cursorMain.getColumnIndex("weather"));
            tv_today_type.setText(weather);
            Logger.i("TAG",weather);
            layout_fragment_bg.setBackgroundResource(WeatherUtil.getBgPicFromWeatherType(weather));

            String temphigh = cursorMain.getString(cursorMain.getColumnIndex("temphigh"));
            tv_today_max_temp.setText(temphigh + "°");

            String templow = cursorMain.getString(cursorMain.getColumnIndex("templow"));
            tv_today_min_temp.setText(templow + "°");

            String winddirect = cursorMain.getString(cursorMain.getColumnIndex("winddirect"));
            tv_today_wind_direct.setText(winddirect);

            String windpower = cursorMain.getString(cursorMain.getColumnIndex("windpower"));
            tv_today_wind_power.setText(windpower);

            String updatetime = cursorMain.getString(cursorMain.getColumnIndex("updatetime"));
            tv_today_update_time.setText(getUpdateTime(updatetime));

            String humidity = cursorMain.getString(cursorMain.getColumnIndex("humidity"));
            tv_today_humidity.setText(humidity + "%");
        }
    }

    /**
     * 设置指数相关数据
     */
    private void setIndexData() {
        IndexAdapter indexAdapter = new IndexAdapter(indexList,HistoryActivity.this);
        gv_weather_index.setAdapter(indexAdapter);
    }

    /**
     * 设置aqi相关数据
     */
    private void setAqiData(){
        Logger.i("TAG","setAqiData");
        iv_today_aqi.setImageResource(WeatherUtil.getAqiPicFromValue(aqi.getAqi()));
        Logger.i("TAG",aqi.getAqi()+"");
        tv_today_aqi.setText(aqi.getAqi() + " " + aqi.getQuality());
    }

    @Override
    public void initListener() {
        layout_aqi.setOnClickListener(onClickListener);
        gv_weather_index.setOnItemClickListener(onItemClickListener);
        tv_today_show_time.setOnClickListener(onClickListener);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_history;
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    private String getUpdateTime(String time){
        int index = time.indexOf(' ');
        String t = time.substring(index+1);

        return t;
    }

    private String getTopShowTime(){
        String y = date.substring(0,4);
        String m = date.substring(4,6);
        String d = date.substring(6,8);

        return y + "-" + m + "-" + d;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.layout_aqi){
                AqiDetailDialog aqiDetailDialog = new AqiDetailDialog(HistoryActivity.this,aqi,pressure);
                aqiDetailDialog.show();
            }else if(v.getId() == R.id.tv_today_show_time){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(HistoryActivity.this,onDateSetListener,year,month,day);
                datePickerDialog.show();
            }
        }
    };

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            IndexDetailsDialog indexDetailsDialog = new IndexDetailsDialog(HistoryActivity.this,indexList.get(position));
            indexDetailsDialog.show();
        }
    };

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date = String.valueOf(year) + String.format("%02d",monthOfYear+1) + String.format("%02d",dayOfMonth);
            initData();
        }
    };
}