package sk.stuba.fei.uim.vsa.pr2.auth.annotations;

import sk.stuba.fei.uim.vsa.pr2.auth.expresions.AuthorizationExpression;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({TYPE, METHOD})
@Repeatable(Authorizations.class)
public @interface Authorize {
    Class<? extends AuthorizationExpression>[] value() default {};
}
