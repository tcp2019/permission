package com.tcp.permission.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tcp.permission.exception.PermissionException;
import org.apache.commons.collections.MapUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
 * @ClassName BeanValidator
 * @Description: BeanValidator 校验
 * @Author TCP
 * @Date 2020/4/29 0029
 * @Version V1.0
 **/
public class BeanValidator {
    public static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    /**
     * 传入单个类时是否满足检验
     *
     * @param t
     * @param groups
     * @param <T>
     * @return
     */
    public static <T> Map<String, String> validate(T t, Class... groups) {
        Validator validator = validatorFactory.getValidator();
        //获取校验后的结果
        Set<ConstraintViolation<T>> validateResult = validator.validate(t, groups);
        //如果结果为空，即：校验通过
        if (validateResult.isEmpty()) {
            return Collections.emptyMap();
        } else {
            //如果校验结果不为空，遍历set，取出结果放到map中
            Map<String, String> validateErrorMap = Maps.newLinkedHashMap();
            Iterator<ConstraintViolation<T>> iterator = validateResult.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation constraintViolation = iterator.next();
                validateErrorMap.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
            return validateErrorMap;
        }
    }

    /**
     * 校验集合类型
     *
     * @param collection
     * @return
     */
    public static Map<String, String> validateCollection(Collection<?> collection) {
        Map<String, String> validateErrorMap;
        //判断集合是否为空
        Preconditions.checkNotNull(collection);
        //遍历集合
        Iterator<?> iterator = collection.iterator();
        do {
            if (!iterator.hasNext()) {
                return Collections.emptyMap();
            }
            Object object = iterator.next();
            validateErrorMap = validate(object, new Class[0]);
        } while (validateErrorMap.isEmpty());
        return validateErrorMap;
    }

    /**
     * 整合统一 validateCollection和validate方法
     *
     * @param first
     * @param objects
     * @return
     */
    public static Map<String, String> validateObject(Object first, Object... objects) {
        if (objects != null && objects.length > 0) {
            return validateCollection(Lists.asList(first, objects));
        } else {
            return validate(first, new Class[0]);
        }
    }

    public static void check(Object object) {
        Map<String, String> map = validateObject(object);
        if (MapUtils.isNotEmpty(map)) {
            throw new PermissionException(map.toString());
        }
    }
}
