package com.courier.sdk.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by ryan on 16/6/1.
 */
public class DESUtil {

    private static byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };

    public static String encryptDES(String input, String encryptKey) throws Exception {
        if(input == null || input == "") return input;

        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(input.getBytes());

        return URLEncoder.encode(Base64.encode(encryptedData), "UTF-8");
    }


    public static String decryptDES(String input, String decryptKey) throws Exception {
        if(input == null || input == "") return input;
        input = URLDecoder.decode(input, "UTF-8");

        byte[] byteMi = Base64.decode(input);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte decryptedData[] = cipher.doFinal(byteMi);
        return new String(decryptedData);
    }

    public static void main(String[] args) throws Exception{
        String key = "y2x5g8sb";
       /* String[] test = {"18871970577", "18871970577*", "18211827298", "18211827298***"};
        for(String str : test){
            String cipherText = DESUtil.encryptDES(str, key);
            System.out.println(String.format("key is %s, value is %s", str, cipherText));
        }

        String plainText = "18871970577*";
        String cipherText = DESUtil.encryptDES(plainText, key);
        System.out.println("明文：" + plainText);
        System.out.println("密钥：" + key);
        System.out.println("密文：" + cipherText);
        System.out.println("解密后：" + DESUtil.decryptDES(cipherText, key));*/
    }
}
