package com.snow.ly.blog.common.bean;

public class Result {


    private Integer code;
    private String msg="";
    private Object data="";


    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public static Result over(){
        return new Result(Tips.DISABLED_TOEK.code,Tips.DISABLED_TOEK.msg);
    }

    public static Result success(){
        return new Result(Tips.SUCCESS.code,Tips.SUCCESS.msg);
    }


    public static Result success(Object data){
        return new Result(Tips.SUCCESS.code,Tips.SUCCESS.msg,data);
    }

    public static Result fail(){
        return new Result(Tips.FAIL.code,Tips.FAIL.msg);
    }

    public static Result fail(String msg){
        return new Result(Tips.FAIL.code,msg);
    }

    public static Result fail(Integer code,String msg){
        return new Result(code,msg);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
