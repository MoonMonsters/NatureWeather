package edu.csuft.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

import java.util.ArrayList;

import edu.csuft.bean.ResponseBody;
import edu.csuft.db.SqlDbHelper;
import edu.csuft.interfaces.IFragment;
import edu.csuft.utils.WeatherUtil;

/**
 * 显示图片，在显示期间后台加载数据
 */
public class SplashActivity extends BaseActivity {

    private ImageView iv_loading = null;

    private SqlDbHelper helper = null;

    @Override
    public void initView() {
        iv_loading = (ImageView) findViewById(R.id.iv_loading);

        initLoadingAnim();
    }

    /**
     * 设置loading图片
     */
    private void initLoadingAnim(){
        //设置背景图片资源，不要设置错方法了
        iv_loading.setImageResource(R.drawable.anim_loading);
        //转换成AnimationDrawable 类
        AnimationDrawable background = (AnimationDrawable) iv_loading.getDrawable();
        //启动动画
        background.start();
    }

    private void enterMainActivity(ArrayList<ResponseBody> responseBodyList){
        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
        //携带数据到MainActivity中去
        intent.putExtra(IFragment.WEATHERDATA,responseBodyList);
        startActivity(intent);

        //关闭此界面
        SplashActivity.this.finish();
    }

    @Override
    public void initData() {

        helper = new SqlDbHelper(this);

        new Thread(new Runnable() {
            @Override
            public void run() {

                SQLiteDatabase readableDatabase = helper.getReadableDatabase();
                Cursor cursor = readableDatabase.query("weather_city", new String[]{"city", "citycode"}, null, null, null, null, null);

                //获得城市数目
                int count = cursor.getCount();

                if(count == 0){     //如果数据库没有数据，则定位取得
                    Intent intent = new Intent(SplashActivity.this,ManagerCityActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }else{      //否则从数据库中取得
                    int index = 0;
                    String cities[] = new String[count];
                    String citycodes[] = new String[count];

                    //取出所有城市数据
                    while (cursor.moveToNext()){
                        String city = cursor.getString(cursor.getColumnIndex("city"));
                        String citycode = cursor.getString(cursor.getColumnIndex("citycode"));
                        cities[index] = city;
                        citycodes[index] = citycode;
                        index ++;
                    }

                    ArrayList<ResponseBody> responseBodyList = new ArrayList<>();
                    for(int i=0; i<count; i++){
                        ResponseBody responseBody = WeatherUtil.request(cities[i],citycodes[i]);
                        responseBodyList.add(responseBody);
                    }
                    //进入主界面
                    enterMainActivity(responseBodyList);
                }

            }
        }).start();
    }

    @Override
    public void initListener() {

    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_splash;
    }

}