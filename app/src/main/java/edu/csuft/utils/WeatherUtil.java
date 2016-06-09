package edu.csuft.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.csuft.activity.R;
import edu.csuft.application.MyApplication;
import edu.csuft.bean.Aqi;
import edu.csuft.bean.Index;
import edu.csuft.bean.ResponseBody;
import edu.csuft.bean.Result;
import edu.csuft.db.SqlDbHelper;

/**
 * Created by Chalmers on 2016-05-29 12:42.
 * email:qxinhai@yeah.net
 */

/**
 * 跟天气相关的工具类
 */
public class WeatherUtil {

    /**
     * 解析所有天气json数据
     *
     * @param data 天气数据
     */
    public static ResponseBody parseJsonData(String data) {
        Gson gson = new Gson();
        ResponseBody responseBody = gson.fromJson(data, ResponseBody.class);

        return responseBody;
    }

    public static ResponseBody request(String city, String citycode) {
        String httpUrl = "http://apis.baidu.com/netpopo/weather/query";
        String httpArg = "city=" + city + "&citycode=" + citycode;

        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", "794c63afab0452b472eb7d4f2c334a40");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();

            Logger.i("TAG", result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResponseBody responseBody = parseJsonData(result);

        //插入数据
        doInsertIntoDatabase(responseBody);

        return responseBody;
    }

    /**
     * 根据index的iname，得到相应的图片
     *
     * @param iname 指数名称
     * @return 对应的图片
     */
    public static int getIndexPicFromIname(String iname) {
        int resId = 0;

        switch (iname) {
            case "空调指数":
                resId = R.drawable.icon_index_kt;
                break;
            case "运动指数":
                resId = R.drawable.icon_index_yd;
                break;
            case "紫外线指数":
                resId = R.drawable.icon_index_zwx;
                break;
            case "感冒指数":
                resId = R.drawable.icon_index_gm;
                break;
            case "洗车指数":
                resId = R.drawable.icon_index_xc;
                break;
            case "穿衣指数":
                resId = R.drawable.icon_index_cy;
                break;
        }

        return resId;
    }

    /**
     * 根据天气类型，返回对应图片资源
     *
     * @param type 天气类型
     * @return 图片资源id
     */
    public static int getWeatherPicFromType(String type) {
        int resId = 0;

        switch (type) {
            case "晴":
                resId = R.drawable.icon_daily_qing;
                break;
            case "多云":
                resId = R.drawable.icon_daily_duoyun;
                break;
            case "阴":
                resId = R.drawable.icon_daily_yin;
                break;
            case "阵雨":
                resId = R.drawable.icon_daily_zhenyu;
                break;
            case "雷阵雨":
                resId = R.drawable.icon_daily_leizhenyu;
                break;
            case "雷阵雨伴有冰雹":
                resId = R.drawable.icon_daily_leizhenyubingbao;
                break;
            case "雨夹雪":
                resId = R.drawable.icon_daily_yujiaxue;
                break;
            case "小雨":
                resId = R.drawable.icon_daily_xiaoyu;
                break;
            case "中雨":
                resId = R.drawable.icon_daily_zhongyu;
                break;
            case "大雨":
                resId = R.drawable.icon_daily_zhongyu;
                break;
            case "暴雨":
                resId = R.drawable.icon_daily_baoyu;
                break;
            case "特大暴雨":
                resId = R.drawable.icon_daily_tedabaoyu;
                break;
            case "阵雪":
                resId = R.drawable.icon_daily_zhenxue;
                break;
            case "小雪":
                resId = R.drawable.icon_daily_xiaoxue;
                break;
            case "中雪":
                resId = R.drawable.icon_daily_zhongxue;
                break;
            case "大雪":
                resId = R.drawable.icon_daily_daxue;
                break;
            case "暴雪":
                resId = R.drawable.icon_daily_baoxue;
                break;
            case "冻雨":
                resId = R.drawable.icon_daily_dongyu;
                break;
            case "雾":
                resId = R.drawable.icon_daily_wu;
                break;
            case "霾":
                resId = R.drawable.icon_daily_mai;
                break;
            default:
                resId = R.drawable.icon_daily_default;
                break;
        }

        return resId;
    }

    /**
     * 根据天气返回对应的图片资源id
     *
     * @param type 天气类型
     * @return 图片资源id
     */
    public static int getBgPicFromWeatherType(String type) {
        int resId = 0;

//        if (type.contains("晴")) {
            resId = R.drawable.bg_qing;
//        } else if (type.contains("雷")) {
//            resId = R.drawable.bg_lei;
//        } else if (type.contains("雨")) {
//            resId = R.drawable.bg_yu;
//        }  else if (type.contains("霾")) {
//            resId = R.drawable.bg_mai;
//        } else if (type.contains("雪")) {
//            resId = R.drawable.bg_xue;
//        } else {
//            resId = R.drawable.bg_default;
//        }

        return resId;
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
                    readData();
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

    //TODO 删除
    private static void readData() {
        SqlDbHelper helper = new SqlDbHelper(MyApplication.getContext());
        SQLiteDatabase readableDatabase = helper.getReadableDatabase();

        Cursor cursor = readableDatabase.query("weather_daily", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Logger.i("DATABASE", cursor.getString(cursor.getColumnIndex("city")));
            Logger.i("DATABASE", cursor.getString(cursor.getColumnIndex("citycode")));
            Logger.i("DATABASE", cursor.getString(cursor.getColumnIndex("winddirect")));
        }
        cursor.close();
        readableDatabase.close();
    }

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
     * 根据aqi的值来选择对应的图片
     *
     * @param value aqi的值
     * @return 图片资源的id
     */
    public static int getAqiPicFromValue(String value) {
        int resId = 0;
        int result = Integer.parseInt(value);

        if (result <= 50) {
            resId = R.drawable.notif_level1;
        } else if (result > 50 && result <= 100) {
            resId = R.drawable.notif_level2;
        } else if (result > 100 && result <= 150) {
            resId = R.drawable.notif_level3;
        } else if (result > 150 && result <= 200) {
            resId = R.drawable.notif_level4;
        } else if (result > 200 && result <= 300) {
            resId = R.drawable.notif_level5;
        } else {
            resId = R.drawable.notif_level6;
        }

        return resId;
    }
}