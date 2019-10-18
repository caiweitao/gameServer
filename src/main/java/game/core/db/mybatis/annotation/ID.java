package game.core.db.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 标识主键字段(一个类只能有一个字段标记)
 * autoIncrement:主键是否自增
 * 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ID {
	
	boolean autoIncrement() default true;
}
