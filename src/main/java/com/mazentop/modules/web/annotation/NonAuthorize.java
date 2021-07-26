package com.mazentop.modules.web.annotation;

import java.lang.annotation.*;

/**
 * 不需要授权控制器方法
 *
 * @author zhaoqt
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface NonAuthorize {
}
