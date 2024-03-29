package com.xz.utils.code;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    /**
     * 返回指定长度的随机字符串
     * @param length
     * @return
     */
    public static String getRandomString(int length){
        String st = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234657980";
        Random random = new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(36);
            sb.append(st.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 返回指定长度的随机字符串，可指定大小写
     * @param length
     * @param isLower
     * @return
     */
    public static String getRandomString(int length,boolean isLower){
        String st = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234657980abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(st.length());
            sb.append(st.charAt(number));
        }
        if (isLower){
            return sb.toString().toLowerCase();
        }else{
            return sb.toString();
        }
    }

    /**
     * 拼接URL
     * @param baseUrl
     * @param paraMap
     * @return
     */
    public static String SplicUrl(String baseUrl, TreeMap<String,String> paraMap){

        baseUrl = baseUrl+"?";
        //拼接Url
        for (String key :paraMap.keySet()){
            baseUrl  = baseUrl+key+"="+paraMap.get(key)+"&";
        }
        baseUrl = baseUrl.substring(0,baseUrl.length()-1);
        return baseUrl;
    }

    /**
     * 切割指定字符
     * @param text
     * @param c 要切割的字符
     * @return
     */
    public static String SplicString(String text,char c){
        String str = "";
        for (int i = 0;i<text.length();i++){
            if (text.charAt(i)!=c){
                str+=text.charAt(i);
            }
        }
        return str;
    }
    /*
     * 按指定长度，省略字符串部分字符
     * @para String 字符串
     * @para length 保留字符串长度
     * @return 省略后的字符串
     */
    public static String omitString(String string, int length) {
        StringBuffer sb = new StringBuffer();
        if (byteLength(string) > length) {
            int count = 0;
            for (int i = 0; i < string.length(); i++) {
                char temp = string.charAt(i);
                if (Integer.toHexString(temp).length() == 4) {
                    count += 2;
                } else {
                    count++;
                }
                if (count < length - 3) {
                    sb.append(temp);
                }
                if (count == length - 3) {
                    sb.append(temp);
                    break;
                }
                if (count > length - 3) {
                    sb.append(" ");
                    break;
                }
            }
            sb.append("...");
        } else {
            sb.append(string);
        }
        return sb.toString();
    }

    /*
     * 计算字符串的字节长度(字母数字计1，汉字及标点计2)
     *
     */
    public static int byteLength(String string) {
        int count = 0;
        for (int i = 0; i < string.length(); i++) {
            if (Integer.toHexString(string.charAt(i)).length() == 4) {
                count += 2;
            } else {
                count++;
            }
        }
        return count;
    }

    public static boolean isphone(String value) {

        String regExp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";

        Pattern p = Pattern.compile(regExp);

        Matcher m = p.matcher(value);

        return m.find();//boolean
    }

    /**
     * 一位补0
     *
     * @param c
     * @return
     */
    public static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public static String formdate(Date c) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String t = format.format(c);
        return t;
    }

    public static String toUtil(String c) {
        String Key = c;
        String newKey;
        if (Key.indexOf("&") != -1) {
            newKey = Key.replaceAll("&", "#38;");
        } else {
            newKey = Key;
        }
        System.out.println(newKey);
        return newKey;
    }

    /**
     * 十六进制转化二进制
     *
     * @param hexString
     * @return
     */
    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0)
            return null;
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(hexString
                    .substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    /**
     * 字符序列转换为16进制字符串
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);

            stringBuilder.append(buffer);

        }
        return stringBuilder.toString();
    }

    // StringBuffer
    public static String reverse(String str) {
        return new StringBuilder(str).reverse().toString();
    }
}
