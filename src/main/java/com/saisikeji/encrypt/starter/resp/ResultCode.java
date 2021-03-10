package com.saisikeji.encrypt.starter.resp;

/**
 * 常用API操作码
 *
 * @author beiguoge
 * @version 1.0
 * @date 2021/1/26 9:43
 */
public enum ResultCode implements IErrorCode {
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败");

    private final long code;
    private final String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
