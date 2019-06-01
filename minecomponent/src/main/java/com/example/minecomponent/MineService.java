package com.example.minecomponent;

import android.content.Context;
import android.content.Intent;

import com.example.componentlib2.IMineService;

public class MineService implements IMineService {
    @Override
    public void launch(Context context, int userId) {
        Intent intent = new Intent(context, MineActivity.class);
        intent.putExtra("ID", userId);
        context.startActivity(intent);
    }
}
