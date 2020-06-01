package com.tcp.permission.util;

import com.google.common.base.Splitter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName StringUtil
 * @Description: TODO
 * @Author TCP
 * @Date 2020/6/1 0001
 * @Version V1.0
 **/
public class StringUtil {
    public static List<Integer> handle(String ids) {
        List<String> stringList = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(ids);
        return stringList.stream().map(str -> Integer.parseInt(str)).collect(Collectors.toList());
    }
}
