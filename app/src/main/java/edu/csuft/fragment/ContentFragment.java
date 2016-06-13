package edu.csuft.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import edu.csuft.activity.R;
import edu.csuft.adapter.HourlyAdapter;
import edu.csuft.adapter.IndexAdapter;
import edu.csuft.bean.Daily;
import edu.csuft.bean.Hourly;
import edu.csuft.bean.Index;
import edu.csuft.bean.ResponseBody;
import edu.csuft.interfaces.IFragment;
import edu.csuft.utils.WeatherUtil;
import edu.csuft.view.AqiDetailDialog;
import edu.csuft.view.HorizontalListView;
import edu.csuft.view.IndexDetailsDialog;
import edu.csuft.view.LineGraphicView;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * Fragment
 * 该Fragment对应所有的ViewPager显示
 */
public class ContentFragment extends Fragment {

    private ResponseBody responseBody = null;

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

    //横向ListView，用来记录每小时的天气
    private HorizontalListView hlv_weather_hourly = null;

    //GridView的对象,用来显示指数
    private GridView gv_weather_index = null;

    //布局，用来设置背景图片
    private LinearLayout layout_fragment_bg = null;

    //
    LineGraphicView line_graphic = null;
    LineGraphicView line_graphic2 = null;

    public ContentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_content, container, false);

        initView(view);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        responseBody = (ResponseBody) bundle.getSerializable(IFragment.FRAGMENTDATA);
    }

    @Override
    public void onStart() {
        super.onStart();

        initData();
        initListener();
    }

    /**
     * 初始化组件信息
     *
     * @param view
     */
    private void initView(View view) {
        iv_today_aqi = (ImageView) view.findViewById(R.id.iv_today_aqi);
        tv_today_aqi = (TextView) view.findViewById(R.id.tv_today_aqi);
        tv_today_cur_temp = (TextView) view.findViewById(R.id.tv_today_cur_temp);
        tv_today_update_time = (TextView) view.findViewById(R.id.tv_today_update_time);
        tv_today_type = (TextView) view.findViewById(R.id.tv_today_type);
        tv_today_max_temp = (TextView) view.findViewById(R.id.tv_today_max_temp);
        tv_today_min_temp = (TextView) view.findViewById(R.id.tv_today_min_temp);
        tv_today_wind_direct = (TextView) view.findViewById(R.id.tv_today_wind_direct);
        tv_today_wind_power = (TextView) view.findViewById(R.id.tv_today_wind_power);
        tv_today_humidity = (TextView) view.findViewById(R.id.tv_today_humidity);
        layout_aqi = (LinearLayout) view.findViewById(R.id.layout_aqi);

        hlv_weather_hourly = (HorizontalListView) view.findViewById(R.id.hlv_weather_hourly);
        gv_weather_index = (GridView) view.findViewById(R.id.gv_weather_index);

        layout_fragment_bg = (LinearLayout) view.findViewById(R.id.layout_fragment_bg);
        line_graphic = (LineGraphicView) view.findViewById(R.id.line_graphic);
        line_graphic2 = (LineGraphicView) view.findViewById(R.id.line_graphic2);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        initViewData();
        initHorizontalViewData();
        initListViewData();
        initGridViewData();
    }

    /**
     * 初始化当前天气的View中的数据
     */
    private void initViewData() {
        iv_today_aqi.setImageResource(WeatherUtil.getAqiPicFromValue(responseBody.getResult().getAqi().getAqi()));
        tv_today_aqi.setText(responseBody.getResult().getAqi().getAqi() +
                " " + responseBody.getResult().getAqi().getQuality());
        tv_today_cur_temp.setText(responseBody.getResult().getTemp());
        tv_today_update_time.setText(getUpdateTime(responseBody.getResult().getUpdatetime()));
        tv_today_type.setText(responseBody.getResult().getWeather());
        tv_today_max_temp.setText(responseBody.getResult().getTemphigh() + "°");
        tv_today_min_temp.setText(responseBody.getResult().getTemplow() + "°");
        tv_today_wind_direct.setText(responseBody.getResult().getWinddirect());
        tv_today_wind_power.setText(responseBody.getResult().getWindpower());
        tv_today_humidity.setText(responseBody.getResult().getHumidity() + "%");

        layout_fragment_bg.setBackgroundResource(WeatherUtil.getBgPicFromWeatherType(responseBody.getResult().getWeather()));
    }

    /**
     * 提取更新时间为正确类型
     *
     * @param time 时间
     * @return
     */
    private String getUpdateTime(String time) {
        int index = time.indexOf(' ');
        String t = time.substring(index, time.length());

        return "[" + t + "]";
    }

    /**
     * 初始化GridView中的数据
     */
    private void initGridViewData() {
        ArrayList<Index> indexList = responseBody.getResult().getIndex();
        IndexAdapter indexAdapter = new IndexAdapter(indexList, getActivity());

        gv_weather_index.setAdapter(indexAdapter);
    }

    /**
     * 初始化ListView控件的数据
     */
    private void initListViewData() {
        ArrayList<Daily> dailyList = responseBody.getResult().getDaily();
        ArrayList<Double> yList = new ArrayList<>();
        ArrayList<Double> yList2 = new ArrayList<>();

        ArrayList<String> xRawDatas = new ArrayList<String>();
        for(int i=0; i<dailyList.size(); i++){
            yList.add(Double.valueOf(dailyList.get(i).getDay().getTemphigh()));
            yList2.add(Double.valueOf(dailyList.get(i).getNight().getTemplow()));
            xRawDatas.add(getParseTime(dailyList.get(i).getDate()));
        }
        line_graphic.setColorResId(R.color.high_temp);
        line_graphic.setData(yList, xRawDatas, 40, 5);
        line_graphic2.setColorResId(R.color.low_temp);
        line_graphic2.setData(yList2,xRawDatas,40, 5);
    }

    private String getParseTime(String time){
        String t = time.substring(5);

        return t;
    }

    /**
     * 初始化横向ListView数据
     */
    private void initHorizontalViewData() {
        ArrayList<Hourly> hourlyList = responseBody.getResult().getHourly();
        HourlyAdapter hourlyAdapter = new HourlyAdapter(hourlyList, getActivity());

        hlv_weather_hourly.setAdapter(hourlyAdapter);
    }

    /**
     * 给控件添加监听事件
     */
    private void initListener() {
        gv_weather_index.setOnItemClickListener(onItemClickListener);
        layout_aqi.setOnClickListener(onClickListener);
    }

    /**
     * 返回ContentFragment对象
     *
     * @param bundle 保存了从MainActivity传递过来的数据
     * @return ContentFragment对象
     */
    public static ContentFragment getInstance(Bundle bundle) {
        ContentFragment instance = new ContentFragment();

        instance.setArguments(bundle);

        return instance;
    }

    /**
     * GridView的点击事件
     */
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Toast.makeText(getActivity(),"positon = " + position,Toast.LENGTH_SHORT).show();
            IndexDetailsDialog dialog = new IndexDetailsDialog(getActivity(), responseBody.getResult().getIndex().get(position));
            dialog.show();
        }
    };

    /**
     * LinearLayout_aqi的点击事件
     */
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            AqiDetailDialog dialog = new AqiDetailDialog(getActivity(), responseBody.getResult().getAqi(),
                    responseBody.getResult().getPressure());
            dialog.show();
        }
    };
}