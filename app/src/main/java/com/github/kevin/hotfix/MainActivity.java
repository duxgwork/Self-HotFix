package com.github.kevin.hotfix;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;

import com.github.kevin.hotfix.activities.SecondActivity;


public class MainActivity extends BaseActivity {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] params = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (checkSelfPermission(params[0]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(params, 200);
            }
        }
    }

    //点击跳转
    public void jump(View view) {
        readyGo(SecondActivity.class);
    }

}
