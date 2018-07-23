package com.khy.jwt.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hxy
 */
public final class BeanUtils {
    private static final Map<String, BeanCopier> copiers = new ConcurrentHashMap<>();

    private BeanUtils() {
    }

    public static <T, F> List<F> convertList(List<T> sourceList, Class<F> targetClass) {
        return convertList(sourceList, targetClass, null);
    }

    public static <T, F> List<F> convertList(List<T> sourceList, Class<F> targetClass, Converter converter) {
        List<F> targetList = new ArrayList();
        if (CollectionUtils.isNotEmpty(sourceList)) {
            for (T source : sourceList) {
                try {
                    F target = targetClass.newInstance();
                    targetList.add(convert(source, target, converter));
                } catch (Exception e) {
                    throw new RuntimeException("create class[" + targetClass.getName() + "] instance error", e);
                }
            }
        }
        return targetList;
    }

    public static <T, F> F convert(T source, Class<F> targetClass) {
        try {
            F target = targetClass.newInstance();
            return convert(source, target);
        } catch (Exception e) {
            throw new RuntimeException("create class[" + targetClass.getName() + "] instance error", e);
        }
    }

    public static <T, F> F convert(T source, F target) {
        return convert(source, target, null);
    }

    public static <T, F> F convert(T source, F target, Converter converter) {
        if (source == null || target == null) {
            return null;
        }
        copy(source, target, converter);
        return target;
    }

    private static <T, F> void copy(T source, F target, Converter converter) {
        BeanCopier copier = getCopier(source.getClass(), target.getClass(), converter);
        copier.copy(source, target, converter);
    }

    private static <T, F> BeanCopier getCopier(Class<T> source, Class<F> target, Converter converter) {
        String key = source.getName() + "@" + target.getName();
        if (!copiers.containsKey(key)) {
            BeanCopier copier = BeanCopier.create(source, target, converter != null);
            copiers.putIfAbsent(key, copier);
        }
        return copiers.get(key);
    }
}
