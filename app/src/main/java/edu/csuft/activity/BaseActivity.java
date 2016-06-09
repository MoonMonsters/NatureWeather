package edu.csuft.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        initView();
        initData();
        initListener();
    }

    /**
     * 返回布局id
     * @return 布局id
     */
    public abstract int getLayoutResourceId();

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 初始化数据信息
     */
    public abstract void initData();

    /**
     * 添加监听器
     */
    public abstract void initListener();

    /**
     *  应用模板，简写操作
     * @param id 控件id
     * param <T> 模板
     * @return View对象
     */
    public <T> T findView(int id){
        T view = (T)super.findViewById(id);

        return view;
    }
}
