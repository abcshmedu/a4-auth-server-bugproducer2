package edu.hm.bugproducer.model;

public class Error {

    private int code;
    private String msg;

    public Error() {
        code=9000;
        msg="Its over!!!";

    }

    public Error(int code, String msg) {
        this.code = code;
        this.msg = msg;
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
