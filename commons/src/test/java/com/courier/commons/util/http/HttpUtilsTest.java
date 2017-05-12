package com.courier.commons.util.http;

import com.courier.commons.util.Uuid;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HttpUtilsTest {

    public static final Logger logger = LoggerFactory.getLogger(HttpUtilsTest.class);

    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void testDoHttpMethod() throws Exception {
        HttpUtils.HttpMethodType type = HttpUtils.HttpMethodType.POST;
        String url = "http://127.0.0.1:9998/sxgInterface/test";
        Map<String, String> headerParams = new HashMap<String, String>();
        headerParams.put("Accept-Charset", "UTF-8");
        headerParams.put("x-up-calling-line-id", "");
        headerParams.put("handphone", "12323213");

        headerParams.put("user-agent",
                "Mozilla/5.0 (Linux; U; Android 2.3.6; zh-cn; GT-B9120 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
        headerParams.put("storeua",
                "Mozilla/5.0 (Linux; U; Android 2.3.6; zh-cn; GT-B9120 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
        headerParams.put("handua", "104059");
        headerParams.put("preassemble", "Android-v16>Common>V2.0.0>20120328>NA>NA>NA>beiyong>NA>NA");


        headerParams.put("settertype", "3");
        headerParams.put("version", "android_v4.0.3");
        headerParams.put("imei", "imei_11111111");
        headerParams.put("imsi", "imsi_22222222");

        headerParams.put("companylogo", "2164");
        headerParams.put("sessionid", "");
        headerParams.put("appfrom", "openfeint");
        headerParams.put("newclient", "1");

        headerParams.put("phoneAccessMode", "3");
        headerParams.put("usertype", "0");
        Map<String, String> params = new HashMap<String, String>();
        params.put("version", "1111");
        params.put("uid", "2222");
        params.put("token", "3333");
        int connectTimeout = 2000;
        int connectRequestTimeout = 2000;
        int socketTimeout = 2000;

//        String result = new HttpUtils().doHttpMethod(type, url, headerParams, params, connectTimeout, connectRequestTimeout, socketTimeout);
        //logger.info("response content:{}", result);

        //Assert.assertNotNull(result);
    }

    @Test
    public void testHttp(){
        /*String url = "http://192.168.5.141:9998/sxgInterface/ios/m/0412";
        Map<String, String> headerParams = new HashMap<>();
//        headerParams.put("Content-Type", "application/json");
        String json = "{\"s\":\"g655/C1PK3fBiHnNavtIxQ==\",\"k\":\"DC6E07B1E9C1DF90E800BF1E1B3FE05A3833C72AA77888AB7DED6D25E61FAEBD72B8DA19C5BD3806883E1E1213C6F3226DC1F3EBD475187F5B61EBBCD8436613\"}";
        Map<String, String> params = GsonUtil.getBean(json, Map.class);
        HttpUtils httpUtils = new HttpUtils();
        String result = httpUtils.doPost(url, headerParams, params, null, 50000, 50000, 50000);
        httpUtils.
        logger.info("response content:{}", result);

        Assert.assertNotNull(result);*/
        for(int i =0;i<1000;i++){
            System.out.println((new Uuid()).toString());
        }
    }

}