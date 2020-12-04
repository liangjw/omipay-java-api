package com.matrix.omipay.response;

/**
 * @Author: Gene
 * @Date: 2020/12/4 10:03
 */
public interface OmipayResponse<T> {

    //状态码
    String getRuturn_code();

    //错误码
    String getError_code();

    //错误信息
    String getError_msg();

    //是否成功
    Boolean isSuccess();


}
