package fr.alexisvisco.sparklay.route;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Route {
    String value();
    Method type() default Method.GET;
    String view() default "index.vm";
    String title() default "MAED, site de vente de vêtement et d'accéssoires d'occasion !";
}
