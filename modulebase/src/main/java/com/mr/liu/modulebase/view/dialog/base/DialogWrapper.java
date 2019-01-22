package com.mr.liu.modulebase.view.dialog.base;

/**
 * Created by mrliu on 2018/12/31.
 * 此类用于:管理多个dialog 按照dialog的优先级依次弹出
 */

public class DialogWrapper {
    private MlDialog.Builder dialog;//统一管理dialog的弹出顺序

    public DialogWrapper(MlDialog.Builder dialog) {
        this.dialog = dialog;
    }

    public MlDialog.Builder getDialog() {
        return dialog;
    }

    public void setDialog(MlDialog.Builder dialog) {
        this.dialog = dialog;
    }
}
