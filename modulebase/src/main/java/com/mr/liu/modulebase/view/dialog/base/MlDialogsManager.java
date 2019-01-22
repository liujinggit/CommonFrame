package com.mr.liu.modulebase.view.dialog.base;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by mrliu on 2018/12/31.
 * 此类用于:dialog管理类,支持多个dialog弹出
 */

public class MlDialogsManager {
    private volatile boolean showing = false;//是否有dialog在展示
    private ConcurrentLinkedQueue<DialogWrapper> dialogQueue = new ConcurrentLinkedQueue<>();

    private MlDialogsManager() {
    }

    public static MlDialogsManager getInstance() {
        return DialogHolder.instance;
    }

    private static class DialogHolder {
        private static MlDialogsManager instance = new MlDialogsManager();
    }

    /**
     * 请求加入队列并展示
     *
     * @param dialogWrapper DialogWrapper
     * @return 加入队列是否成功
     */
    public synchronized boolean requestShow(DialogWrapper dialogWrapper) {
        boolean b = dialogQueue.offer(dialogWrapper);
        checkAndDispatch();
        return b;
    }

    /**
     * 结束一次展示 并且检查下一个弹窗
     */
    public synchronized void over() {
        showing = false;
        next();
    }

    private synchronized void checkAndDispatch() {
        if (!showing) {
            next();
        }
    }

    /**
     * 弹出下一个弹窗
     */
    private synchronized void next() {
        DialogWrapper poll = dialogQueue.poll();
        if (poll == null) return;
        MlDialog.Builder dialog = poll.getDialog();
        if (dialog != null) {
            showing = true;
            dialog.show();
        }
    }
}
