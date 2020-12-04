package com.matrix.omipay.response;

/**
 * @Author: Gene
 * @Date: 2020/12/4 10:07
 */
public class AbstractOmipayResponse implements OmipayResponse{

    private String return_code;

    private String error_code;

    private String error_msg;

    public String getRuturn_code() {
        return return_code;
    }

    public String getError_code() {
        return error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public Boolean isSuccess() {
        return "SUCCESS".equals(return_code);
    }
}
