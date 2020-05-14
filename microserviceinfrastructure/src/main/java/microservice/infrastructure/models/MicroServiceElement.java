package microservice.infrastructure.models;


/**
 * Implemented by models, that are linked to MicroService
 * */
public interface MicroServiceElement {
    /**
     * Returns the MicroService that the MicroServiceElement belongs too.
     * */
    public MicroService getMicroservice();
    /**
     * Sets the MicroService that the MicroServiceElement belongs too.
     * */
    public void setMicroservice(MicroService ms);
}
