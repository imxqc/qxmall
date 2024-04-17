package com.cqx.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * @author xqc
 * @version 1.0
 * @date 2024/4/17 19:31
 */
public class ListValueConstraintValidator implements ConstraintValidator<ListValue, Integer> {
    Set<Integer> set = new HashSet<>();

    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] vals = constraintAnnotation.vals();//获取valsxinxi

        //将vals加入set当中
        for (int val : vals) {
            set.add(val);
        }
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return set.contains(integer);//integer为使用校验注解的属性传来的值
    }
}
