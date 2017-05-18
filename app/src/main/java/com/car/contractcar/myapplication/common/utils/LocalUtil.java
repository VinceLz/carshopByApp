package com.car.contractcar.myapplication.common.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dq_da on 2016/5/25
 * 本地数据 工具类
 */
public class LocalUtil {

    // SD卡
    public static String SDDIR = null;

    // 目录
    public static String path_app = null;
    public static String path_imgCache = null;

    /**
     * 判断SDCard是否加载
     *
     * @return
     */
    public static boolean isHasSdcard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 初始化SDCard的根路径
     */
    public static void getSDExternalRootPath() {
        if (isHasSdcard()) {
            SDDIR = Environment.getExternalStorageDirectory().toString();
        }
    }

    /**
     * 创建本地目录
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void createSdPath() {

        // 第一层目录 : "/onlinemealordering"
        path_app = SDDIR + "/MyTrade";
        File appDir = new File(path_app);
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        // 图片缓存的路径 : "/onlinemealordering/imgCache"
        path_imgCache = SDDIR + "/MyTrade/imgCache/";
        File imgCacheDir = new File(path_imgCache);
        if (!imgCacheDir.exists()) {
            imgCacheDir.mkdir();
        }

    }

    /**
     * drawable -> Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        return bitmap;
    }

    /**
     * Bitmap -> Drawable
     *
     * @param bitmap
     * @return
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        Drawable drawable = new BitmapDrawable(Resources.getSystem(), bitmap);
        return drawable;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 判断图片是否存在
     *
     * @param img_name : 图片名
     * @return T/F
     */
    public boolean containsCacheKey(String img_name) {
        // 文件完整路径
        String fileUrl = path_imgCache + img_name;
        try {
            File f = new File(fileUrl);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 缓存加载图片信息
     *
     * @param img_name :　图片名
     * @param suffix   　：　后缀名
     * @return Object : drawable - bitmap
     */
    public Object loadCacheImg(String img_name, String suffix) {
        // 文件完整路径
        String fileUrl = path_imgCache + img_name;
        if (suffix.equals("d")) {
            return decodeDrawableFile(fileUrl);
        } else if (suffix.equals("b")) {
            return decodeBitmapFile(fileUrl);
        } else {
            return null;
        }
    }

    /**
     * 获取当前时间的数字
     */
   public static String getDateString(){
        SimpleDateFormat   formatter   =   new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date   curDate   =   new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }




    /**
     * 从 完整路径 构建压缩 drawable 对象
     *
     * @param path : 完整路径
     * @return img
     */
    public Drawable decodeDrawableFile(String path) {
        return Drawable.createFromPath(path);
    }

    /**
     * 从 完整路径 构建压缩 bitmap 对象
     *
     * @param path : 完整路径
     * @return bmp
     */
    public Bitmap decodeBitmapFile(String path) {
        return readBitmap(path);
    }

    /**
     * 以最省内存的方式读取图片
     */
    public static Bitmap readBitmap(final String path) {
        try {
            FileInputStream stream = new FileInputStream(new File(path + "test.jpg"));
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = 8;
            opts.inPurgeable = true;
            opts.inInputShareable = true;
            Bitmap bitmap = BitmapFactory.decodeStream(stream, null, opts);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 磁盘保存图片信息
     *
     * @param img_name : 图片名
     * @param img      : drawable
     */
    public void saveCacheImg(String img_name, Drawable img) {
        // 文件完整路径
        String fileUrl = path_imgCache + img_name;
        // 转换 图片
        Bitmap bmp = drawableToBitmap(img);
        replaceBitmapToFile(bmp, fileUrl);
    }

    /**
     * 磁盘保存图片信息
     *
     * @param img_name : 图片名
     * @param bmp      : Bitmap
     */
    public void saveCacheImg(String img_name, Bitmap bmp) {
        // 文件完整路径
        String fileUrl = path_imgCache + img_name;
        replaceBitmapToFile(bmp, fileUrl);
    }

    /**
     * 替换 本地磁盘 图片
     *
     * @param bmp  : 图片
     * @param path : 完整路径
     * @return 是否成功?
     */
    public boolean replaceBitmapToFile(Bitmap bmp, String path) { // 图片, 图片名
        File f = new File(path);
        if (bmp == null) {
            return false;
        }
        FileOutputStream fOut = null;
        try {
            Log.e("replaceBitmapToFile", path);
            fOut = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, fOut);
            fOut.flush();
            fOut.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 删除 本软件的所有目录
     *
     * @return 删除成功返回 true，否则返回 false
     */
    public boolean DeleteMainDir() {
        File file = new File(path_app);
        return file.exists() && deleteDirectory(path_app);
    }

    /**
     * 删除文件夹以及目录下的文件
     *
     * @param filePath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public boolean deleteDirectory(String filePath) {
        boolean flag = false;
        //如果filePath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        //遍历删除文件夹下的所有文件(包括子目录)
        for (File file : files) {
            if (file.isFile()) {
                //删除子文件
                flag = deleteFile(file.getAbsolutePath());
                if (!flag) break;
            } else {
                //删除子目录
                flag = deleteDirectory(file.getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前空目录
        return dirFile.delete();
    }

    /**
     * 删除单个文件
     *
     * @param filePath 被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return file.isFile() && file.exists() && file.delete();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

}