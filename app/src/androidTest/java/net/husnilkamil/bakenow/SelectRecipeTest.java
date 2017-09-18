package net.husnilkamil.bakenow;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.husnilkamil.bakenow.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by husnilk on 9/17/2017.
 */

@RunWith(AndroidJUnit4.class)
public class SelectRecipeTest {

    @Rule
    ActivityTestRule<MainActivity> mMainActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void click_on_recipe_display_detail(){

    }
}
