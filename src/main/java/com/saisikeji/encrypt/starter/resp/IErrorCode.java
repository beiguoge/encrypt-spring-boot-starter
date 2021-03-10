package com.saisikeji.encrypt.starter.resp;

/**
 * 封装API的错误码
 *
 * @author beiguoge
 * @version 1.0
 * @date 2021/1/26 9:46
 */
public interface IErrorCode {
    /**
     * 状态码
     * @return 状态码
     */
    long getCode();

    /**
     * 错误信息
     * @return 错误信息
     */
    String getMessage();
}
