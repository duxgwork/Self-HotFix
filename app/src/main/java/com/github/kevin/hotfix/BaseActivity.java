package com.github.kevin.hotfix;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class BaseActivity extends AppCompatActivity {
    /**
     * Log tag
     */
    protected static String TAG_LOG = null;

    /**
     * 加载 layout布局
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();


    /**
     * get bundle data
     *
     * @param extras
     */
    protected void getBundleExtras(Bundle extras) {

    }

    /**
     * 加载控件以及监听事件
     */
    protected void initViewsAndEvents() {

    }

    /**
     * 初始化内容
     */
    protected void init() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG_LOG = this.getClass().getSimpleName();
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }
        setContentView(getContentViewLayoutID());
        initViewsAndEvents();
        init();
    }

    protected <T extends View> T internalFindViewById(int id) {
        return ((T) this.findViewById(id));
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

}
