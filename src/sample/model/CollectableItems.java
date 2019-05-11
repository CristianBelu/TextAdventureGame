package sample.model;

public interface CollectableItems {
    default boolean isCollectable(){
        return true;
    }
}
