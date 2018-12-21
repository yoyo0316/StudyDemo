package com.github.yoyozhangh.studydemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoyozhangh on 2018/12/21.
 */

public class OpearteDbActivity extends AppCompatActivity {

    CommonItem readDbItem;

    private Context mContext;

    private List<Program> programList = new ArrayList<>();

    private BaseAdapter adapter;
    private ListView listView;
    SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opeartedb_layout);
        mContext = this;
        readDbItem = (CommonItem) findViewById(R.id.read_db);
        listView = findViewById(R.id.list);
        readDbItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
            }
        });

        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return programList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = getLayoutInflater();
                View view;
                if (convertView == null) {
                    view = inflater.inflate(R.layout.list_item, null);
                } else {
                    view = convertView;
                }
                ImageView imageView = (ImageView) view.findViewById(R.id.img);
                Uri uri = Uri.parse(programList.get(position).imgurl);
                imageView.setImageURI(uri);
                TextView cmsid = (TextView) view.findViewById(R.id.cmsid);
                cmsid.setText(programList.get(position).cmsid);
                TextView title = (TextView) view.findViewById(R.id.title);
                title.setText(programList.get(position).title);
                return view;
            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Snackbar.make(view,"确定删除？",Snackbar.LENGTH_LONG).setAction("yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String sql = "delete from channel_history where cmsid=?";
//                        db.rawQuery(sql,new String[]{programList.get(position).cmsid});
                        db.execSQL(sql,new String[]{programList.get(position).cmsid});
                        refreshData();
                    }
                }).show();
                return false;
            }
        });
    }

    private void refreshData() {
        db = readDatabase();
        String sql = "select * from channel_history";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            programList.clear();
            for (int i = 0; i < cursor.getCount(); i++) {
                Program program = new Program();
                program.setCmsid(String.valueOf(cursor.getInt(cursor.getColumnIndex("cmsid"))));
                program.setImgurl(cursor.getString(cursor.getColumnIndex("imgurl")));
                program.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                programList.add(program);
                cursor.moveToNext();
            }
            cursor.close();
        }
        adapter.notifyDataSetChanged();
    }

    private SQLiteDatabase readDatabase() {
        SQLiteDatabase database = null;
        try {
            String DATABASE_FILENAME = "pptv_user_center_bak.db";
            //获取db 文件的绝对路径
            String DATABASE_PATH = mContext.getCacheDir() + "/database";
            String databseFileName = DATABASE_PATH + "/" + DATABASE_FILENAME;
            File dir = new File(DATABASE_PATH);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File dbFile = new File(databseFileName);
            if (!dbFile.exists()) {
                // 获取db 文件的InputStream 对象
//                InputStream is = new FileInputStream(pathFile);
                InputStream is = getResources().getAssets().open("pptv_user_center.db");
                FileOutputStream fos = new FileOutputStream(databseFileName);
                byte[] buffer = new byte[1024];
                int count = 0;
                // 开始复制db 文件
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            database = SQLiteDatabase.openOrCreateDatabase(databseFileName, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return database;
    }
}
