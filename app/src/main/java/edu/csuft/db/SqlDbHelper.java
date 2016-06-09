package edu.csuft.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.csuft.utils.Logger;

/**
 * Created by Chalmers on 2016-06-02 19:10.
 * email:qxinhai@yeah.net
 */
public class SqlDbHelper extends SQLiteOpenHelper{

    //数据库名
    public final static String DB_NAME = "NatureWeather.db";
    //版本号
    public final static int VERSION = 1;

    /**
     * 当日天气情况，显示在最上方的位置
     * id 主键
     * city 城市名
     * citycode 城市代码
     * date 时间 20160606
     * weather 天气，汉字描述
     * temp 当前天气
     * temphigh 最高温度
     * templow 最低温度
     * winddirect 风向
     * windpower 风力
     * updatetime 更新时间
     */
    public final String CREATE_TABLE_WEATHER_DAILY = "create table weather_daily(" +
            "id integer primary key autoincrement, " +
            "city text, " +
            "citycode text, " +
            "date text, " +
            "weather text, " +
            "temp text, " +
            "temphigh text, " +
            "templow text, " +
            "winddirect text, " +
            "windpower text, " +
            "updatetime text, " +
            "humidity text" +
            ")";

    /**
     * 指数数据表
     * id 主键
     * city 城市名
     * citycode 城市代码
     * date 时间 20160606
     * iname 指数名称
     * ivalue 指数简单解释
     * detail 指数详细解释
     */
    public final String CREATE_TABLE_WEATHER_INDEX = "create table weather_index(" +
            "id integer primary key autoincrement, " +
            "city text, " +
            "citycode text, "+
            "date text, " +
            "iname text, "+
            "ivalue text, " +
            "detail text" +
            ")";

    /**
     * 当前aqi介绍
     * id 主键
     * citycode 城市代码
     * level aqi级别，按官方说明，分成六级
     * affect 对健康的影响
     * measure 建议采取的措施
     * color aqi级别颜色
     * date 时间 20160606
     * humidity 湿度
     * pressure 气压
     * quality aqi评价
     * aqi aqi评分
     */
    public final String CREATE_TABLE_WEATHER_AQI = "create table weather_aqi(" +
            "id integer primary key autoincrement, " +
            "city text, " +
            "citycode text, " +
            "date text, " +
            "level text, " +
            "aqi text, " +
            "quality text, " +
            "affect text, " +
            "measure text, " +
            "pressure text" +
            ")";

    /**
     * 创建城市数据表，在加载数据时，需要加载哪些城市的数据
     * id 主键
     * city 城市名
     * citycode 城市代码
     */
    public final String CREATE_TABLE_WEATHER_CITY = "create table weather_city(" +
            "id integer primary key autoincrement, " +
            "city text, " +
            "citycode text" +
            ")";


    public SqlDbHelper(Context context){
        this(context,SqlDbHelper.DB_NAME,null,SqlDbHelper.VERSION);
    }

    public SqlDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SqlDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        创建表
         */
        Logger.i("DATABASE","创建数据库...");
        db.execSQL(CREATE_TABLE_WEATHER_AQI);
        db.execSQL(CREATE_TABLE_WEATHER_DAILY);
        db.execSQL(CREATE_TABLE_WEATHER_INDEX);
        db.execSQL(CREATE_TABLE_WEATHER_CITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}