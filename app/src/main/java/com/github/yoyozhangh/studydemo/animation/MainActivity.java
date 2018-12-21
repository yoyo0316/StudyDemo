package com.github.yoyozhangh.studydemo.animation;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.yoyozhangh.studydemo.R;

/**
 * Created by yoyozhangh on 2018/12/18.
 */

public class MainActivity extends Activity {
    public static final String TAG = MainActivity.class.getSimpleName();
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final ImageView imageView = (ImageView) findViewById(R.id.img);
        textView = (TextView) findViewById(R.id.tv);
        final AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();


        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                animationDrawable.start();
//                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha);
//                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale);
//                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate);
//                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate);
//                imageView.startAnimation(animation);

//                alpha(imageView);

//                scale(imageView);

//                translate(imageView);

//                rotate(imageView);

                ValueAnimator animator = ValueAnimator.ofInt(0,300);
                animator.setDuration(500);
                animator.setStartDelay(500);
                animator.setRepeatCount(0);
                animator.setRepeatMode(ValueAnimator.RESTART);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int currentValue = (Integer)animation.getAnimatedValue();
                        Log.d(TAG, "onAnimationUpdate() called with: currentValue = [" + currentValue + "]");
                        textView.getLayoutParams().width = currentValue;
                        textView.requestLayout();
                    }
                });

                animator.start();
            }
        });
    }

    private void rotate(ImageView imageView) {
        RotateAnimation rotateAnimation = new RotateAnimation(0,-720,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setDuration(1000);
        imageView.startAnimation(rotateAnimation);
    }

    private void translate(ImageView imageView) {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 2f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 2f);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(3000);
        imageView.startAnimation(translateAnimation);
    }

    private void scale(ImageView imageView) {
        ScaleAnimation animation1 = new ScaleAnimation(0.2f, 1.5f, 0.2f, 1.5f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation1.setDuration(2000);
        animation1.setFillAfter(true);
        imageView.startAnimation(animation1);
    }

    private void alpha(ImageView imageView) {
        AlphaAnimation animation = new AlphaAnimation(1, 0);
        animation.setDuration(500);//500 毫秒
        animation.setFillAfter(true);//动画 结束后保留结束状态
        imageView.startAnimation(animation);
    }
}
