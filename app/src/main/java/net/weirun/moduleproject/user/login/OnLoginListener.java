package net.weirun.moduleproject.user.login;

/**
 * 登录接口
 * Created by JiangYongHao on 2016/7/6.
 */
public interface OnLoginListener {

    /**
     * 登录成功
     *
     * @param data
     */
    void onSuccess(String data);

    /**
     * 登录失败
     */
    void onFailed(String message);

    /**
     * 取消登录
     *
     * @param data
     */
    void onCancel(String data);


}
