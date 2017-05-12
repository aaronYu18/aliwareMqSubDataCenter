package com.courier.commons.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;


/**
 * Created by bin on 2015/11/26.
 */
public class MD5Utils {
    public static String md5Base64Url(String str){
        //先进行MD5加密
        String res = "";
        byte[] bytesMd5 = DigestUtils.md5(str);
        Base64 base64 = new Base64();
        //base64 加密
        byte[] byteBase64 = base64.encode(bytesMd5);
//        try {
//            //url
//            res = URLEncoder.encode(new String(byteBase64), "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        return new String(byteBase64);
    }
    public static String md5(String jsonBuilder,String parternId) throws Exception{
        MessageDigest messagedigest = MessageDigest.getInstance("MD5");
        messagedigest.update((jsonBuilder.toString() + parternId).getBytes("UTF-8"));
        byte[] abyte0 = messagedigest.digest();
        String data_digest = new String(Base64.encodeBase64(abyte0));
        return data_digest;
    }

    public static String employeeMd5(String str) throws Exception {
        if (StringUtils.isEmpty(str)) return str;
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] temp = str.getBytes("UTF-8");
        byte[] buff = md.digest(temp);
        return new sun.misc.BASE64Encoder().encode(buff);
    }
}
