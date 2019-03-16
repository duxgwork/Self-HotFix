package com.github.kevin.library;

import android.content.Context;

import com.github.kevin.library.utils.ArrayUtils;
import com.github.kevin.library.utils.Constants;
import com.github.kevin.library.utils.ReflectUtils;

import java.io.File;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class FixDexUtils {
    //存放需要修复的dex集合，可能不止一个
    private static HashSet<File> loadedDex = new HashSet<>();

    static {
        //修复前进行清理工作
        loadedDex.clear();
    }

    /**
     * 加载热修复的dex文件
     *
     * @param context 上下文
     */
    public static void loadFixDex(Context context) {
        if (context == null) return;
        File fileDir = context.getDir(Constants.DEX_DIR, Context.MODE_PRIVATE);
        File[] listFiles = fileDir.listFiles();
        for (File file : listFiles) {
            if (file.getName().endsWith(Constants.DEX_SUFFIX) && !Constants.DEX_MAIN.equals(file.getName())) {
                loadedDex.add(file);
            }
        }
        //模拟类加载器
        createDexClassLoader(context, fileDir);
    }

    /**
     * 创建加载补丁的DexClassLoader （自有）
     *
     * @param context 上下文
     * @param fileDir dex文件目录
     */
    private static void createDexClassLoader(Context context, File fileDir) {
        //创建临时解压目录（先解压到该目录，再加载java）
        String optimizedDir = fileDir.getAbsolutePath() + File.separator + "opt_dex";
        //不存在就创建
        File fopt = new File(optimizedDir);
        if (!fopt.exists()) {
//            fopt.mkdir();//创建目录
            fopt.mkdirs();//创建多级目录
        }
        for (File dex : loadedDex) {
            //每遍历一个要修复的dex文件，就需要插桩一次
            //参数：DexClassLoader(dex包路径,解压目录,寻找本地库,类加载器)
            DexClassLoader classLoader = new DexClassLoader(dex.getAbsolutePath(),
                    optimizedDir, null, context.getClassLoader());
            hotfix(classLoader, context);
        }
    }

    /**
     * 热修复
     *
     * @param classLoader 自有的类加载器，加载了修复包的DexClassLoader
     * @param context     上下文
     */
    private static void hotfix(DexClassLoader classLoader, Context context) {
        //获取系统的PathClassLoader
        PathClassLoader pathLoader = (PathClassLoader) context.getClassLoader();
        try {
            //获取自有的dexElements数组对象
            Object myDexElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(classLoader));

            //获取系统的dexElements数组对象
            Object systemDexElements = ReflectUtils.getDexElements(ReflectUtils.getPathList(pathLoader));

            //合并成新的dexElements数组对象
            Object dexElements = ArrayUtils.combineArray(myDexElements, systemDexElements);

            //重新赋值给系统的pathList属性 --- 修改pathList中的dexElemnets对象
            //通过反射获取系统的pathList对象
            Object systemPathList = ReflectUtils.getPathList(pathLoader);
            ReflectUtils.setField(systemPathList, systemPathList.getClass(), dexElements);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
