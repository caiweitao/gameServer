package game.core.network.server.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface HttpDefine {

	/**
	 * handler描述
	 * @return
	 */
	String desc();

	/**
	 * 处理的handler类
	 * @return
	 */
	Class<?> handler();

	/**
	 * 是否和后台对接完成
	 * @return
	 */
	boolean isComplete() default true;
}
