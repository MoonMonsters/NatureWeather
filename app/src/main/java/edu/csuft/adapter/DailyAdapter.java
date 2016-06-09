package edu.csuft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.csuft.activity.R;
import edu.csuft.bean.Daily;
import edu.csuft.utils.WeatherUtil;

/**
 * Created by Chalmers on 2016-05-28 18:39.
 * email:qxinhai@yeah.net
 */
public class DailyAdapter extends BaseAdapter {

    private ArrayList<Daily> dailyList = null;
    private LayoutInflater layoutInflater = null;

    public DailyAdapter(ArrayList<Daily> dailyList, Context context) {
        this.dailyList = dailyList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return dailyList.size();
    }

    @Override
    public Object getItem(int position) {
        return dailyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.daily_item, parent, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bindData(dailyList.get(position));

        return convertView;
    }

    class ViewHolder {

        private ImageView iv_daily_img;
        private TextView tv_daily_date;
        private TextView tv_daily_type;
        private TextView tv_daily_max_min_temp;

        public ViewHolder(View view) {
            iv_daily_img = (ImageView) view.findViewById(R.id.iv_daily_img);
            tv_daily_date = (TextView) view.findViewById(R.id.tv_daily_date);
            tv_daily_type = (TextView) view.findViewById(R.id.tv_daily_type);
            tv_daily_max_min_temp = (TextView) view.findViewById(R.id.tv_daily_max_min_temp);
        }

        public void bindData(Daily daily) {
            iv_daily_img.setImageResource(WeatherUtil.getWeatherPicFromType(daily.getDay().getWeather()));
            tv_daily_date.setText(daily.getDate());
            tv_daily_type.setText(daily.getDay().getWeather());
            tv_daily_max_min_temp.setText(getTempFromMaxAndMinTemp(daily.getDay().getTemphigh(),daily.getNight().getTemplow()));
        }

        /**
         * 构造字符串，显示 最高温度和最低温度
         * @param maxTemp 最高温度
         * @param minTemp 最低温度
         * @return 需要的字符串
         */
        private String getTempFromMaxAndMinTemp(String maxTemp, String minTemp){

            return maxTemp + "°" + " | " + minTemp + "°";
        }
    }
}
