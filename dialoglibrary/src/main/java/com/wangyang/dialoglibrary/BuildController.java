package com.wangyang.dialoglibrary;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.Set;

/**
 * Created by wangyang on 2017/7/12.
 * des:
 */

class BuildController {
    private final Context mContext;
    final AppCompatDialog mDialog;
    public final Window mWindow;
    public View mView;
    public boolean mCancelable;
    public BuildEnum mBuildEnum;
    public int mAnimResId;
    private boolean mIsFullWidth;
    private int mGravity;

    public BuildController(Context context, BuildDialog buildDialog, Window window) {
        mContext = context;
        mDialog = buildDialog;
        mWindow = window;
    }
    private int getScreenWidth(){
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }
    public void apply(){
        applyAnima();
        applyWidth();
        if (mGravity!=0){
            mWindow.setGravity(mGravity);
        }
    }

    private void applyWidth() {
        int width=mIsFullWidth? getScreenWidth():ViewGroup.LayoutParams.WRAP_CONTENT;
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mDialog.setContentView(mView,layoutParams);
    }

    private void applyAnima(){
        if (mBuildEnum==null && mAnimResId==0){
             return;
        }
        int resId = mAnimResId;
        if (mBuildEnum!=null){
            switch (mBuildEnum){
                case FORM_BOTTOM:
                    break;
                case FORM_TOP:
                    break;
                case FORM_LEFT:
                    break;
                case FORM_RIGHT:
                    break;
                default:
                    return;
            }
        }
        mWindow.setWindowAnimations(resId);
    }


    public static class BuildParams {

        public Context mContext;
        public int layoutId;
        public boolean mCancelable = true;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public View mView;
        public BuildEnum mBuildEnum;
        public int mAnimResId;
        private final LayoutInflater mInflater;
        public boolean mIsFullWidth;
        public int gravity;
        private ArrayMap<Integer,View.OnClickListener> listenerArrayMap;

        public BuildParams(Context context) {
            mContext = context;
            mCancelable = true;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void apply(BuildController mAlert){
            if (mView == null && layoutId == 0) {
                Log.e("BuildController","you must set a view or layoutId");
                return;
            }
            mAlert.mView = mView == null ? mInflater.inflate(layoutId, null, false) : mView;
            mAlert.mCancelable = mCancelable;
            mAlert.mBuildEnum = mBuildEnum;
            mAlert.mAnimResId = mAnimResId;
            mAlert.mIsFullWidth=mIsFullWidth;
            mAlert.mGravity=gravity;
            //设置点击事件
            if (listenerArrayMap!=null){
                Set<Integer> integers = listenerArrayMap.keySet();
                for (final Integer integer : integers) {
                    mAlert.mView.findViewById(integer).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            View.OnClickListener onClickListener = listenerArrayMap.get(integer);
                            if (onClickListener!=null){
                                onClickListener.onClick(v);
                            }
                        }
                    });
                }
            }

        }
        public void putListener(int resId,View.OnClickListener listener){
            if (listenerArrayMap==null){
                listenerArrayMap=new ArrayMap<>();
            }
            listenerArrayMap.put(resId,listener);
        }
    }


}

