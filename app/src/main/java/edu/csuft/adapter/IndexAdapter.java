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
import edu.csuft.bean.Index;
import edu.csuft.utils.WeatherUtil;

/**
 * Created by Chalmers on 2016-05-28 22:56.
 * email:qxinhai@yeah.net
 */
public class IndexAdapter extends BaseAdapter {

    private ArrayList<Index> indexList = null;
    private LayoutInflater layoutInflater = null;

    public IndexAdapter(ArrayList<Index> indexList, Context context) {
        this.indexList = indexList;
        //移除掉了空气扩散指数，因为对GridView的整体结构破坏了
        for(int i=0; i<indexList.size(); i++){
            if(indexList.get(i).getIname().contains("污染")){
                indexList.remove(i);
                break;
            }
        }

        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return indexList.size();
    }

    @Override
    public Object getItem(int position) {
        return indexList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.index_item, parent, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bindData(indexList.get(position));

        return convertView;
    }

    class ViewHolder {

        private ImageView iv_index_aqi;
        private TextView tv_index_iname;
        private TextView tv_index_ivalue;

        public ViewHolder(View view) {
            iv_index_aqi = (ImageView) view.findViewById(R.id.iv_index_aqi);
            tv_index_iname = (TextView) view.findViewById(R.id.tv_index_iname);
            tv_index_ivalue = (TextView) view.findViewById(R.id.tv_index_ivalue);
        }

        /**
         * 绑定数据
         * @param index 绑定审计局对象
         */
        public void bindData(Index index) {
            //根据指数名称来设置相应的图片
            iv_index_aqi.setImageResource(WeatherUtil.getIndexPicFromIname(index.getIname()));
            tv_index_iname.setText(index.getIname());
            tv_index_ivalue.setText(index.getIvalue());
        }
    }
}