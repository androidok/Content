package kotlin.wangyang.com.baselibiary;

import android.content.Context;
import android.util.Log;

/**
 * Created by qq1440214507 on 2017/6/21.
 * 全局捕获异常
 */

public class CrashExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Context context;
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;//默认异常处理器
    private volatile static CrashExceptionHandler crashExceptionHandler=null;
    public static CrashExceptionHandler getInstance(){
        if (crashExceptionHandler==null){
            crashExceptionHandler=new CrashExceptionHandler();
        }
        return crashExceptionHandler;
    }

    public void init(Context context){
        this.context=context;
        //设置全局异常处理类
        Thread.currentThread().setUncaughtExceptionHandler(this);
        //获得默认的异常处理类，
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }
    @Override
    public void uncaughtException(Thread t, Throwable e) {

        Log.e("CrashExceptionHanlder",e.getMessage());
        //这里记录崩溃日志，手机,版本号等等

        //捕获异常后交给默认的异常处理器，处理，该闪退闪退
        //可以做优化，比如finsh当前activity，或者重庆应用等等
        defaultUncaughtExceptionHandler.uncaughtException(t,e);
    }
}
