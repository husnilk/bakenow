package net.husnilkamil.bakenow.retrofit;

import net.husnilkamil.bakenow.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by husnilk on 9/12/2017.
 */

public interface RecipeInterface {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();
}
