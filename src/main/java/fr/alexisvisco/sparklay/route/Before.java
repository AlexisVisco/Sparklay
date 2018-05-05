package fr.alexisvisco.sparklay.route;

import spark.Route;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Before {
    Class<? extends Pass>[] value() default {};
    String redirect() default  "/";
}
