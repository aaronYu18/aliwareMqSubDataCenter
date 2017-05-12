package com.courier.commons.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by admin on 2015/7/14.
 */
public class CommonUtil {
    private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);
    public static Double convertString(String moneyDeliver){
        if (StringUtils.isEmpty(moneyDeliver)) return null;
        try{
            return Double.parseDouble(moneyDeliver);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("convert string to double error message:{}",e.getMessage());
        }
        return null;
    }
    /**
     *
     * @param bytes
     * @return
     */
    public static String convertByteArrayToString(Byte[] bytes){
        if(bytes == null || bytes.length == 0) return "";

        StringBuffer sb = new StringBuffer();

        for (Byte by : bytes)
            sb.append(by + ",");

        if(sb != null) {
            String s = sb.toString();
            return s.substring(0, s.length() - 1);
        }

        return "";
    }

    /**
     * 格式化工号
     */
    public static String formatJobNo(String jobNo){
        if (jobNo == null) return null;
        if (jobNo.trim().equals("")) return null;
        return String.format("%8s", jobNo).replaceAll(" ", "0");
    }
}
