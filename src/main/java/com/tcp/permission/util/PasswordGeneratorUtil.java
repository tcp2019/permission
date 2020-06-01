package com.tcp.permission.util;

import org.apache.commons.lang3.RandomUtils;

import java.util.Random;

/**
 * @ClassName PasswordGeneratorUtil
 * @Description: TODO
 * @Author TCP
 * @Date 2020/5/8 0008
 * @Version V1.0
 **/
public class PasswordGeneratorUtil {
    public static String[] word = {
            "a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "m", "n", "p", "q", "r",
            "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "M", "N", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z"
    };
    public static String[] num = {
            "2", "3", "4", "5", "6", "7", "8", "9"
    };

    public static String randomPassword() {
        //设置密码长度：6-11位
        Random random = new Random(System.currentTimeMillis());
        int length = random.nextInt(5) + 6;
        StringBuffer stringBuffer = new StringBuffer();
        boolean flag = false;
        for (int i = 0; i < length; i++) {
            if (flag) {
                stringBuffer.append(num[random.nextInt(num.length)]);
            } else {
                stringBuffer.append(word[random.nextInt(word.length)]);
            }
            flag = !flag;
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        System.out.println(randomPassword());
        System.out.println(randomPassword());
        System.out.println(randomPassword());
    }
}
