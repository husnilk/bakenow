package net.husnilkamil.bakenow;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.RemoteViews;

import net.husnilkamil.bakenow.entities.Ingredient;
import net.husnilkamil.bakenow.entities.Recipe;
import net.husnilkamil.bakenow.ui.MainActivity;
import net.husnilkamil.bakenow.ui.StepActivity;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetPovider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_povider);

        //get Recipe
        SharedPreferences preferences = context.getSharedPreferences(
                context.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        int recipeId = preferences.getInt(Recipe.KEY_RECIPE_ID, -1);
        if(recipeId != -1){
            List<Recipe> recipes = Recipe.find(Recipe.class, "recipe_id=?", String.valueOf(recipeId));
            updateWidget(views, recipes);
        }else{
            List<Recipe> recipes = Recipe.listAll(Recipe.class);
            updateWidget(views, recipes);
        }

        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(appWidgetId, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static void updateWidget(RemoteViews views,  List<Recipe> recipes) {
        String widgetIngredients = "";
        if(recipes != null) {
            Recipe recipe = recipes.get(0);
            List<Ingredient> ingredients = Ingredient.find(Ingredient.class, "recipe_id=?", String.valueOf(recipe.getRecipeId()));
            for (Ingredient ingredient : ingredients) {
                widgetIngredients += ingredient.getIngredient() + "   (" + ingredient.getQuantity() + " " + ingredient.getMeasure() + ")\n";
            }
            views.setTextViewText(R.id.widget_text, recipe.getName());
            views.setTextViewText(R.id.widget_ingredient, widgetIngredients);
        }else{
            views.setViewVisibility(R.id.widget_text, View.GONE);
            views.setTextViewText(R.id.widget_ingredient, "No Recipe Selected");
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

