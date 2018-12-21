package com.github.yoyozhangh.studydemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by yoyozhangh on 2018/12/21.
 */

public class CommonItem extends LinearLayout {

    private TextView mItemNumberText;
    private TextView mItemTitle;
    private LinearLayout mItem;

    private String itemNumber;
    private String itemText;

    public CommonItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //加载视图布局
        LayoutInflater.from(context).inflate(R.layout.common_item, this, true);

        //加载自定义属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CommonItem);
        itemNumber = a.getString(R.styleable.CommonItem_itemNumber);
        itemText = a.getString(R.styleable.CommonItem_itemText);

        //回收资源
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mItemNumberText = (TextView) findViewById(R.id.item_number);
        mItemTitle = (TextView) findViewById(R.id.item_title);
        mItem = (LinearLayout) findViewById(R.id.item);

        if (!TextUtils.isEmpty(itemNumber)) {
            setItemNumber(itemNumber);
        }

        if (!TextUtils.isEmpty(itemText)) {
            setItemText(itemText);
        }
    }


    public void setItemNumber(String itemNumber) {
        mItemNumberText.setText(itemNumber);
    }

    public void setItemText(String itemText) {
        mItemTitle.setText(itemText);
    }

}
