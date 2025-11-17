package com.zrh.framework.core.validators;

import cn.hutool.core.util.IdcardUtil;
import com.zrh.framework.core.annotations.IdCard;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @PACKAGE_NAME: com.zrh.framework.core.utils
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/11/4
 * @TIME: 21:47
 * @Description:
 */
public class IdCardValidator implements ConstraintValidator<IdCard, String> {
    @Override
    public boolean isValid(String idCard, ConstraintValidatorContext context) {
        if (idCard == null || idCard.isEmpty()) {
            return true; // 空值交给 @NotBlank/@NotNull 处理
        }
        return IdcardUtil.isValidCard(idCard);
    }
}
