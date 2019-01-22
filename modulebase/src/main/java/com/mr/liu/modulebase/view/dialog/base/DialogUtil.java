package com.mr.liu.modulebase.view.dialog.base;

import android.content.Context;
import android.text.TextUtils;

import com.mr.liu.modulebase.R;

import java.util.HashMap;

/**
 * Created by mrliu on 2018/12/31.
 * 此类用于:dialog工具类
 */

public class DialogUtil {

    /**
     * 带有一个按钮的dialog
     *
     * @param context               Context
     * @param title                 标题
     * @param content               内容
     * @param btn1Str               button文字
     * @param positiveClickListener 点击事件
     */
    public static void createDefaultDialog(Context context, String title, String content, String btn1Str, IDialog.OnClickListener positiveClickListener) {
        createDefaultDialog(context, title, content, btn1Str, positiveClickListener, "", null);
    }

    /**
     * 带有两个按钮的dialog
     *
     * @param context               Context
     * @param title                 标题
     * @param content               内容
     * @param btn1Str               左边按钮
     * @param negativeClickListener 左边点击事件
     * @param btn2Str               右边按钮
     * @param positiveClickListener 右边点击事件
     */
    public static void createDefaultDialog(Context context, String title, String content, String btn1Str,
                                           IDialog.OnClickListener positiveClickListener, String btn2Str, IDialog.OnClickListener negativeClickListener) {
        MlDialog.Builder builder = new MlDialog.Builder(context);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(content)) {
            builder.setContent(content);
        }
        if (positiveClickListener != null) {
            if (TextUtils.isEmpty(btn1Str)) {
                builder.setPositiveButton(positiveClickListener);
            } else {
                builder.setPositiveButton(btn1Str, positiveClickListener);
            }
        }
        if (negativeClickListener != null) {
            if (TextUtils.isEmpty(btn2Str)) {
                builder.setNegativeButton(negativeClickListener);
            } else {
                builder.setNegativeButton(btn2Str, negativeClickListener);
            }
        }
        builder.show();
    }

    private static HashMap<String, MlDialog> hashMap = new HashMap<>();

    /**
     * 创建加载框的 dialog
     *
     * @param context Context
     */
    public static void createLoadingDialog(Context context) {
        closeLoadingDialog(context);
        MlDialog.Builder builder = new MlDialog.Builder(context);
        MlDialog dialog = builder.setDialogView(R.layout.base_dialog_loading)
                .setWindowBackgroundP(0.5f)
                .setCancelableOutSide(false)
                .setCancelable(false)
                .show();
        hashMap.put(context.getClass().getSimpleName(), dialog);

    }

    /**
     * 关闭loading dialog
     *
     * @param context Context
     */
    public static void closeLoadingDialog(Context context) {
        String dialogKey = context.getClass().getSimpleName();
        MlDialog dialog = hashMap.get(dialogKey);
        if (dialog != null) {
            hashMap.remove(dialogKey);
            dialog.dismiss();
        }

    }
}
