package edu.csuft.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.csuft.activity.R;
import edu.csuft.bean.Index;
import edu.csuft.utils.WeatherUtil;

/**
 * Created by Chalmers on 2016-05-31 10:14.
 * email:qxinhai@yeah.net
 */
public class IndexDetailsDialog extends AlertDialog {

    private Dialog dialog = null;
    private Context context = null;

    private Index index = null;

    public IndexDetailsDialog(Context context, Index index) {
        super(context);
        this.context = context;
        this.dialog = new Dialog(context);
        this.index = index;
    }

    @Override
    public void show(){
        View view = LayoutInflater.from(context).inflate(R.layout.index_details_item,null,false);
        dialog.setContentView(view);

        ImageView iv_index_pic = (ImageView) view.findViewById(R.id.iv_index_pic);
        TextView tv_index_details = (TextView) view.findViewById(R.id.tv_index_details);
        LinearLayout layout_index_detail_root = (LinearLayout) view.findViewById(R.id.layout_index_detail_root);

        iv_index_pic.setImageResource(WeatherUtil.getIndexPicFromIname(index.getIname()));
        tv_index_details.setText(index.getDetail());

        dialog.show();
    }
}
