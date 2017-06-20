package kotlin.wangyang.com.baselibiary.viewutils;

import android.app.Activity;
import android.view.View;

/**
 * Created by qq1440214507 on 2017/6/19.
 * findID的辅助类
 */

 class ViewFinder {
    private Activity activity;
    private View view;

     ViewFinder(Activity activity) {
        this.activity = activity;
    }

     ViewFinder(View view) {
        this.view = view;
    }
     View findId(int viewId){
        return view==null?activity.findViewById(viewId):view.findViewById(viewId);
    }
}
