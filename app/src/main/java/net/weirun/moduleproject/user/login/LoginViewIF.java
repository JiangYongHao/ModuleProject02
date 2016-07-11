package net.weirun.moduleproject.user.login;

/**
 * Created by JiangYongHao on 2016/7/6.
 */
public interface LoginViewIF {

    /**
     * 提示信息
     *
     * @param message
     */
    void showToast(String message);

    /**
     * 获取登录信息
     *
     * @param userName
     * @param passWord
     */
    void getLoginMessage(String userName, String passWord);

    /**
     * 调转页面
     */
    void moveToIndex();
}
