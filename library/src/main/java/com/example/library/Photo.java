package com.example.library;

public class Photo {
    /**
     * 图片原始路径
     */
    private String originpath;

    /**
     * 是否压缩过
     */
    private boolean compressed;


    /**
     * 压缩后路径
     */
    private String compressPath;


    public Photo(String originpath) {
        this.originpath = originpath;
    }


    public String getOriginpath() {
        return originpath;
    }

    public void setOriginpath(String originpath) {
        this.originpath = originpath;
    }

    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    public String getCompressPath() {
        return compressPath;
    }

    public void setCompressPath(String compressPath) {
        this.compressPath = compressPath;
    }
}
