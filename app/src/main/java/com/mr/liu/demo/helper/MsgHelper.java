package com.mr.liu.demo.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.SparseArray;

import com.mr.liu.demo.R;
import com.mr.liu.demo.utils.HeartUtil;


/**
 * Created by mrliu on 2018/12/28.
 * 此类用于:
 */

public class MsgHelper {
    public static final String LOG_TAG = MsgHelper.class.getSimpleName();

    private static MsgHelper instance;

    private SparseArray<CharSequence> levelSequence = new SparseArray<>(128);
    private static final boolean CACH_IMAGE_ENABLED = false;

    //   进入提示颜色
    @ColorInt
    private int color_yellow;

    @ColorInt
    private int color_white;

    private int color_black;
    @ColorInt
    private int color_green;

    private int[] heartColorArray;

    private MsgHelper(Context context) {
        color_yellow = getColor(context, R.color.yellow_content);
        color_white = getColor(context, R.color.white_content);
        color_green = getColor(context, R.color.green_content);
        color_black = getColor(context, R.color.black_content);
        heartColorArray = context.getResources().getIntArray(R.array.multi_colors);
    }

    @ColorInt
    private int getColor(Context context, @ColorRes int color) {
        return ContextCompat.getColor(context, color);
    }

    public static MsgHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (MsgHelper.class) {
                if (instance == null) {
                    instance = new MsgHelper(context);
                }
            }
        }
        return instance;
    }

    public CharSequence buildUserName(@NonNull String username) {
        return SpanHelper.createSpan("", String.format("%s: ", username),
                new ForegroundColorSpan(color_yellow));
    }


    public CharSequence buildPublicMsgContent(@NonNull String msg) {
        return SpanHelper.createSpan("", msg, new ForegroundColorSpan(color_white));
    }


    public CharSequence buildPublicSysMsgWelcome(@NonNull String welcome) {
        return SpanHelper.createSpan("", welcome, new ForegroundColorSpan(color_green));
    }

    public CharSequence buildPublicSysMsgName(@NonNull String msg) {
        return SpanHelper.createSpan("", msg, new ForegroundColorSpan(color_yellow));
    }

    public CharSequence buildPrvMsgContent(@NonNull String msg) {
        return SpanHelper.createSpan("", msg, new ForegroundColorSpan(color_black));
    }

    @Nullable
    @CheckResult
    public CharSequence buildLevel(Context context, int level) {
        return null;
    }

    public CharSequence buildHeart(Context context, int colorIndex) {
        int color = heartColorArray[colorIndex];

        int width = context.getResources().getDimensionPixelSize(R.dimen.dp18);
        int height = context.getResources().getDimensionPixelSize(R.dimen.dp18);
        //Must use a BitmapConfig with Alpha channel!
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
//        canvas.drawColor(color);
        HeartUtil.drawHeart(canvas, 0.72F, color);

        ImageSpan span = new ImageSpan(context, bitmap);
        SpannableStringBuilder ssb = new SpannableStringBuilder(String.valueOf(colorIndex));
        ssb.setSpan(span, 0, ssb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        levelSequence.put(colorIndex, ssb);
        return ssb;
    }
}
