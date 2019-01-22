package com.mr.liu.modulebase.view.dialog.base;

import android.view.View;

/**
 * Created by mrliu on 2018/12/31.
 * 此类用于:
 */

public interface IDialog {
    void dismiss();

    interface OnBuildListener {
        void onBuildChildView(IDialog dialog, View view, int layoutRes);
    }

    interface OnClickListener {
        void onClick(IDialog dialog);
    }

    interface OnDismissListener {
        /**
         * This method will be invoked when the dialog is dismissed.
         *
         * @param dialog the dialog that was dismissed will be passed into the
         *               method
         */
        void onDismiss(IDialog dialog);
    }
}
