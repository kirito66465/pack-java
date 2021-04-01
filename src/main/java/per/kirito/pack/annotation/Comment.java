package per.kirito.pack.annotation;

import java.lang.annotation.*;

/**
 * @author kirito
 * @date 2021/3/30
 * @time 16:13
 * 自定义注释注解
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Comment {

	/**
	 * 属性注释
	 */
	String msg() default "";

	/**
	 * 方法参数详细注释
	 */
	String[] detail() default {""};

}
