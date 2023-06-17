package com.szuse.common.base.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.CaseFormat;

import java.util.Map;

/**
 * @author LiuYe
 * @version 1.0
 * @date 23/5/2023 下午8:43
 */
@SuppressWarnings("rawtypes, unchecked")
public class WrapperUtil {

    public static String camelToUnderscore(String string) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,string);
    }

    public static QueryWrapper allEquals(Map<String,Object> paramMap, QueryWrapper wrapper) {
        paramMap.forEach((field,val) -> wrapper.eq(camelToUnderscore(field),val));
        return wrapper;
    }
}
