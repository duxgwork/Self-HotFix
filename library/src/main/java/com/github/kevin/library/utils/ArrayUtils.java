package com.github.kevin.library.utils;

import java.lang.reflect.Array;

public class ArrayUtils {

    /**
     * 合并数组
     *
     * @param arrayLhs 前数组（插队数组）
     * @param arrayRhs 后数组（已有数组）
     * @return 处理后的新数组
     */
    public static Object combineArray(Object arrayLhs, Object arrayRhs) {
        Class<?> localClass = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);
        int j = i + Array.getLength(arrayRhs);
        Object result = Array.newInstance(localClass, j);
        for (int k = 0; k < j; ++k) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i));
            }
        }
        return result;
    }

}
