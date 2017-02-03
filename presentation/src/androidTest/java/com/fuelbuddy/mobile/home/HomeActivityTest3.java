package com.fuelbuddy.mobile.home;


import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatButton;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;

import com.fuelbuddy.mobile.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HomeActivityTest3 {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void homeActivityTest3() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.login_google_button), withText("Sign in with Google"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.fuelType92Btn), withText("92"), isDisplayed()));
        //appCompatButton2.check(ViewAssertions.matches(withViewColor(R.color.app_green)));
      /*  appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.fuelPriceView), withText("Kampmannsgade 2 1604 København V")));

       // appCompatButton3.check(ViewAssertions.matches(withViewColor(R.color.app_green)));

        appCompatButton3.perform(scrollTo(),click());
        appCompatButton3.check(ViewAssertions.matches(withViewColor(R.color.app_green)));*/

/*        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction button = onView(
                allOf(withId(R.id.fuelPriceView),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fuelPriceHolderView),
                                        0),
                                0),
                        isDisplayed()));
        button.check(matches(isDisplayed()));*/

    }


/*
    private static Matcher<View> withViewColor(final int color) {
        return new BoundedMatcher<View, AppCompatButton>(AppCompatButton.class) {
            @Override
            protected boolean matchesSafely(AppCompatButton item) {
                Drawable drawable = (Drawable) item.getBackground();
                return drawable.getCo == color;
            }

            @Override
            public void describeTo(Description description) {

            }


        };
    }
*/


    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
