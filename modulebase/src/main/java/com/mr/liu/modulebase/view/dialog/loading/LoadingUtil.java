package com.mr.liu.modulebase.view.dialog.loading;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mr.liu.modulebase.R;

/**
 * Created by mrliu on 2018/12/30.
 * 此类用于:自定义加载等待框
 */

public class LoadingUtil {

    public static Dialog createLoadingDialog(Context context, String content) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.base_dialog_loading, null);// 得到加载view
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_loading_view);// 加载布局
        TextView tvContent = (TextView) view.findViewById(R.id.tipTextView);
        if (!TextUtils.isEmpty(content)) {
            tvContent.setText(content);
        }
        Dialog loadingDialog = new Dialog(context, R.style.MyDialogStyle);// 创建自定义样式dialog
        loadingDialog.setCancelable(true); // 是否可以按“返回键”消失
        loadingDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        /**
         *将显示Dialog的方法封装在这里面
         */
        Window window = loadingDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.dialogStyle);
        loadingDialog.show();

        return loadingDialog;
    }

    /**
     * 关闭dialog
     */
    public static void closeDialog(Dialog mDialogUtils) {
        if (mDialogUtils == null || !mDialogUtils.isShowing()) {
            return;
        }
        mDialogUtils.dismiss();
        mDialogUtils = null;
    }
}
