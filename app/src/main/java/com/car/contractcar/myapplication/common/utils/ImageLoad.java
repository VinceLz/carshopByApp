package com.car.contractcar.myapplication.common.utils;

import android.net.Uri;
import android.util.Log;

import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;

/**
 * Created by macmini2 on 17/3/21.
 */

public class ImageLoad {

    private static final String IMAGE_SERVER_PATH = "http://59.110.5.105";

    public static void loadImg(SimpleDraweeView simpleDraweeView, String url) {
        Uri uri = Uri.parse(IMAGE_SERVER_PATH + url);
        GenericDraweeHierarchy hierarchy = simpleDraweeView.getHierarchy();
        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP);
        simpleDraweeView.setImageURI(uri);
    }

}
