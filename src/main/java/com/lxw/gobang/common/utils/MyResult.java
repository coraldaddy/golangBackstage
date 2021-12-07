package com.lxw.gobang.common.utils;


import org.springframework.util.StringUtils;

public class MyResult<T> {
//    @ApiModelProperty("返回对象")
    private T data;
//    @ApiModelProperty("返回状态码")
    private Integer code;
//    @ApiModelProperty("返回信息")
    private String msg;

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MyResult() {
    }

    public MyResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public MyResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        if (!StringUtils.isEmpty(data)) {
            this.data = data;
        }

    }

    public static MyResult success() {
        return success("操作成功");
    }

    public static MyResult success(Object data) {
        return success("操作成功", data);
    }

    public static MyResult success(String msg) {
        return success(msg, (Object)null);
    }

    public static MyResult success(String msg, Object data) {
        return new MyResult(200, msg, data);
    }

    public static MyResult error() {
        return error("操作失败");
    }

    public static MyResult error(String msg) {
        return error(msg, (Object)null);
    }

    public static MyResult error(String msg, Object data) {
        return new MyResult(500, msg, data);
    }

    public static MyResult error(int code, String msg) {
        return new MyResult(code, msg, (Object)null);
    }
}
