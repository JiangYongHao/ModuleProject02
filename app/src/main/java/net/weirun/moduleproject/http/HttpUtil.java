package net.weirun.moduleproject.http;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadQueue;
import com.yolanda.nohttp.download.DownloadRequest;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by JiangYongHao on 2016/7/5.
 */
public class HttpUtil {

    private static HttpUtil httpUtil;
    private static RequestQueue requestQueue;
    private Context mContext;
    public static final int TIME_OUT = 10000;

    private net.weirun.moduleproject.http.OnResponseListener listener;

    private static DownloadQueue downloadQueue;

    private net.weirun.moduleproject.http.DownloadListener mDownloadListener;

    public static HttpUtil newInstance() {

        if (httpUtil == null) {
            httpUtil = new HttpUtil();
            requestQueue = NoHttp.newRequestQueue();
            downloadQueue = NoHttp.newDownloadQueue();
        }
        return httpUtil;
    }

    /**
     * 当有文件上传的时候使用此方法
     *
     * @param url      请求路径
     * @param what     请求标志
     * @param params   请求附加键值对 Map
     * @param listener 请求的监听
     */
    public void post(@NonNull String url, int what, Map<String, String> params, net.weirun.moduleproject.http.OnResponseListener listener) {

        this.listener = listener;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        setCancelSign(what, request);
        if (params != null) {
            request.add(params);
        }
        requestQueue.add(what, request, responseListener);
    }

    /**
     * 当有文件上传的时候使用此方法
     *
     * @param url      请求路径
     * @param what     请求标志
     * @param params   请求附加键值对 Map
     * @param files    请求上传的文件的路径
     * @param listener 请求的监听
     */
    public void post(@NonNull String url, int what, Map<String, String> params, Map<String, String> files, net.weirun.moduleproject.http.OnResponseListener listener) {

        this.listener = listener;
        Request<String> request = createRequest(url, RequestMethod.POST);
        setCancelSign(what, request);
        if (params != null) {
            request.add(params);
        }
        addFiles(files, request);
        requestQueue.add(what, request, responseListener);
    }


    /**
     * @param request
     * @param what
     * @param listener
     */
    public void post(@NonNull Request<String> request, int what, net.weirun.moduleproject.http.OnResponseListener listener) {
        this.listener = listener;
        if (request != null) {
            request.setConnectTimeout(TIME_OUT);
            requestQueue.add(what, request, responseListener);
        }
    }


    /**
     * @param url      请求的路径Url
     * @param what     请求标志：可以用于取消连接
     * @param listener 请求监听
     */
    public void get(@NonNull String url, int what, net.weirun.moduleproject.http.OnResponseListener listener) {
        this.listener = listener;
//        this.mContext = context;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.GET);
        setCancelSign(what, request);
        requestQueue.add(what, request, responseListener);
    }


    /**
     * 根据路径 ，下载文件
     *
     * @param what        下载标志：可用于取消下载
     * @param url         下载文件的url
     * @param path        下载的文件的存放路径
     * @param isDeleteOld 是否删除已有的文件（如果有）
     * @param listener    下载监听器
     */
    public void downloadFile(int what, @NonNull String url, @NonNull String path, boolean isDeleteOld, net.weirun.moduleproject.http.DownloadListener listener) {
        if (url != null) {
            this.mDownloadListener = listener;
            DownloadRequest downloadRequest = NoHttp.createDownloadRequest(url, path, isDeleteOld);
            setCancelSign(what, downloadRequest);
            downloadQueue.add(what, downloadRequest, downloadListener);
        } else {

        }

    }

    /**
     * 根据路径 ，下载文件
     *
     * @param what        下载标志：可用于取消下载
     * @param url         下载文件的url
     * @param path        下载的文件的存放路径
     * @param fileName    下载的文件的自定义名称
     * @param isRange     是否断点下载
     * @param isDeleteOld 是否删除已有的文件（如果有）
     * @param listener    下载监听器
     */
    public void downloadFile(int what, @NonNull String url, @NonNull String path, String fileName, boolean isRange, boolean isDeleteOld, net.weirun.moduleproject.http.DownloadListener listener) {

        this.mDownloadListener = listener;
        DownloadRequest downloadRequest = NoHttp.createDownloadRequest(url, path, fileName, isRange, isDeleteOld);
        setCancelSign(what, downloadRequest);
        downloadQueue.add(what, downloadRequest, downloadListener);
    }


    /**
     * 穿件请求
     */
    private Request createRequest(@NonNull String url, @NonNull RequestMethod method) {
        return NoHttp.createStringRequest(url, method);
    }

    /**
     * 把文件添加到请求
     *
     * @param files
     * @param request
     */
    private void addFiles(Map<String, String> files, Request<String> request) {
        if (files != null) {
            Set<Map.Entry<String, String>> entries = files.entrySet();
//                Iterator<Map.Entry<String, String>> it = entries.iterator();
            for (Map.Entry<String, String> entry : entries) {
                if (entry != null) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (value != null) {
                        File file = new File(value);
                        request.add(key, file);
                    }
                }
            }
        }
    }

    /**
     * 设置请求的取消标志
     *
     * @param what
     * @param request
     */
    private void setCancelSign(int what, DownloadRequest request) {
        if (request != null) {
            request.setCancelSign(what);
            request.setConnectTimeout(TIME_OUT);
        }
    }

    /**
     * 设置请求的取消标志
     *
     * @param what
     * @param request
     */
    private void setCancelSign(int what, Request<String> request) {
        if (request != null) {
            request.setCancelSign(what);
            request.setConnectTimeout(TIME_OUT);
        }
    }


    /**
     * 取消数据请求
     *
     * @param sign
     */
    public void cancelBySign(int sign) {
        requestQueue.cancelBySign(sign);
    }

    /**
     * 取消下载请求
     *
     * @param sign
     */
    public void cancelDownloadBySign(int sign) {
        downloadQueue.cancelBySign(sign);
    }

    /**
     * 取消所有的数据请求请求
     */
    public void cancelAll() {
        requestQueue.cancelAll();
    }

    /**
     * 取消所有的下载请求请求
     */
    public void cancelAllDownload() {
        requestQueue.cancelAll();
    }

    /**
     * 取消所有的请求
     */
    public void cancelAllRequest() {
        requestQueue.cancelAll();
        downloadQueue.cancelAll();
    }


    private OnResponseListener responseListener = new OnResponseListener() {
        @Override
        public void onStart(int what) {
            if (listener != null) {
                listener.onStart(what);
            }
        }

        @Override
        public void onSucceed(int what, Response response) {
            if (listener != null) {
                listener.onSucceed(what, response.get().toString());
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            if (mContext != null)
                Toast.makeText(mContext, "连接失败！", Toast.LENGTH_SHORT).show();

            if (listener != null)
                onFailed(what, url, tag, exception, responseCode, networkMillis);
        }

        @Override
        public void onFinish(int what) {
            if (listener != null) {
                listener.onFinish(what);
            }
        }
    };

    /**
     * 下载文件监听器
     */
    private DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onDownloadError(int what, Exception exception) {
            if (mDownloadListener != null) {
                mDownloadListener.onDownloadError(what, exception);
            }
        }

        @Override
        public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {
            if (mDownloadListener != null) {
                mDownloadListener.onStart(what, isResume, rangeSize, responseHeaders, allCount);
            }
        }

        @Override
        public void onProgress(int what, int progress, long fileCount) {
            if (mDownloadListener != null) {
                mDownloadListener.onProgress(what, progress, fileCount);
            }
        }

        @Override
        public void onFinish(int what, String filePath) {
            if (mDownloadListener != null) {
                mDownloadListener.onFinish(what, filePath);
            }
        }

        @Override
        public void onCancel(int what) {
            if (mDownloadListener != null) {
                mDownloadListener.onCancel(what);
            }
        }
    };
}
