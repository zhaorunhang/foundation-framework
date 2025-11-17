package com.zrh.framework.core.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Locale;
import java.util.ResourceBundle;

@Getter
@AllArgsConstructor
public enum ResultEnum {
    SUCCESS(200, "SUCCESS", "请求成功"),
    ERROR(500, "ERROR", "系统异常"),
    EXCEPTION(500, "EXCEPTION", "系统异常"),
    VALIDATE_FAILED(400, "VALIDATE_FAILED", "参数校验失败"),
    INVALID_ARGS(422, "INVALID_ARGS", "无效参数"),
    NOT_LOGIN(401, "NOT_LOGIN", "未登录"),
    NOT_POWER(403, "FORBIDDEN", "无权限");

    private final Integer code;

    private final String status;

    private final String defaultMessage;

    /**
     * 获取国际化消息
     *
     * @param locale 语言环境
     * @return 国际化后的 message，如果未找到则返回默认消息
     */
    public String getMessage(Locale locale) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
            return bundle.getString(this.status);
        } catch (Exception e) {
            return this.defaultMessage;
        }
    }

    /**
     * 获取消息，默认 Locale 为系统默认
     */
    public String getMessage() {
        return getMessage(Locale.getDefault());
    }

    /**
     * 判断是否成功
     */
    public boolean isSuccess() {
        return this == SUCCESS;
    }

    /**
     * 判断是否失败
     */
    public boolean isFailure() {
        return !isSuccess();
    }
}
