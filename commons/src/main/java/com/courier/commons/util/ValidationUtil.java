package com.courier.commons.util;

import com.courier.commons.constant.Global;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by admin on 2015/6/17.
 */
public class ValidationUtil {

    /*public static String filter(String str) throws PatternSyntaxException {
        if (StringUtils.isEmpty(str))return str;
        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】\\\\≧▽≦\"‘；：”“’。，、？ 　\n\t ]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }*/
    public static String filter(String regEx,String str) throws PatternSyntaxException {
        if (StringUtils.isEmpty(str)||StringUtils.isEmpty(regEx))return str;
        // 清除掉所有特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
    /**
     * 验证mCode 是否正确
     *
     * @param mCode
     * @return
     */
    public static boolean checkMCode(String mCode) {
        Pattern pattern = Pattern.compile(Global.MCODE_PATTERN);
        Matcher matcher = pattern.matcher(mCode);
        return matcher.matches();
    }

    /**
     * 验证是否是手机号
     *
     * @param password
     * @return
     */
    public static boolean isPwd(String password) {
        Pattern p = Pattern.compile(Global.REGEX_PASSWORD);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 验证是否是手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNo(String mobiles) {
        Pattern p = Pattern.compile(Global.REGEX_PHONE);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isTimeNo(String time){
        if (StringUtils.isEmpty(time)) return false;
        Pattern p = Pattern.compile(Global.TIME_REQ);
        Matcher m = p.matcher(time);
        return m.matches();
    }

    /**
     * 验证寄件人姓名是否正确
     *
     * @param name
     * @return
     */
    public static boolean isSenderName(String name) {
        Pattern p = Pattern.compile(Global.REGEX_SENDER_NAME);
        Matcher m = p.matcher(name);
        return m.matches();
    }

    /**
     * 验证面单号为数字
     * @param mailNo
     * @return
     */
    public static boolean isNumber(String mailNo) {
        Pattern p = Pattern.compile(Global.NUMBER);
        Matcher m = p.matcher(mailNo);
        return m.matches();
    }

    /**
     * 验证面单号为英文和数字
     * @param mailNo
     * @return
     */
    public static boolean isCharNumber(String mailNo) {
        Pattern p = Pattern.compile(Global.CHARNUMBER);
        Matcher m = p.matcher(mailNo);
        return m.matches();
    }

    public static void main(String[] args) {
        double d = 4.0d;
        byte b = (byte) d;
        System.out.println(checkMCode("0090"));
    }
}
