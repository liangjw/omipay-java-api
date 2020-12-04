package com.matrix.omipay;

/**
 * @Author: Gene
 * @Date: 2020/12/4 9:57
 */
public class OmipayConfig {

    //商户号
    String mNumber;

    //密钥
    String secretKey;

    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
