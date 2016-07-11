package net.weirun.moduleproject.http;

import com.yolanda.nohttp.rest.Response;

/**
 * Created by JiangYongHao on 2016/7/5.
 */
public interface OnResponseListener {

    /**
     *  当请求开始.
     *
     * @param what the credit of the incoming request is used to distinguish between multiple requests.
     */
    void onStart(int what);

    /**
     * 请求成功返回数据
     *
     * @param what 所设置的请求码
     * @param data 请求成功返回的数据
     */
    void onSucceed(int what,String data);

    /**
     * 当请求失败的时候执行此方法
     *
     * @param what          the credit of the incoming request is used to distinguish between multiple requests.
     * @param url           url.
     * @param tag           tag of request callback.
     * @param exception     error types. Error types include the following:<p>{@link com.yolanda.nohttp.error.NetworkError} The network is not available, please check the network.</p>
     *                      <p>{@link com.yolanda.nohttp.error.TimeoutError} Connect to the server or a timeout occurred while reading data.</p>
     *                      <p>{@link com.yolanda.nohttp.error.UnKnownHostError} Is not found in the network of the target server.</p>
     *                      <p>{@link com.yolanda.nohttp.error.URLError} Download url is wrong.</p>
     * @param responseCode  server response code.
     * @param networkMillis request process consumption time.
     */
    void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis);

    /**
     * 当请求完成
     *
     * @param what the credit of the incoming request is used to distinguish between multiple requests.
     */
    void onFinish(int what);
}
