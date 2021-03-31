package per.kirito.pack.annotation;

import java.lang.annotation.*;

/**
 * @author kirito
 * @date 2021/3/30
 * @time 16:13
 * 自定义注释注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface Comment {

	String msg() default "";

}
