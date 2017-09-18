package net.husnilkamil.bakenow.entities;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 * Created by husnilk on 9/15/2017.
 */

public class Step extends SugarRecord<Step> {

    @Ignore
    public static final String KEY_STEP_ID = "key_step_id";

    long recipeId;
    String shortDescription;
    String description;
    String videoURL;
    String thumbnailURL;

    public Step() {}

    public Step(Long recipeId, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.recipeId = recipeId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
