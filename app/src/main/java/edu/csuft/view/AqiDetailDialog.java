package edu.csuft.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.csuft.activity.R;
import edu.csuft.bean.Aqi;

/**
 * Created by Chalmers on 2016-06-05 14:30.
 * email:qxinhai@yeah.net
 */
public class AqiDetailDialog extends AlertDialog{

    private Context context = null;
    private Aqi aqi = null;
    private String pressure = null;
    Dialog dialog = null;

    public AqiDetailDialog(Context context, Aqi aqi, String pressure) {
        super(context);
        dialog = new Dialog(context);
        this.aqi = aqi;
        this.pressure = pressure;
        this.context = context;
    }

    @Override
    public void show() {
        View view = LayoutInflater.from(context).inflate(R.layout.aqi_detail_item,null,false);
        dialog.setContentView(view);

        LinearLayout layout_aqi_detail_root = (LinearLayout) view.findViewById(R.id.layout_aqi_detail_root);
        TextView tv_aqi_detail_pressure = (TextView) view.findViewById(R.id.tv_aqi_detail_pressure);
        TextView tv_aqi_detail_affect = (TextView) view.findViewById(R.id.tv_aqi_detail_affect);
        TextView tv_aqi_detail_measure = (TextView) view.findViewById(R.id.tv_aqi_detail_measure);

        tv_aqi_detail_affect.setText(aqi.getAqiinfo().getAffect());
        tv_aqi_detail_measure.setText(aqi.getAqiinfo().getMeasure());
        tv_aqi_detail_pressure.setText(pressure);

        dialog.show();
    }
}