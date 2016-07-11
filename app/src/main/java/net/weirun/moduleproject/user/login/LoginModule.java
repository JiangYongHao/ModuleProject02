package net.weirun.moduleproject.user.login;

/**
 * Created by JiangYongHao on 2016/7/6.
 */
public interface LoginModule {
    /**
     * 登录
     */
    void login(String userName,String passWord,String url,OnLoginListener loginListener);

    /**
     * 退出
     */
    void exit();
}
