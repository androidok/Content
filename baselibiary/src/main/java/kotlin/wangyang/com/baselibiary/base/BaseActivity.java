package kotlin.wangyang.com.baselibiary.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import kotlin.wangyang.com.baselibiary.viewutils.ViewUtils;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;


/**
 * Created by qq1440214507 on 2017/6/20.
 * 基类
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initContentView());
        ViewUtils.inject(this);
        initView();
        initTitle();
        initData();
    }
    public abstract int initContentView();

    protected abstract void initView();

    protected abstract void initTitle();

    protected abstract void initData();

     protected void startActivity(Class<?> clazz,Bundle bundle){
         Intent intent = new Intent(this, clazz);
         if (bundle!=null){
             intent.putExtras(bundle);
         }
         startActivity(intent);

     }
     protected void startActivity(Class<?> clazz){
         startActivity(clazz,null);
     }
     protected <T extends View> T findId(int res){
         View view = findViewById(res);
         if (view==null)return null;
         return (T) view;
     }

}
