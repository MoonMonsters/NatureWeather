package edu.csuft.interfaces;

/**
 * Created by Chalmers on 2016-05-30 16:37.
 * email:qxinhai@yeah.net
 */
public class IFragment {
    /** 城市名称 */
    public static final String CITY = "city";
    /** 城市代码 */
    public static final String CITYCODE = "citycode";

    //从SplashActivity中传递数据到MainActivity去
    public static final String WEATHERDATA = "responseBody";
    //从MainActivity传递数据到Fragment中去
    public static final String FRAGMENTDATA = "fragment_data";
}
