package net.husnilkamil.bakenow;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.media.MediaMetadataCompat;

import net.husnilkamil.bakenow.ui.StepActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by husnilk on 9/30/2017.
 */
@RunWith(AndroidJUnit4.class)
public class StepClickedTest {

    @Rule
    public ActivityTestRule<StepActivity> mRecipeActivity = new ActivityTestRule<>(StepActivity.class, true, false);

    @Test
    public void clickOnStep_OpenStepDetailFragment(){

        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, StepActivity.class);
        intent.putExtra("net.husnilkamil.bakenow.RECIPE_ID_KEY", "1");

        mRecipeActivity.launchActivity(intent);

        onView(withId(R.id.rv_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(withId(R.id.next_button)).check(matches(isDisplayed()));
    }

}
