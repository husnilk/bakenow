package net.husnilkamil.bakenow.entities;

import com.orm.SugarRecord;

/**
 * Created by husnilk on 9/15/2017.
 */

public class Step extends SugarRecord<Step> {

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
}
