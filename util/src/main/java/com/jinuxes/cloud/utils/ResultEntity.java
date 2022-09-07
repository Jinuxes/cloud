package com.jinuxes.cloud.utils;

/**
 * 同一项目中Ajax请求返回结果
 * @param <T>
 */
public class ResultEntity<T> {
    private String code;  // 结果状态，"0"成功，“1”失败
    private String msg;  // 失败时错误信息
    private T data;  // 请求成功时的数据

    public static ResultEntity successWithoutData(){
        return new ResultEntity(CloudConstant.SUCCESS,null,null);
    }

    // 泛型方法，数据类型调用时才确定
    public static <E> ResultEntity<E> successWithData(E data){
        return new ResultEntity<E>(CloudConstant.SUCCESS,null, data);
    }

    public static ResultEntity failed(String message){
        return new ResultEntity(CloudConstant.FAILED,message,null);
    }

    public ResultEntity() {
    }

    public ResultEntity(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
