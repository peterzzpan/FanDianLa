package com.huaimikeji.fandianla.framework;

import java.util.Objects;

/**
 * Created by Administrator on 2016/4/13.
 */
public interface IBaseView {

    public void showToast(int msgId);

    public void showProgress(Object object);

    public void dismissProgress();
}
