package com.github.yoyozhangh.studydemo.serial;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yoyozhangh on 2019/1/6.
 */

public class BookItem implements Parcelable {

    public String name;
    public long lastTime;
    public String title;
    public String path;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeLong(this.lastTime);
        dest.writeString(this.title);
        dest.writeString(this.path);
    }

    public BookItem() {
    }

    protected BookItem(Parcel in) {
        this.name = in.readString();
        this.lastTime = in.readLong();
        this.title = in.readString();
        this.path = in.readString();
    }

    public static final Parcelable.Creator<BookItem> CREATOR = new Parcelable.Creator<BookItem>() {
        @Override
        public BookItem createFromParcel(Parcel source) {
            return new BookItem(source);
        }

        @Override
        public BookItem[] newArray(int size) {
            return new BookItem[size];
        }
    };
}
