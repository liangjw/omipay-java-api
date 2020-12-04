package com.matrix.omipay.response;

/**
 * @Author: Gene
 * @Date: 2020/12/4 12:37
 */
public class JsapiOrderResponse extends AbstractOmipayResponse{

    String order_no;

    String pay_url;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getPay_url() {
        return pay_url;
    }

    public void setPay_url(String pay_url) {
        this.pay_url = pay_url;
    }
}
