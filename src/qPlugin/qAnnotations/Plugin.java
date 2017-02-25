package qPlugin.qAnnotations;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.*;
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Plugin {

	PluginEvent event();
	int seq() default 99;
	boolean editable() default true;
	boolean active() default true;
	
	
	
}
