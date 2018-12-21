package com.github.yoyozhangh.studydemo.animation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yoyozhangh on 2018/12/19.
 */

public class MyCircleView extends View {

    //设置需要用到的变量
    public static final float RADIUS = 70f; // 圆的半径
    private Point currentPoint;//当前点坐标
    private Paint mPaint;//绘图的画笔


    public MyCircleView(Context context) {
        super(context);
        init();
    }

    public MyCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        //初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    //重写onDraw 从而实现绘制逻辑
    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null){
            currentPoint = new Point(RADIUS,RADIUS);
            float x = currentPoint.getX();
            float y = currentPoint.getY();
            canvas.drawCircle(x,y,RADIUS,mPaint);
            Point startPoint = new Point(RADIUS,RADIUS);
            Point endPoint = new Point(700,1000);

            ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(),startPoint,endPoint);
            animator.setDuration(5000);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentPoint = (Point)animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.start();
        }else {
            float x = currentPoint.getX();
            float y = currentPoint.getY();
            canvas.drawCircle(x,y,RADIUS,mPaint);
        }
    }
}
