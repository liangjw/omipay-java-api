package com.matrix.omipay.response;

/**
 * @Author: Gene
 * @Date: 2020/12/6 21:22
 */
public class PayNotifyResponse extends AbstractOmipayResponse {

    String return_code;

    String nonce_str;

    Long timestamp;

    String sign;

    String order_no;

    String out_order_no;

    String currency;

    Integer total_amount;

    String order_time;

    String pay_time;

    Integer exchange_rate;

    Integer cny_amount;

    public String getReturn_code() {
        return return_code;
    }

    @Override
    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getOut_order_no() {
        return out_order_no;
    }

    public void setOut_order_no(String out_order_no) {
        this.out_order_no = out_order_no;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Integer total_amount) {
        this.total_amount = total_amount;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public Integer getExchange_rate() {
        return exchange_rate;
    }

    public void setExchange_rate(Integer exchange_rate) {
        this.exchange_rate = exchange_rate;
    }

    public Integer getCny_amount() {
        return cny_amount;
    }

    public void setCny_amount(Integer cny_amount) {
        this.cny_amount = cny_amount;
    }
}
