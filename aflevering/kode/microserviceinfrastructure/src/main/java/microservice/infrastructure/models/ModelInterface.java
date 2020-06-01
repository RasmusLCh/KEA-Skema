package microservice.infrastructure.models;

/**
 * The ModelInterface is used for making sure that out Objects has an getId method.
 *
 * The id of a ModelInterface should be unique, so it can uniquely identify the ModenInterface (the id is taken from the database id)
 * */

public interface ModelInterface {

    /**
     * Returns the id of the ModelInterfae
     * */
    int getId();
    /**
     * A class that implements ModelInterface should override equals,
     * so equals checks for type and then compared this.id with obj.id if the two are the same,
     * it should returns true.
     * */
    boolean equals(Object obj);
}
