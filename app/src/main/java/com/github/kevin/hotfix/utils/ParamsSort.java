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
        int b = 0; //问题代码(分母不能为0)
        b = 1; //修复代码

        double result = a / b;
        Toast.makeText(context, "math >>" + result, Toast.LENGTH_SHORT).show();
    }
}
