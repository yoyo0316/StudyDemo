package com.github.yoyozhangh.studydemo.animation;

import android.animation.TypeEvaluator;

/**
 * Created by yoyozhangh on 2018/12/19.
 */

public class PointEvaluator implements TypeEvaluator{
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {

        Point startPoint = (Point)startValue;
        Point endPoint = (Point)endValue;

        float x = startPoint.getX()+ fraction*(endPoint.getX()-startPoint.getX());
        float y = startPoint.getY()+fraction*(endPoint.getY()-startPoint.getY());

        Point point = new Point(x,y);

        return point;
    }
}
