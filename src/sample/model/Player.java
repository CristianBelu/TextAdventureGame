package sample.model;

import java.util.ArrayList;
import java.util.List;

public class Player implements ILocalizable {
    private int horizontal;
    private int vertical;

    public int getHorizontal() {
        return horizontal;
    }

    public int getVertical() {
        return vertical;
    }

    /**
     * Items tht the player that has a list of collected items
     */

    List<CollectableItems> artifacts;

    /**
     * Creat a player tht can collect items
     */
    public Player(){
        artifacts = new ArrayList<>();
    }

    public void collect(CollectableItems item){
        artifacts.add(item);
    }

    @Override
    public void setPosition(int horizontal, int vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public String getArtifacts(){
        return artifacts.toString();
    }
}
