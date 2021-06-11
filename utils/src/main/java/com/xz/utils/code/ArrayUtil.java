package com.xz.utils.code;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayUtil {

    /**
     * 指定长度切割数组
     * 示例： [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 ]
     * 指定分割长度:6
     * 结果:
     * [1, 2, 3, 4, 5, 6]
     * [7, 8, 9, 10, 11, 12]
     * [13, 14]
     *
     * @param div  指定切割长度
     * @param data 元数据
     * @return 切割好的数据
     */
    public static List<byte[]> optByte(int div, byte[] data) {

        int length = data.length;
        int mod = length % div;// 余数
        int total = length / div;// 商
        int num = 0;
        int cla = 0;

        List<byte[]> base = new ArrayList<>();
        for (int i = 1; i <= total; i++) {
            base.add(Arrays.copyOfRange(data, num, cla + div));
            cla = cla + div;
            num = cla;
        }
        if (mod > 0) {
            base.add(Arrays.copyOfRange(data, cla, cla + mod));
        }

        return base;
    }

    /**
     * 将一个集合拆分成制定长度的若干个集合
     *
     * @param list 元数据
     * @param len 指定切割长度
     * @return
     */
    public static List<List<?>> splitList(List<?> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }

        List<List<?>> result = new ArrayList<List<?>>();


        int size = list.size();
        int count = (size + len - 1) / len;


        for (int i = 0; i < count; i++) {
            List<?> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }

    /**
     * char[] 数组转为byte[] 数组
     * @param chars
     * @return
     */
    public static byte[] getBytes(char[] chars) {
        Charset cs = StandardCharsets.UTF_8;
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);
        return bb.array();
    }

    /**
     * byte[] 数组转为数组 char[]
     * @param bytes
     * @return
     */
    public static char[] getChars(byte[] bytes) {
        Charset cs = StandardCharsets.UTF_8;
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array();
    }

    /**
     * char 转 byte[] 数组
     * @param c
     * @return
     */
    public static byte[] charToByte(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        return b;
    }

    /**
     * byte[] 数组转 char
     * @param b
     * @return
     */
    public static char byteToChar(byte[] b) {
        char c = (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
        return c;
    }

}
