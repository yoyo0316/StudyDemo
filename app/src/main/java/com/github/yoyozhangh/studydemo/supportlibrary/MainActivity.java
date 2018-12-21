package com.github.yoyozhangh.studydemo.supportlibrary;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.yoyozhangh.studydemo.R;

/**
 * Created by yoyozhangh on 2018/12/20.
 */

public class MainActivity extends Activity {

    TextView textView ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textView = (TextView) findViewById(R.id.tv);
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"here is a snackBar",Snackbar.LENGTH_LONG).setAction(R.string.revert, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textView.setText("snackBar show");
                    }
                }).show();
            }
        });
    }
}
