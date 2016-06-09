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
import edu.csuft.bean.Hourly;
import edu.csuft.utils.WeatherUtil;

/**
 * Created by Chalmers on 2016-05-27 22:32.
 * email:qxinhai@yeah.net
 */

/**
 * 横向ListView的适配器
 * 每个小时的天气的ListView的配置器
 */
public class HourlyAdapter extends BaseAdapter{

    private ArrayList<Hourly> hourlyList = null;
    private LayoutInflater layoutInflater = null;

    public HourlyAdapter(ArrayList hourlyList, Context context){
        this.hourlyList = hourlyList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return hourlyList.size();
    }

    @Override
    public Object getItem(int position) {

        return hourlyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.hourly_item,parent,false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bindData(hourlyList.get(position));

        return convertView;
    }

    class ViewHolder{

        private TextView tv_hourly_time;
        private ImageView iv_hourly_img;
        private TextView tv_hourly_type;
        private TextView tv_hourly_temp;

        public ViewHolder(View view){
            tv_hourly_time = (TextView) view.findViewById(R.id.tv_hourly_time);
            iv_hourly_img = (ImageView) view.findViewById(R.id.iv_hourly_img);
            tv_hourly_type = (TextView) view.findViewById(R.id.tv_hourly_type);
            tv_hourly_temp = (TextView) view.findViewById(R.id.tv_hourly_temp);
        }

        public void bindData(Hourly hourly){
            iv_hourly_img.setImageResource(WeatherUtil.getWeatherPicFromType(hourly.getWeather()));
            tv_hourly_time.setText(hourly.getTime());
            tv_hourly_temp.setText(hourly.getTemp());
            tv_hourly_type.setText(hourly.getWeather());
        }
    }
}
