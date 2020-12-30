package com.matrix.omipay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matrix.omipay.request.JsapiOrderRequest;
import com.matrix.omipay.request.MiniProgramOrderRequest;
import com.matrix.omipay.response.ExchangeRateResponse;
import com.matrix.omipay.response.JsapiOrderResponse;
import com.matrix.omipay.response.MiniprogramOrderResponse;
import com.matrix.omipay.response.PayNotifyResponse;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Gene
 * @Date: 2020/12/4 9:54
 */
public class OmipayClient {

    Logger logger  = LoggerFactory.getLogger(OmipayClient.class);

    private OmipayConfig omipayConfig;

    private RestTemplate restTemplate;

    ObjectMapper om = new ObjectMapper();

    public OmipayClient(OmipayConfig omipayConfig){
        this.omipayConfig = omipayConfig;
        this.restTemplate = defaultRestTemplate();
    }

    public OmipayClient(OmipayConfig omipayConfig,RestTemplate restTemplate){
        this.omipayConfig = omipayConfig;
        this.restTemplate = restTemplate;
    }

    /**
     * 获取汇率
     * @return 返回null定表示无法获取汇率
     */
    public BigDecimal getExchangeRate() {

        StringBuilder urlBuilder = new StringBuilder().
                append("https://www.omipay.com.cn/omipay/api/v2/GetExchangeRate?")
                .append("currency=AUD&base_currency=CNY&")
                .append(OmipayUtil.generateSignUrlstr(omipayConfig.getmNumber(), omipayConfig.getSecretKey()));

        ResponseEntity<ExchangeRateResponse> res
                = restTemplate.getForEntity(urlBuilder.toString(), ExchangeRateResponse.class);

        if (res.getStatusCode() == HttpStatus.OK) {
            BigDecimal rate = res.getBody().getRate();
            ExchangeRateResponse rep = res.getBody();
            if (rep.isSuccess()) {
                return rate;
            } else {
                logger.error("can not fetch exchange rate", rep.getError_msg());
            }
        }
        return null;
    }

    /**
     * 生成JSAPI订单
     * @param request
     * @return
     * @throws OmipayException
     */
    public JsapiOrderResponse createJsapiOrder(JsapiOrderRequest request) throws OmipayException{

        Map<String, Object> map = om.convertValue(request, Map.class);
        String signUrlstr = OmipayUtil.generateSignUrlstr(omipayConfig.getmNumber(),omipayConfig.getSecretKey());

        StringBuilder urlBuilder = new StringBuilder().
                append("https://www.omipay.com.cn/omipay/api/v2/MakeJSAPIOrder?")
                .append(map2UriVariables(map))
                .append(signUrlstr);


        ResponseEntity<JsapiOrderResponse> res
                = restTemplate.getForEntity(urlBuilder.toString(), JsapiOrderResponse.class);

        if(res.getStatusCode() == HttpStatus.OK) {
            JsapiOrderResponse rep = res.getBody();
            if(rep.isSuccess()){
                //将验证字段拼到url后，详见：https://www.omipay.com.cn/Help/API_new.html
                rep.setPay_url(rep.getPay_url() + "&" + signUrlstr);
                return rep;
            }else {
                throw new OmipayException(rep.getError_msg());
            }
        }
        throw new OmipayException("can not generate jsapi order");
    }

    /**
     * 生成小程序支付订单
     * @param request
     * @return
     */
    public MiniprogramOrderResponse createMiniprogramOrder(MiniProgramOrderRequest request){
        Map<String, Object> map = om.convertValue(request, Map.class);
        String signUrlstr = OmipayUtil.generateSignUrlstr(omipayConfig.getmNumber(),omipayConfig.getSecretKey());

        StringBuilder urlBuilder = new StringBuilder().
                append("https://www.omipay.com.cn/omipay/api/v2/MakeAppletOrder?")
                .append(map2UriVariables(map))
                .append(signUrlstr);


        ResponseEntity<MiniprogramOrderResponse> res
                = restTemplate.getForEntity(urlBuilder.toString(), MiniprogramOrderResponse.class);

        if(res.getStatusCode() == HttpStatus.OK) {
            MiniprogramOrderResponse rep = res.getBody();
            if(rep.isSuccess()){
                return rep;
            }else {
                throw new OmipayException(rep.getError_msg());
            }
        }
        throw new OmipayException("can not generate miniprogram order");
    }

    /**
     * 检查支付回调数据是否合法
     * @param response
     * @return
     */
    public Boolean checkPayNotifyResponse(PayNotifyResponse response){
        String signKey = OmipayUtil.generateSignKey(omipayConfig.getmNumber(),response.getTimestamp().toString(),
                response.getNonce_str(),omipayConfig.getSecretKey());
        return signKey.equals(response.getSign());
    }

    /**
     * 将map转换成uri参数字符串,使用“"&"”分隔
     * @param map
     * @return uri字符串
     */
    private String map2UriVariables(Map<String,Object> map){
        Iterator<String> iterator =  map.keySet().iterator();
        String key;
        StringBuilder uriVariablesBuilder = new StringBuilder();
        while(iterator.hasNext()){
            key = iterator.next();
            uriVariablesBuilder.append(key)
            .append("=").append(map.get(key))
            .append("&");
        }
        return uriVariablesBuilder.toString();
    }

    public RestTemplate defaultRestTemplate(){
        RestTemplate restTemplate = new RestTemplate(requestFactory());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate;
    }

    public HttpComponentsClientHttpRequestFactory requestFactory() {
        try {
            TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
            SSLContext sslContext = null;
            sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(csf)
                    .build();

            HttpComponentsClientHttpRequestFactory requestFactory =
                    new HttpComponentsClientHttpRequestFactory();

            requestFactory.setHttpClient(httpClient);

            return requestFactory;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (KeyManagementException e) {
            e.printStackTrace();
            return null;
        } catch (KeyStoreException e) {
            e.printStackTrace();
            return null;
        }
    }

}
