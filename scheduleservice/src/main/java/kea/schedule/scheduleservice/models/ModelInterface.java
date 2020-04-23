package kea.schedule.scheduleservice.models;

import net.minidev.json.JSONObject;

/**
 * The ModelInterface is used for making sure that out Objects has an getId method.
 * */

public interface ModelInterface {

    int getId();
    boolean equals(Object obj);
}
