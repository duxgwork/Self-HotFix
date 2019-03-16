package com.github.kevin.hotfix.utils;

import android.content.Context;
import android.widget.Toast;

public class ParamsSort {

    /**
     * 示范代码修复
     * @param context
     */
    public void math(Context context) {
        int a = 10;
        //在此处修复后,重新打包提取classes2.dex供客户端下载
        int b = 0; //问题代码(分母不能为0)
        Toast.makeText(context, "math >>" + a/b, Toast.LENGTH_SHORT).show();
    }
}
