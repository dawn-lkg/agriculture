package com.example.agriculture.entity;

/**
 * 文章表(Article)表实体类
 *
 * @author 陈黎明
 * @since 2022/4/14 9:47
 */
public enum AppHttpCodeEnum {
    SUCCESS(200,"操作成功"),
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONENUMBER_EXIST(502,"手机号已存在"),
    REQUIRE_USERNAME(504,"必需填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    FILE_TYPE_ERROR(507,"文件类型错误,请上次png文件"),
    NICKNAME_NOT_NULL(509,"昵称不能为空"),
    PASSWORD_NOT_NULL(511,"密码不能为空"),
    EMAIL_NOT_NULL(512,"邮箱不能为空"),
    USERNAME_NOT_NULL(508,"用户名不能为空"),
    NICKNAME_EXIST(510,"昵称已存在"),
    FILE_NOT_NULL(513,"文件不能为空"),
    BALANCE_DUE(514,"余额不足");
    int code;
    String msg;
    AppHttpCodeEnum(int code, String Message){
        this.code=code;
        this.msg=Message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
