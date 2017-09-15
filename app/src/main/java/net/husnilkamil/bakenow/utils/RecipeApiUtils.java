package net.husnilkamil.bakenow.utils;

import net.husnilkamil.bakenow.retrofit.RecipeClient;
import net.husnilkamil.bakenow.retrofit.RecipeInterface;

/**
 * Created by husnilk on 9/12/2017.
 */

public class RecipeApiUtils {
    public static RecipeInterface getRecipes() {
        return RecipeClient.getClient().create(RecipeInterface.class);
    }
}
