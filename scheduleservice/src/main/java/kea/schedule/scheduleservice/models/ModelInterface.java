package kea.schedule.scheduleservice.models;

import net.minidev.json.JSONObject;

/**
 * The ModelInterface is used for making sure that out Objects has an getId method.
 * */

public interface ModelInterface {

    int getId();
    //Default is that resursive is false
    JSONObject toJSON(JSONObject obj);
    //Setting recursive = true, will include json for objects this object contains.
    JSONObject toJSON(JSONObject obj, boolean recursive);
}
