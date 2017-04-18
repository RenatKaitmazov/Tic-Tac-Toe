package lz.renatkaitmazov.tictactoe;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import lz.renatkaitmazov.tictactoe.main.MainActivity;

/**
 * @author Renat Kaitmazov
 */

@RunWith(AndroidJUnit4.class)
public final class MainFragmentTest {

    @Rule
    public final ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public final void setup() {
        activityTestRule.getActivity();
    }

    @Test
    public final void testFirstPlayerWon() {
        onView(withId(R.id.button1))
                .perform(click())
                .check(matches(withText(R.string.mark_player_1)));

        onView(withId(R.id.button2))
                .perform(click())
                .check(matches(withText(R.string.mark_player_2)));

        onView(withId(R.id.button4))
                .perform(click())
                .check(matches(withText(R.string.mark_player_1)));

        onView(withId(R.id.button3))
                .perform(click())
                .check(matches(withText(R.string.mark_player_2)));

        onView(withId(R.id.button7))
                .perform(click())
                .check(matches(withText(R.string.mark_player_1)));
    }

    @Test
    public final void testGameIsOver() {
        onView(withId(R.id.button1))
                .perform(click())
                .check(matches(withText(R.string.mark_player_1)));

        onView(withId(R.id.button2))
                .perform(click())
                .check(matches(withText(R.string.mark_player_2)));

        onView(withId(R.id.button4))
                .perform(click())
                .check(matches(withText(R.string.mark_player_1)));

        onView(withId(R.id.button3))
                .perform(click())
                .check(matches(withText(R.string.mark_player_2)));

        onView(withId(R.id.button7))
                .perform(click())
                .check(matches(withText(R.string.mark_player_1)));

        onView(withId(R.id.button1))
                .check(matches(withText("")));
    }
}
