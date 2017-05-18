package com.car.contractcar.myapplication.common.http;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.car.contractcar.myapplication.common.utils.Constant;
import com.car.contractcar.myapplication.common.utils.LocalUtil;
import com.car.contractcar.myapplication.common.utils.UIUtils;
import com.car.contractcar.myapplication.loginandr.activity.LoginActivity;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.disk.NoOpDiskTrimmableRegistry;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by doter on 2016/8/8.
 */
public class HttpUtil {
    public static final String TYPE = "application/octet-stream";
    public static String server_name = "http://172.16.120.65:8080/shop/admin/category/list.action";
    public static String serverIP = "59.110.5.105";
    public static String udpPort = "9966";
    public static String udpName = "ALL";
    public static String server = "http://59.110.5.105/carshop/";
    //外网
    public static String server_img = "http://59.110.5.105";
    //内网
    // public static String server_img = "http://59.110.5.105";
    private static String api_path = "front/";
    private static String admin_path = "admin/";
    private static String api_end = ".action";
    private static String img_user_path = "user_img/";
    private static String img_store_path = "store_img/";
    private static String img_food_path = "food_img/";
    public static OkHttpClient client;
    private static Context context;
    private static int MAX_MEM = 30 * ByteConstants.MB;

    //    public static Picasso picasso;
    public static String getApi_path(String action) {
        return server + api_path + action + api_end;
    }

    private static final OkHttpClient.Builder builder = new OkHttpClient.Builder();

    public static String getLevel_path() {
        return server + "admin/category/list.action";
    }

//    public static String getImage_path(String prame) {
//        return server_img + prame;
//    }

    public static int POOL_SIZE = 8;

    private static ExecutorService sExecutorService;

    private static int READ_TIME_OUT = 10 * 1000;

    private static int CONNECTE_TIME_OUT = 10 * 1000;

    //okhttp缓存


    //图形缓存
    //分配的可用内存
    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();
    //使用的缓存数量
    private static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;
    //小图极低磁盘空间缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
    private static final int MAX_SMALL_DISK_VERYLOW_CACHE_SIZE = 20 * ByteConstants.MB;

    //小图低磁盘空间缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
    private static final int MAX_SMALL_DISK_LOW_CACHE_SIZE = 60 * ByteConstants.MB;

    //默认图极低磁盘空间缓存的最大值
    private static final int MAX_DISK_CACHE_VERYLOW_SIZE = 20 * ByteConstants.MB;

    //默认图低磁盘空间缓存的最大值
    private static final int MAX_DISK_CACHE_LOW_SIZE = 60 * ByteConstants.MB;

    //默认图磁盘缓存的最大值
    private static final int MAX_DISK_CACHE_SIZE = 100 * ByteConstants.MB;
    //小图所放路径的文件夹名
    private static final String IMAGE_PIPELINE_SMALL_CACHE_DIR = "ImagePipelineCacheSmall";
    //默认图所放路径的文件夹名
    private static final String IMAGE_PIPELINE_CACHE_DIR = "ImagePipelineCacheDefault";


    public static ImagePipelineConfig getDefaultImagePipelineConfig(Context context) {
        //内存配置
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                MAX_MEMORY_CACHE_SIZE,// 内存缓存中总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE,// 内存缓存中图片的最大数量。
                MAX_MEMORY_CACHE_SIZE,// 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE,// 内存缓存中准备清除的总图片的最大数量。
                Integer.MAX_VALUE);// 内存缓存中单个图片的最大大小。
        //修改内存图片缓存数量，空间策略（这个方式有点恶心）
        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams = new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                return bitmapCacheParams;
            }
        };

        //小图片的磁盘配置
        DiskCacheConfig diskSmallCacheConfig = DiskCacheConfig.newBuilder(context).setBaseDirectoryPath(context.getApplicationContext().getCacheDir())//缓存图片基路径
                .setBaseDirectoryName(IMAGE_PIPELINE_SMALL_CACHE_DIR)//文件夹名
                .setMaxCacheSize(MAX_DISK_CACHE_SIZE)//默认缓存的最大大小。
                .setMaxCacheSizeOnLowDiskSpace(MAX_SMALL_DISK_LOW_CACHE_SIZE)//缓存的最大大小,使用设备时低磁盘空间。
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_SMALL_DISK_VERYLOW_CACHE_SIZE)//缓存的最大大小,当设备极低磁盘空间
                .setDiskTrimmableRegistry(NoOpDiskTrimmableRegistry.getInstance())
                .build();
        //默认图片的磁盘配置
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context).setBaseDirectoryPath(Environment.getExternalStorageDirectory().getAbsoluteFile())//缓存图片基路径
                .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)//文件夹名
                .setMaxCacheSize(MAX_DISK_CACHE_SIZE)//默认缓存的最大大小。
                .setMaxCacheSizeOnLowDiskSpace(MAX_DISK_CACHE_LOW_SIZE)//缓存的最大大小,使用设备时低磁盘空间。
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_DISK_CACHE_VERYLOW_SIZE)//缓存的最大大小,当设备极低磁盘空间
                .setDiskTrimmableRegistry(NoOpDiskTrimmableRegistry.getInstance())
                .build();

        //缓存图片配置
        ImagePipelineConfig.Builder configBuilder = OkHttpImagePipelineConfigFactory.newBuilder(context, client)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)
                .setSmallImageDiskCacheConfig(diskSmallCacheConfig)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setMemoryTrimmableRegistry(NoOpMemoryTrimmableRegistry.getInstance())
                .setResizeAndRotateEnabledForNetwork(true);

        // 就是这段代码，用于清理缓存
        NoOpMemoryTrimmableRegistry.getInstance().registerMemoryTrimmable(new MemoryTrimmable() {
            @Override
            public void trim(MemoryTrimType trimType) {
                final double suggestedTrimRatio = trimType.getSuggestedTrimRatio();
                Log.d("---", String.format("onCreate suggestedTrimRatio : %d", suggestedTrimRatio));
                if (MemoryTrimType.OnCloseToDalvikHeapLimit.getSuggestedTrimRatio() == suggestedTrimRatio
                        || MemoryTrimType.OnSystemLowMemoryWhileAppInBackground.getSuggestedTrimRatio() == suggestedTrimRatio
                        || MemoryTrimType.OnSystemLowMemoryWhileAppInForeground.getSuggestedTrimRatio() == suggestedTrimRatio
                        ) {
                    ImagePipelineFactory.getInstance().getImagePipeline().clearMemoryCaches();
                }
            }
        });

        return configBuilder.setDownsampleEnabled(true).setDecodeMemoryFileEnabled(true)
                .build();

    }

    public HttpUtil(Context mcontext) {
        this.context = mcontext;
        //// TODO: 2016/9/8   cache
        File sdcache = context.getFilesDir();
        int cacheSize = 40 * 1024 * 1024; // 10 MiB


        client = builder.cache(new Cache(sdcache.getAbsoluteFile(), cacheSize)).connectTimeout(60, TimeUnit.SECONDS).cookieJar(new CookiesManager()).build();
//        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(MAX_MEM, Integer.MAX_VALUE, MAX_MEM, Integer.MAX_VALUE, Integer.MAX_VALUE);
//        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams = new Supplier<MemoryCacheParams>() {
//            @Override
//            public MemoryCacheParams get() {
//                return bitmapCacheParams;
//            }
//        };
//
//        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context).setBaseDirectoryPathSupplier(new Supplier<File>() {
//            @Override
//            public File get() {
//                File sdcache = context.getFilesDir();
//                return sdcache;
//            }
//        }).setBaseDirectoryName("MYPROJECTDIRECOTRY").setMaxCacheSize(41943040L).setMaxCacheSizeOnLowDiskSpace(10485760L).setMaxCacheSizeOnVeryLowDiskSpace(2097152L).build();
//
//
//        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
//                .newBuilder(context, client)
//                .setDownsampleEnabled(true).setResizeAndRotateEnabledForNetwork(true).setDecodeMemoryFileEnabled(true).setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams).setMainDiskCacheConfig(diskCacheConfig)
//                .build();

        //方案一
        //Fresco.initialize(context, config);
        Fresco.initialize(context, getDefaultImagePipelineConfig(context));
    }

    static {
        sExecutorService = Executors.newCachedThreadPool();
    }


    /*

     */
    public static void get(final String url, final callBlack call, final Integer cache_maxAge_inseconds) {
        final String urltemp;
        if (Constant.USER != null) {
            if (url.contains("?")) {
                urltemp = url + "&token=" + Constant.USER.getToken();
            } else {
                urltemp = url + "?token=" + Constant.USER.getToken();
            }
        } else {
            urltemp = url;
        }
        Log.d("-----", "没问题，我去请求网络" + urltemp);
        sExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Request request = null;
                okhttp3.Response response = null;
                try {
                    if (cache_maxAge_inseconds == 0 || cache_maxAge_inseconds == null) {
                        //没有配置时间
                        request = new Request.Builder().cacheControl(new CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build()).url(urltemp).build();
                    } else {
                        request = new Request.Builder().cacheControl(new CacheControl.Builder().maxStale(cache_maxAge_inseconds, TimeUnit.SECONDS).build()).url(urltemp).build();
                    }

//                    if (!isCache) {
//                        //走网络
//                        request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
//                    }
                    response = client.newCall(request).execute();
                    if (call != null) {
                        if (response.isSuccessful()) {
                            String str = response.body().string();
                            Log.d("----response", str);
                            com.alibaba.fastjson.JSONObject object = JSON.parseObject(str);
                            Integer code = object.getInteger("status");
                            if (code == 1) {
                                call.succcess(str);
                            } else {
                                if (code == -2) {
                                    Log.d("----", "没有登陆");
                                    Constant.USER = null;
                                    UIUtils.SpputString(Constant.USER_SP, "");
                                    Intent i = new Intent(context, LoginActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(i);
                                    //没有登陆或者token过期
                                }
                                call.fail(str);
                            }
                        } else {
                            call.err();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    response.body().close();
                }
            }
        });
    }


    /**
     * post请求
     *
     * @param Url
     * @param map
     * @return
     * @throws IOException
     */

    public static void post(final String Url, final Map<String, Object> map, final callBlack callb) {
        if (Constant.USER != null) {
            map.put("token", Constant.USER.getToken());
        }
        Log.d("#######", "post调用了");
        sExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    FormBody.Builder body = new FormBody.Builder();
                    if (map != null) {
                        Set<String> item = map.keySet();
                        for (String str : item) {
                            body.add(str, "" + map.get(str));
                        }
                    }
                    FormBody formbody = body.build();
                    Request request = new Request.Builder()
                            .url(Url)
                            .post(formbody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            callb.err();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (callb != null) {
                                String stre = response.body().string();
                                Log.v("####reslut", stre);
                                JSONObject obj = null;
                                try {
                                    obj = new JSONObject(stre);
                                    int reCode = obj.getInt("status");
                                    if (1 == reCode) {
                                        callb.succcess(stre);
                                    } else {
                                        if (reCode == -2) {
                                            Log.d("----", "没有登陆");
                                            Constant.USER = null;
                                            UIUtils.SpputString(Constant.USER_SP, "");
                                            Intent i = new Intent(context, LoginActivity.class);
                                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(i);
                                            //没有登陆或者token过期
                                        }
                                        callb.fail(stre);
                                    }
                                } catch (Exception e) {
                                    callb.fail(stre);
                                } finally {
                                    response.body().close();
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.v("####POSTError", "" + e.toString());
                }
            }

        });


    }

    public static void postJson(final String Url, final Map<String, Object> map, final callBlack callb) {

        Log.d("#######", "post调用了");
        sExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    FormBody.Builder body = new FormBody.Builder();
                    if (map != null) {
                        Set<String> item = map.keySet();

                        for (String str : item) {
                            JSONArray jsonArray = (JSONArray) map.get(str);
                            if (jsonArray.size() > 0) {

                                Log.v("----------", str);
                                Log.v("--------++", "" + map.get(str));
                                body.add(str, "" + map.get(str));
                            }
                        }
                    }

                    FormBody formbody = body.build();
                    Request request = new Request.Builder()
                            .url(Url)
                            .post(formbody)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            callb.err();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (callb != null) {
                                String stre = response.body().string();
                                Log.v("####reslut", stre);
                                JSONObject obj = null;
                                try {
                                    obj = new JSONObject(stre);
                                    int reCode = obj.getInt("status");
                                    if (1 == reCode) {
                                        callb.succcess(stre);
                                    } else {
                                        callb.fail(stre);
                                    }
                                } catch (JSONException e) {
                                    callb.fail(stre);
                                } finally {
                                    response.body().close();
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.v("####POSTError", "" + e.toString());
                }
            }

        });


    }

    /**
     * 上傳文件
     *
     * @param url
     * @param gkey
     * @param files
     * @param callb
     */
    public static void uploadFile(final String url, final String gkey, final List<byte[]> files, final callBlack callb) {

        if (files == null) {
            callb.err();

            return;
        }
        sExecutorService.submit(new Runnable() {
            @Override
            public void run() {


                MultipartBody.Builder builder = new MultipartBody.Builder();
                builder.addFormDataPart("gkey", gkey);
                for (byte[] f : files) {
                    if (f.length > 0) {
                        RequestBody fileBody = RequestBody.create(MediaType.parse(TYPE), f);
                        builder.addFormDataPart("file", LocalUtil.getDateString() + ".jpg", fileBody);
                    }
                }

                RequestBody requestBody = builder.build();
                Request requestPostFile = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                client.newCall(requestPostFile).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        callb.fail(e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        callb.succcess(response.body().string());
                        response.body().close();
                    }
                });
            }
        });
    }

    public interface callBlack {
        void succcess(String code);

        void fail(String code);

        void err();

    }

    public interface callBlackIO {
        void succcess(InputStream i);

        void fail();

    }

    private class CookiesManager implements CookieJar {

        private final PersistentCookieStore cookieStore = new PersistentCookieStore(context);

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies;
        }

    }


    /**
     * get方法
     * 这个get方法 的缓存需要服务器的支持
     *
     * @param url
     * @param call
     * @param isCache
     */
    public static void get(final String url, final callBlack call, final boolean isCache) {
        final String urltemp;
        if (url.contains("?")) {
            urltemp = url + "&token=" + Constant.USER.getToken();
        } else {
            urltemp = url + "?token=" + Constant.USER.getToken();
        }
        sExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                okhttp3.Response response = null;
                try {
                    Request request = new Request.Builder().url(urltemp).build();
                    if (!isCache) {
                        //走网络
                        request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
                    }
                    response = client.newCall(request).execute();
                    if (call != null) {
                        if (response.isSuccessful()) {
                            String str = response.body().string();
                            com.alibaba.fastjson.JSONObject object = JSON.parseObject(str);
                            Integer code = object.getInteger("status");
                            if (code == 1) {
                                call.succcess(str);
                            } else if (code == 0) {
                                call.fail(str);
                            } else if (code == -2) {
                                Constant.USER = null;
                                Intent i = new Intent(context, LoginActivity.class);
                                context.startActivity(i);
                            }
                        } else {
                            call.err();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    response.body().close();
                }
            }
        });
    }


    //同步post
    public static String postMap(String url, Map<String, String> map) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        if (Constant.USER != null) {
            map.put("token", Constant.USER.getToken());
        }
        //遍历map
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.add(entry.getKey(), entry.getValue().toString());
            }
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder().url(url).post(body).build();
        Response execute = client.newCall(request).execute();
        if (execute.isSuccessful()) {
            return execute.body().string();
        } else {
            return null;
        }
    }


    public static void getImg(final String url, final callBlackIO call, final boolean isCache) {
        sExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                okhttp3.Response response = null;
                try {
                    Request request = new Request.Builder().url(url).build();

                    if (!isCache) {
                        //走网络
                        request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
                    }
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        InputStream stream = response.body().byteStream();
                        call.succcess(stream);
                    } else {
                        call.fail();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    response.body().close();
                }
            }
        });
    }
}






