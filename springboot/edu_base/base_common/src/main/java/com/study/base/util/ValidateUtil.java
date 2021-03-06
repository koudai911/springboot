package com.study.base.util;

import com.alibaba.fastjson.JSONObject;
import com.study.base.common.ValidateResult;
import com.study.base.exception.CommonException;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static com.google.common.collect.Iterables.getFirst;

/**
 * @Description: 参数校验
 * @Author: luoshangcai
 * @Date 2020-08-15 14:45
 **/
public class ValidateUtil {

    /**
     * 开启快速结束模式 failFast (true)
     */
    private static Validator validator = Validation.byProvider(HibernateValidator.class).configure().failFast(false).buildValidatorFactory().getValidator();

    /**
     * 参数校验并转换为Dto类
     *
     * @param clazz Dto类Class
     * @param data  数据
     * @param <T>   类型
     * @return dto
     */
    @SuppressWarnings("unchecked")
    public static <T> T validateDto(Class clazz, String data) {
        Object dataJson = JSONObject.parseObject(data, clazz);
        validate(dataJson);
        return (T) dataJson;
    }

    /**
     * 参数校验器
     *
     * @param o   参数
     * @param <T> 类型
     */
    public static <T> void validate(T o) {
        //获取验证器
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        //执行校验
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(o);
        //获取第一个校验执行的信息
        ConstraintViolation<T> constraintViolation = getFirst(constraintViolations, null);
        if (constraintViolation != null) {
            throw new CommonException(constraintViolation.getMessage());
        }
    }

    /**
     * 校验对象
     *
     * @param t      bean
     * @param groups 校验组
     * @return ValidResult
     */
    public static <T> ValidateResult validateBean(T t, Class<?>... groups) {
        ValidateResult result = new ValidateResult();
        Set<ConstraintViolation<T>> violationSet = validator.validate(t, groups);
        boolean hasError = violationSet != null && violationSet.size() > 0;
        result.setHasErrors(hasError);
        if (hasError) {
            for (ConstraintViolation<T> violation : violationSet) {
                result.addError(violation.getMessage());
            }
        }
        return result;
    }

    /**
     * 校验bean的某一个属性
     *
     * @param obj          bean
     * @param propertyName 属性名称
     * @return ValidResult
     */
    public static <T> ValidateResult validateProperty(T obj, String propertyName) {
        ValidateResult result = new ValidateResult();
        Set<ConstraintViolation<T>> violationSet = validator.validateProperty(obj, propertyName);
        boolean hasError = violationSet != null && violationSet.size() > 0;
        result.setHasErrors(hasError);
        if (hasError) {
            for (ConstraintViolation<T> violation : violationSet) {
                result.addError(violation.getMessage());
            }
        }
        return result;
    }
}
