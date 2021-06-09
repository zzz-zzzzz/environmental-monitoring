package com.tsu.utils;

/**
 * @author ZZZ
 * @create 2020/9/27/17:01
 */
//用于分页
public class PageUtil {
    public static Integer checkPageNum(Integer page) {
        if (page == null || page <= 0) {
            return 1;
        }
        return page;
    }

    public static Integer checkSizeNum(Integer size) {
        if (size == null || size <= 0) {
            return 8;
        }
        return size;
    }
}
