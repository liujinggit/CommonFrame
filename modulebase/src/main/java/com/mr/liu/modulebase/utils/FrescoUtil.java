package com.mr.liu.modulebase.utils;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RelativeLayout;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by ymlong on Nov 24, 2015.
 */
public final class FrescoUtil {

    public static final int PAGE_PADDING_IN_DP = 16;
    private static FrescoUtil instance;

    public static FrescoUtil getInstense() {
        if (instance == null) {
            synchronized (FrescoUtil.class) {
                if (instance == null) {
                    instance = new FrescoUtil();
                }
            }
        }
        return instance;
    }

    public void frescoResize(Uri uri, int width, int height, SimpleDraweeView sdv) {
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(sdv.getController())
                .setImageRequest(request)
                .build();
        sdv.setController(controller);
    }

    public DraweeController createResizeController(final Context context, final int
            parentWidth, final SimpleDraweeView imageView, String url) {
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(
                    String id,
                    @Nullable ImageInfo imageInfo,
                    @Nullable Animatable anim) {
                if (imageInfo == null) {
                    return;
                }
                Log.d("FrescoActivity",
                        "Final image received! " + imageInfo.getWidth() + imageInfo.getHeight());
                setSize(context, parentWidth, imageView, imageInfo.getWidth(),
                        imageInfo.getHeight());
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
                FLog.d(getClass(), "Intermediate image received");

            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                FLog.e(getClass(), throwable, "Error loading %s", id);
            }
        };
        return Fresco.newDraweeControllerBuilder()
                .setControllerListener(controllerListener)
                .setUri(Uri.parse(url))
                .build();
    }

    private void setSize(Context context, int parentWidth, SimpleDraweeView imageView, int
            width, int height) {
        //窗口的宽度
        int size = parentWidth - PixelUtil.dp2px(context, PAGE_PADDING_IN_DP);
        height = (int) (height * (((double) size) / width));
        width = size;
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(width,
                height);
        param.addRule(RelativeLayout.CENTER_IN_PARENT);
        imageView.setLayoutParams(param);
    }

    public void clearCache(Uri uri) {
        if (uri == null) {
            return;
        }
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.evictFromMemoryCache(uri);
        imagePipeline.evictFromDiskCache(uri);
    }

}
