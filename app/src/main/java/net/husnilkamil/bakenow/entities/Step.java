package net.husnilkamil.bakenow.entities;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 * Created by husnilk on 9/15/2017.
 */

public class Step extends SugarRecord<Step> {

    @Ignore
    public static final String KEY_STEP_ID = "key_step_id";

    int recipeId;
    String shortDescription;
    String description;
    String videoURL;
    String thumbnailURL;

    public Step() {
    }

    public Step(int recipeId, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.recipeId = recipeId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

}
