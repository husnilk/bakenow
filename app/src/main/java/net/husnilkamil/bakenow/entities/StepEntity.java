package net.husnilkamil.bakenow.entities;

import com.orm.SugarRecord;

/**
 * Created by husnilk on 9/15/2017.
 */

public class StepEntity extends SugarRecord<StepEntity> {

    Integer id;
    Long recipeId;
    String shortDescription;
    String description;
    String videoURL;
    String thumbnailURL;

    public StepEntity() {}

    public StepEntity(Integer id, Long recipeId, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.recipeId = recipeId;
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }
}
