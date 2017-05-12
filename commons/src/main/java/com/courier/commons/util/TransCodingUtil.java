package com.courier.commons.util;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by admin on 2015/12/2.
 */
public class TransCodingUtil {

    public static String urlEncoder(String input) throws UnsupportedEncodingException {
        return URLEncoder.encode(input, "UTF-8");
    }

    public static String encodeBase64(String input) throws UnsupportedEncodingException {
        return new String(Base64.encodeBase64(input.getBytes("UTF-8")));
    }

    public static String decodeBase64(String input) throws UnsupportedEncodingException {
        return new String(Base64.decodeBase64(input.getBytes("UTF-8")));
    }

    public static String base64AndUrlEncoder(String input) throws UnsupportedEncodingException {
        return urlEncoder(encodeBase64(input));
    }



}
