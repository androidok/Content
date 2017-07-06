package kotlin.wangyang.com.baselibiary.fix;

import android.content.Context;

import java.io.File;
import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;

/**
 * Created by wangyang on 2017/7/6.
 * des:热修复管理
 */

public class HotFixManager {

    private Context context;
    private File dexDir;//dex文件包的路径

    public void init(Context context){

        this.context = context;
        dexDir=context.getDir("odex",Context.MODE_PRIVATE);

    }

    /**
     * 修复
     * @param fixDir dex文件路径
     */
    public void fixBug(String fixDir){
        File fixFile = new File(fixDir);
        if (!fixFile.exists()){//如果热更新文件不存在 就返回
            return;
        }
        //准备将fixFile传到 dex专有目录
        ClassLoader classLoader = context.getClassLoader();
        Object dexElements;
        try {
            dexElements= getDexElements(classLoader);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        //将fix.dex拷贝移动到应用的dex目录
        File dexFile = new File(dexDir,fixFile.getName());
        if (dexFile.exists()){
            return;
        }


    }

    /**
     *
     * @param classLoader
     * @return dexElements =>这里面放的是所有dex包集合，类加载的时候，会去遍历dexElements中的dex
     * 所以，只需要将fix后的dex插入在dexElements最前面，当创建类的时候就会先找到修复过后的类而return，就能做到热修复，
     */
    private Object getDexElements(ClassLoader classLoader) throws NoSuchFieldException, IllegalAccessException {
        //找到PathList 里面存放了dex  library等集合信息
        Field pathListFiled = BaseDexClassLoader.class.getDeclaredField("pathList");
        pathListFiled.setAccessible(true);
        Object pathFile = pathListFiled.get(classLoader);
        //获取 pathFile 中的 pathFile
        Field dexElementsFiled = pathFile.getClass().getDeclaredField("dexElements");
        dexElementsFiled.setAccessible(true);
        return dexElementsFiled.get(pathFile);
    }
}
