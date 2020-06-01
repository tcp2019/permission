package com.tcp.permission.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName LevelUtil
 * @Description: 计算level的层级
 * @Author TCP
 * @Date 2020/5/6 0006
 * @Version V1.0
 **/
public class LevelUtil {
    public final static String SEPARATOR = ".";

    public final static String ROOT = "0";

    /**
     * 0  root等级
     * 0.1 第一层级
     * 0.1.1 第二层级
     * 0.1.2 第三层级
     *
     * @param patentLevel
     * @param parentId
     * @return
     */
    public static String calculateLevel(String patentLevel, Integer parentId) {
        //如果当前level是空，则说明是ROOT 等级，为0
        if (StringUtils.isBlank(patentLevel)) {
            return ROOT;
        } else {
            return StringUtils.join(patentLevel, SEPARATOR, parentId);
        }
    }

    public static void main(String[] args) {
        System.out.println(calculateLevel("0.0", 0));
    }
}
