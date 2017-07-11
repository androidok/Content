package kotlin.wangyang.com.baselibiary.viewutils;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by qq1440214507 on 2017/6/19.
 */

public class ViewUtils {
    public static void inject(Activity activity){
        inject(new ViewFinder(activity),activity);

    }
    public static void inject(View view){
        inject(new ViewFinder(view),view);
    }
    public static void inject(Fragment fragment){
        inject(new ViewFinder(fragment.getActivity()),fragment.getActivity());
    }
    public static void inject(View view,Object object){
        inject(new ViewFinder(view),object);
    }
    private static void inject(ViewFinder viewFinder,Object object){

        injectView(viewFinder,object);
        injectEvent(viewFinder,object);

    }
    //注入事件
    private static void injectEvent(ViewFinder viewFinder, final Object object) {
        Class<?> aClass = object.getClass();
        Method[] methods = aClass.getMethods();
        for (final Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick==null){
                continue;
            }
            final CheckNetwork checkNetwork = method.getAnnotation(CheckNetwork.class);
            int[] value = onClick.value();
            for (int ids : value) {
                final View view = viewFinder.findId(ids);
                if (view==null){
                    continue;
                }
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (checkNetwork!=null && !NetWorkUtils.isNetworkConnected(v.getContext())){
                            //没网络Toast
                            Toast.makeText(v.getContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {
                            method.setAccessible(true);
                            method.invoke(object,view);
                        } catch (Exception e) {
                            e.printStackTrace();
                            invokeMethod(method, object);
                        }
                    }
                });

            }
        }
    }

    private static void invokeMethod(Method method,Object object) {
        try {
            method.invoke(object);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private static void injectView(ViewFinder viewFinder, Object object) {
        Class<?> aClass = object.getClass();
        //获取所有属性
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            ViewInject annotation = field.getAnnotation(ViewInject.class);
            if (annotation!=null){
                int resId = annotation.value();
                View view = viewFinder.findId(resId);
                if (view==null){
                    continue;
                }
                field.setAccessible(true);
                //注入view 的值
                try {
                    field.set(object,view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    //支持无参数的

                }

            }
        }
    }

}
