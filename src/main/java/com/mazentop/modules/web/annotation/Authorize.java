package com.mazentop.modules.web.annotation;



import com.mazentop.modules.web.RoleType;

import java.lang.annotation.*;

/**
 * 基础权限控制注解,提供基本的控制配置
 * @author zhaoqt
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Authorize {

    RoleType value() default RoleType.COMMON;
}
