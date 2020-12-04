package com.matrix.omipay.response;

import java.math.BigDecimal;

/**
 * @Author: Gene
 * @Date: 2020/12/4 10:16
 */
public class ExchangeRateResponse extends AbstractOmipayResponse{

    //汇率
    BigDecimal rate;

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
