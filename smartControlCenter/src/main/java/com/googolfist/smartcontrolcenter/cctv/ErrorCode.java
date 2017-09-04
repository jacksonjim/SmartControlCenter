package com.googolfist.smartcontrolcenter.cctv;

/**
 * Created by Administrator on 2017/7/4.
 */

public class ErrorCode {
    /**
     * 200	操作成功	请求成功
     */
    public static final String REQUEST_TOKEN_SUCCESS = "200";
    /**
     * 10001	参数错误	参数为空或格式不正确
     */
    public static final String REQUEST_PARAMS_ERROR = "10001";
    /**
     * 10005	appKey异常	appKey被冻结
     */
    public static final String ERROR_APPKEY_EXCEPTION = "10005";
    /**
     * 10017	appKey不存在	确认appKey是否正确
     */
    public static final String ERROR_APPKEY_NOT_EXIST = "10017";
    /**
     * 10030	appkey和appSecret不匹配
     */
    public static final String ERROR_APPKEY_APPSECRET_NOT_MATCH = "10030";
    /**
     * 49999 数据异常
     */
    public static final String ERROR_DATA_EXCEPTION = "49999";
}
