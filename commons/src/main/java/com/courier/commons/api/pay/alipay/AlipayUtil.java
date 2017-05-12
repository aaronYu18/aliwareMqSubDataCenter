package com.courier.commons.api.pay.alipay;

import com.courier.commons.util.DateUtil;
import com.courier.commons.util.NetworkUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by ryan on 16/3/31.
 */
public class AlipayUtil {

    /**
     * IP(12位) + PLATFORM(1位) + CHANNELTYPE(2位) + PAYTYPE(1位) + COSTTYPE(2位) + MAILNO + DATE(17位) + 校验码(3位)
     * @param platformType
     * @param channelType
     * @param payType
     * @param costType
     * @param mailNo
     * @return
     */
    public static String generateTradeNumber(Byte platformType, String channelType, Byte payType,
                                             String costType, String mailNo){
        List<String> lsts = NetworkUtil.getLocalIPS();
        StringBuilder sb = new StringBuilder();
        String ip = lsts.get(lsts.size()-1);
        sb.append(formatIP(ip));
        sb.append(platformType != null ? platformType.byteValue() : AlipayConstant.DEFAULT_PLATFORM_TYPE);
        sb.append(StringUtils.isNotBlank(channelType) ? channelType : AlipayConstant.DEFAULT_CHANNEL_TYPE);
        sb.append(payType != null ? payType.byteValue() : AlipayConstant.DEFAULT_PAY_TYPE);
        sb.append(StringUtils.isNotBlank(costType) ? costType : AlipayConstant.DEFAULT_COST_TYPE);
//        sb.append(mailNo);
        sb.append(DateUtil.toFullStr(new Date()));
        sb.append(RandomStringUtils.randomAlphanumeric(3).toUpperCase());
        return sb.toString();

    }

    private static String formatIP(String ip){
        String[] strs = ip.split("\\.");
        StringBuilder sb = new StringBuilder();
        for(String s : strs){
            completIP(sb, s);
        }
        return sb.toString();
    }

    private static void completIP(StringBuilder sb, String s){
        if(s.length() == 1)
            sb.append("00").append(s);
        else if(s.length() == 2)
            sb.append("0").append(s);
        else if(s.length() == 3)
            sb.append(s);
    }

    public static void main(String[] args) {
        String tradeCode = AlipayUtil.generateTradeNumber((byte)1, "32", (byte)2, "01", "23213123213213213");
        System.out.println(tradeCode + ",len:"+ tradeCode.length());
    }
}
