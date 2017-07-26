package com.wangyang.dialoglibrary;
import android.content.Context;
import android.support.annotation.AnimRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;

/**
 * Created by wangyang on 2017/7/12.
 * des:BuildDialog，基于建造者模式的自定义dialog
 */

public class BuildDialog extends AppCompatDialog {

    private BuildController mAlert;

    public BuildDialog(@NonNull Context context) {
        this(context, 0);
    }

    public BuildDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, resolveDialogTheme(context, themeResId));
        mAlert = new BuildController(context, this, getWindow());
    }

    protected BuildDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        setCancelable(cancelable);
        setOnCancelListener(cancelListener);
    }

    private static int resolveDialogTheme(@NonNull Context context, @StyleRes int resid) {
        if (resid >= 0x01000000) {   // start of real resource IDs.
            return resid;
        } else {
            TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.alertDialogTheme, outValue, true);
            return outValue.resourceId;
        }
    }
    public static Builder build(Context context){
        return new Builder(context);
    }
    public static Builder build(Context context,@StyleRes int themeResId){
        return new Builder(context,themeResId);
    }

    public static class Builder {
        private final BuildController.BuildParams P;
        private final int mTheme;
        public Builder(@NonNull Context context) {
            this(context, resolveDialogTheme(context, 0));
        }
        public Builder(@NonNull Context context, @StyleRes int themeResId) {
            P = new BuildController.BuildParams(new ContextThemeWrapper(
                    context, resolveDialogTheme(context, themeResId)));
            mTheme = themeResId;
        }
        //设置layoutID
        public Builder setLayoutId(@LayoutRes int layoutId) {
            P.mView=null;
            P.layoutId = layoutId;
            return this;
        }
        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }
        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }
        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }
        /**
         * Sets the callback that will be called if a key is dispatched to the dialog.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }
        /**
         * Set a custom view resource to be the contents of the Dialog. The
         * resource will be inflated, adding all top-level views to the screen.
         *
         * @return this Builder object to allow for chaining of calls to set
         *         methods
         */
        public Builder setView(View view) {
            P.mView = view;
            P.layoutId = 0;
            return this;
        }
        //入场出场动画
        public Builder Anima(BuildEnum buildEnum){
            P.mBuildEnum = buildEnum;
            P.mAnimResId=0;
            return this;
        }
        //入场出场动画 xml形式
        public Builder Anima(@AnimRes int animResId){
            P.mBuildEnum = null;
            P.mAnimResId = animResId;
            return this;
        }
         //是否设置宽度全屏
        public Builder setFullWidth(boolean isFullWidth){
            P.mIsFullWidth = isFullWidth;
            return this;
        }
        //设置显示的位置
        public Builder setGravity(int gravity){
            P.gravity = gravity;
            return this;
        }
        //设置点击事件
        public Builder setOnItemViewClickListener(int viewId,View.OnClickListener listener){
            P.putListener(viewId,listener);
            return this;
        }
        /**
         * Creates an {@link AlertDialog} with the arguments supplied to this
         * builder.
         * <p>
         * Calling this method does not display the dialog. If no additional
         * processing is needed, {@link #show()} may be called instead to both
         * create and display the dialog.
         */
        private BuildDialog create() {
            // We can't use Dialog's 3-arg constructor with the createThemeContextWrapper param,
            // so we always have to re-set the theme
            final BuildDialog dialog = new BuildDialog(P.mContext, mTheme);
            P.apply(dialog.mAlert);
            dialog.mAlert.apply();
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }

        public BuildDialog show(){
            final BuildDialog dialog = create();
            dialog.show();
            return dialog;
        }

    }
}
