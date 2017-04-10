package lz.renatkaitmazov.tictactoe.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * A custom scope for fragments.
 * Objects created with this annotation lives
 * as long as a fragment lives.
 *
 * @author Renat Kaitmazov
 */

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment {
}
