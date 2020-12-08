package com.matrix.omipay.request;

/**
 * @Author: Gene
 * @Date: 2020/12/4 12:37
 */
public class JsapiOrderRequest {

    String order_name;

    String currency = "AUD";

    Integer amount;

    String notify_url;

    String redirect_url;

    String out_order_no;

    Integer direct_pay = 1;

    Integer show_pc_pay_url = 0;

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }

    public String getOut_order_no() {
        return out_order_no;
    }

    public void setOut_order_no(String out_order_no) {
        this.out_order_no = out_order_no;
    }

    public Integer getDirect_pay() {
        return direct_pay;
    }

    public void setDirect_pay(Integer direct_pay) {
        this.direct_pay = direct_pay;
    }

    public Integer getShow_pc_pay_url() {
        return show_pc_pay_url;
    }

    public void setShow_pc_pay_url(Integer show_pc_pay_url) {
        this.show_pc_pay_url = show_pc_pay_url;
    }
}
