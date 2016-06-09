package edu.csuft.interfaces;

/**
 * Created by Chalmers on 2016-06-05 21:11.
 * email:qxinhai@yeah.net
 */

/**
 * 在Activity中需要用上的变量
 */
public class IActivity {
    /**
     * SharedPreference的变量名
     */
    public static String DEFAULT_CITY = "default_city";
    /** 城市名 */
    public static String CITY = "city";
    /** 城市编码 */
    public static String CITYCODE = "citycode";
    /** 获得之前保存的数据 */
    public static String SAVE_DATA = "save_data";
    /** 设置ViewPager的当前界面 */
    public static String CUR_ITEM = "cur_item";
    /** 传递的对象 */
    public static String SIMPLECITY = "simple_city";
    /** 传递的时间 */
    public static String DATE = "date";
    /** 当前显示选项卡，当添加完城市后显示添加后的城市 */
    public static String CURRENT_ITEM = "current_item";
    public static String CURRENT_ITEM_CITY = "current_item_city";
    public static String CURRENT_ITEM_CITYCODE = "current_item_citycode";
}