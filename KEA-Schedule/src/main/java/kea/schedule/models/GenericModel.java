package kea.schedule.models;

public class GenericModel implements ModelInterface {
    private int id;

    public GenericModel(int id){
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }
}
