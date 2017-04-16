package lz.renatkaitmazov.tictactoe.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;
import javax.inject.Scope;

/**
 * A custom scope for fragments.
 * Objects created with this annotation lives
 * as long as a fragment lives.
 *
 * @author Renat Kaitmazov
 */

@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment {
}
