package com.github.kevin.hotfix.activities;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.github.kevin.hotfix.BaseActivity;
import com.github.kevin.hotfix.R;
import com.github.kevin.hotfix.utils.ParamsSort;
import com.github.kevin.library.FixDexUtils;
import com.github.kevin.library.utils.Constants;
import com.github.kevin.library.utils.FileUtils;

import java.io.File;
import java.io.IOException;

public class SecondActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_second;
    }

    @Override
    protected void initViewsAndEvents() {
        findViewById(R.id.bt_show).setOnClickListener(this);
        findViewById(R.id.bt_fix).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (R.id.bt_show == v.getId()) {
            ParamsSort paramsSort = new ParamsSort();
            paramsSort.math(this);
        } else if (R.id.bt_fix == v.getId()) {
            fixBug();
        }
    }

    //classes2.dex ---->  /storage/emulated/0/classes2.dex
    private void fixBug() {
        //通过服务器接口下载dex文件，v1.3.3版本有一个热修复dex包
        File sourceFile = new File(Environment.getExternalStorageDirectory(), Constants.DEX_NAME);
        //目标路径：私有目录里的临时文件夹odex
        File targetFile = new File(getDir(Constants.DEX_DIR, Context.MODE_PRIVATE).getAbsolutePath()
                + File.separator + Constants.DEX_NAME);
        //如果已经存在dex修复包，则先进行清理
        if (targetFile.exists()) {
            targetFile.delete();
            Toast.makeText(this, "删除已存在的dex文件", Toast.LENGTH_SHORT).show();
        }
        try {
            //复制修复包dex文件到app私有目录
            FileUtils.copyFile(sourceFile, targetFile);
            Toast.makeText(this, "复制dex文件完成", Toast.LENGTH_SHORT).show();
            //开始修复
            FixDexUtils.loadFixDex(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}