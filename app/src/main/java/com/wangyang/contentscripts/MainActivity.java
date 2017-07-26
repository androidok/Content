package com.wangyang.contentscripts;

import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.wangyang.dialoglibrary.BuildDialog;
import com.wangyang.dialoglibrary.BuildEnum;

import kotlin.wangyang.com.framelibrary.BaseSkinActivity;

public class MainActivity extends BaseSkinActivity {


    @Override
    protected void initView() {
        findViewById(R.id.textCLick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initData() {
        BuildDialog.build(this, R.style.TransparentDialog).setGravity(Gravity.CENTER)
                .setFullWidth(true).Anima(BuildEnum.FORM_BOTTOM).setCancelable(true)
                .setLayoutId(R.layout.dialog)
                .setOnItemViewClickListener(R.id.tvClick, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "我就哈哈", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();

    }

    @Override
    public int initContentView() {
        return R.layout.activity_main;
    }
}
