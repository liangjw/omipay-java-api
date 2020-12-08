package com.matrix.omipay;

import org.apache.commons.codec.digest.DigestUtils;
import java.util.Date;
import java.util.Random;

/**
 * @Author: Gene
 * @Date: 2020/12/4 10:18
 */
public class OmipayUtil {

    /**
     * 将输入值用"&"串起来然后使用进行MD5加密”
     * @param values
     * @return 处理并加密后的字符串
     */
    private static String md5(String... values){
        StringBuilder strBuilder = new StringBuilder();
        for(String v : values) {
            strBuilder.append(v);
            strBuilder.append("&");
        }
        strBuilder.deleteCharAt(strBuilder.length()-1);
        return DigestUtils.md5Hex(strBuilder.toString()).toUpperCase();
    }

    private static String getRandomString(int length){
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(3);
            long result=0;
            switch(number){
                case 0:
                    result=Math.round(Math.random()*25+65);
                    sb.append(String.valueOf((char)result));
                    break;
                case 1:
                    result=Math.round(Math.random()*25+97);
                    sb.append(String.valueOf((char)result));
                    break;
                case 2:
                    sb.append(String.valueOf(new Random().nextInt(10)));
                    break;
            }


        }
        return sb.toString();
    }


    /**
     * 生成签名字符串
     * @param nNumber
     * @param timestamp
     * @param secretKey
     * @return
     */
    public static String generateSignKey(String nNumber,String timestamp,String nonceStr,String secretKey){
        return md5(nNumber,timestamp,nonceStr,secretKey);
    }

    /**
     * 生成用于签名的URL请求参数字符串
     * @param nNumber
     * @param secretKey
     * @return
     */
    public static String generateSignUrlstr(String nNumber,String secretKey){

        String timestamp = String.valueOf(new Date().getTime());
        String randomStr = getRandomString(10);
        String signKey = generateSignKey(nNumber,timestamp,randomStr,secretKey);

        return String.format(
                "m_number=%s&timestamp=%s&nonce_str=%s&sign=%S",
                nNumber,timestamp,randomStr,signKey
        );
    }

}
