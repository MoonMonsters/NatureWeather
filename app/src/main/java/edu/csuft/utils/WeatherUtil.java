package edu.csuft.utils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.csuft.activity.R;
import edu.csuft.bean.ResponseBody;
import edu.csuft.db.WeatherDao;

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
        WeatherDao.doInsertIntoDatabase(responseBody);

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

        if (type.contains("晴")) {
            resId = R.drawable.bg_qing;
        } else if (type.contains("雷")) {
            resId = R.drawable.bg_lei;
        } else if (type.contains("雨")) {
            resId = R.drawable.bg_yu;
        }  else if (type.contains("霾")) {
            resId = R.drawable.bg_mai;
        } else if (type.contains("雪")) {
            resId = R.drawable.bg_xue;
        } else {
            resId = R.drawable.bg_default;
        }

        Logger.i("TAG","天气类型-->"+type);

        return resId;
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