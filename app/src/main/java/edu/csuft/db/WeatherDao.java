package edu.csuft.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.csuft.application.MyApplication;
import edu.csuft.bean.Aqi;
import edu.csuft.bean.Index;
import edu.csuft.bean.ResponseBody;
import edu.csuft.bean.Result;
import edu.csuft.bean.SimpleCity;
import edu.csuft.utils.Logger;

/**
 * Created by Chalmers on 2016-06-13 23:34.
 * email:qxinhai@yeah.net
 */
public class WeatherDao {
    /**
     * 从数据库中删除数据
     *
     * @param city 城市名
     * @param citycode 城市编号
     */
    public static void doDeleteDataFromDatabase(final String city, final String citycode) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                SqlDbHelper helper = new SqlDbHelper(MyApplication.getContext());

                SQLiteDatabase writableDatabase = helper.getWritableDatabase();

                //删除weather_city中的数据
                writableDatabase.delete("weather_city", "city=? and citycode=?", new String[]{city, citycode});
                //从weather_index中删除数据
                writableDatabase.delete("weather_index", "city=? and citycode=?", new String[]{city, citycode});
                //从weather_aqi表中删除数据
                writableDatabase.delete("weather_aqi", "city=? and citycode=?", new String[]{city, citycode});
                //从weather_daily表中删除数据
                writableDatabase.delete("weather_daily", "city=? and citycode=?", new String[]{city, citycode});

                writableDatabase.close();
            }
        }).start();
    }

    /**
     * 将数据插入到数据库中
     *
     * @param responseBody 插入内容
     */
    public static void doInsertIntoDatabase(final ResponseBody responseBody) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //得到对象
                Result result = responseBody.getResult();
                Aqi aqi = result.getAqi();
                ArrayList<Index> indexList = result.getIndex();
                //得到存入数据库时的时间
                String time = getTodayTime();

                SqlDbHelper helper = new SqlDbHelper(MyApplication.getContext());

                SQLiteDatabase readableDatabase = helper.getReadableDatabase();
                Logger.i("DATABASE", "数据库创建完成？");
                Cursor cursor = readableDatabase.query("weather_daily", null, "date=? and city=? and citycode=?",
                        new String[]{time, result.getCity(), result.getCitycode()}, null, null, null);
                //如果该天已经存入一次数据，那么直接返回
                if (cursor.getCount() != 0) {
                    cursor.close();
                    readableDatabase.close();
                    return;
                } else {  //否则，存入数据库中
                    SQLiteDatabase writableDatabase = helper.getWritableDatabase();

                    /*
                        向aqi表插入数据
                    */
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("city", result.getCity());
                    contentValues.put("citycode", result.getCitycode());
                    contentValues.put("date", time);
                    contentValues.put("level", aqi.getAqiinfo().getLevel());
                    contentValues.put("aqi", aqi.getAqi());
                    contentValues.put("quality", aqi.getQuality());
                    contentValues.put("affect", aqi.getAqiinfo().getAffect());
                    contentValues.put("measure", aqi.getAqiinfo().getMeasure());
                    contentValues.put("pressure", result.getPressure());
                    writableDatabase.insert("weather_aqi", null, contentValues);

                    /*
                        向index中插入数据
                    */
                    for (Index index : indexList) {
                        ContentValues contentValues2 = new ContentValues();
                        contentValues2.put("city", result.getCity());
                        contentValues2.put("citycode", result.getCitycode());
                        contentValues2.put("date", time);
                        contentValues2.put("iname", index.getIname());
                        contentValues2.put("ivalue", index.getIvalue());
                        contentValues2.put("detail", index.getDetail());
                        writableDatabase.insert("weather_index", null, contentValues2);
                    }

                    /*
                        向daily中插入数据
                    */
                    ContentValues contentValues3 = new ContentValues();
                    contentValues3.put("city", result.getCity());
                    contentValues3.put("citycode", result.getCitycode());
                    contentValues3.put("date", time);
                    contentValues3.put("weather", result.getWeather());
                    contentValues3.put("temp", result.getTemp());
                    contentValues3.put("temphigh", result.getTemphigh());
                    contentValues3.put("templow", result.getTemplow());
                    contentValues3.put("winddirect", result.getWinddirect());
                    contentValues3.put("windpower", result.getWindpower());
                    contentValues3.put("updatetime", result.getUpdatetime());
                    contentValues3.put("humidity", result.getHumidity());
                    writableDatabase.insert("weather_daily", null, contentValues3);

                    writableDatabase.close();
                }
            }
        }).start();
    }

    /**
     * 得到今天的时间 格式为 20160606
     *
     * @return
     */
    private static String getTodayTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String time = sdf.format(new Date());

        return time;
    }

    /**
     * 向weather_city表插入数据
     * @param city 城市名
     * @param citycode 城市编码
     */
    public static void doInsertIntoWeatherCity(String city, String citycode){
        SqlDbHelper helper = new SqlDbHelper(MyApplication.getContext());
        /**
         * 向数据库中写入城市名和城市编码
         */
        SQLiteDatabase writableDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("city",city);
        values.put("citycode",citycode);
        writableDatabase.insert("weather_city",null,values);
        writableDatabase.close();
    }

    /**
     * 从数据库weather_city中获得所有城市
     * @return SimpleCity集合
     */
    public static ArrayList<SimpleCity> getSimpleCityListFromDB(){
        SqlDbHelper helper = new SqlDbHelper(MyApplication.getContext());
        ArrayList<SimpleCity> simpleCityList = new ArrayList<>();

        SQLiteDatabase readableDatabase = helper.getReadableDatabase();
        Cursor cursor = readableDatabase.query("weather_city", new String[]{"city", "citycode"}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String city = cursor.getString(cursor.getColumnIndex("city"));
            String citycode = cursor.getString(cursor.getColumnIndex("citycode"));
            simpleCityList.add(new SimpleCity(city,citycode));
        }

        cursor.close();
        readableDatabase.close();

        return simpleCityList;
    }
}
