package game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoard {
    // static field incremented on each creation of a new GameBoard Object
    private static int lastId = 0;

    // id to identify a unique game board
    private int uniqueId;

    // the place where the player could be on the game , multiple game boards can exist
    private String boardName;

    // the game board will have a variable size ( boardSize X boardSize )
    private int boardSize;

    // the actual board where the artefacts/ playes / actors will rest
    private Object [][] gameBoardObjects;

    private int portalHorizontal;
    private int portalVertical;

    // list of connections between boards
    private Map<GameBoard, List<Integer>> connectedBoards;

    public GameBoard(String boardName, int boardSize) {
        this.boardName = boardName;
        this.boardSize = boardSize;
        gameBoardObjects = new Object[boardSize][boardSize];
        connectedBoards = new HashMap<>();
        uniqueId = lastId++;

    }

    /**
     * This method will add an object to the board in the given position
     *
     * @param obj        to place
     * @param vertical   position
     * @param horizontal position
     * @return true if the object can be placed; false if there is already an object there,
     * or throw ArrayIndexOutOfBoundsException if the positions are outside the limits of
     * the board
     */
    public boolean placeOnBoard(Object obj, int horizontal, int vertical) {
        if (vertical >= boardSize || horizontal >= boardSize) {
            throw new IllegalArgumentException("Should be smaller than " + boardSize);
        }

        if (gameBoardObjects[horizontal][vertical] != null) {
            return false;
        } else {
            gameBoardObjects[horizontal][vertical] = obj;
        }

        if (obj instanceof Player) {
            ((Player) obj).setPosition(horizontal, vertical);
        }
        return true;
    }
    /**
     * Removes an object from the board
     *
     * @param vertical   position
     * @param horizontal position
     * @return the object to remove; null if the cell is empty
     */
    public Object removeFromBoard(int horizontal, int vertical) {
        if (vertical >= boardSize|| horizontal >= boardSize) {
            throw new IllegalArgumentException("Should be smaller than " + boardSize);
        }

        Object toRemove = gameBoardObjects[horizontal][vertical];
        gameBoardObjects[horizontal][vertical] = null;

        return toRemove;
    }
    /*
       Door      Key                        Player
                Tree

            a cell is maximum 10 spaces size   . We put an artifact in the middle of the cell
    */
    private final int CELL_DISPLAY_SIZE = 12;

    private String generateSpaces(int number) {
        String spaces = "";
        for (int i = 0; i < number; i++) {
            spaces += " ";
        }
        return spaces;
    }

    private String centerCell(String input) {
        String output = "";
        output += generateSpaces((CELL_DISPLAY_SIZE - input.length()) / 2) +
                input +
                generateSpaces((CELL_DISPLAY_SIZE - input.length()) / 2);
        return output;
    }

    @Override
    public String toString() {
        String toReturn = this.boardName+ "\n";

        /* generate head of table */
        toReturn += generateSpaces(CELL_DISPLAY_SIZE);
        for (int i = 0; i < boardSize; i++) {
            toReturn += centerCell((i + 1) + " ");

        }

        toReturn += "\n";

        // print game board contents
        for (int i = 0; i < boardSize; i++) {
            toReturn += centerCell((i + 1) + "");
            for (int j = 0; j < boardSize; j++) {
                if (gameBoardObjects[i][j] == null) {
                    toReturn += generateSpaces(CELL_DISPLAY_SIZE);
                } else {
                    toReturn += centerCell(gameBoardObjects[i][j].getClass().getSimpleName());
                }
            }
            toReturn += "\n";
        }

        return toReturn;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public Object getGameBoardObject(int horizontal, int vertical) {
        return gameBoardObjects[horizontal][vertical];
    }
    public void addConnectedBoard(GameBoard gameBoard, int horizontal, int vertical){
        List<Integer> coordinates = new ArrayList<>();
        coordinates.add(horizontal);
        coordinates.add(vertical);
        connectedBoards.put(gameBoard,coordinates);
    }

    public GameBoard isAPortalToOtherGameBoard (int horizontal, int vertical){
        for (Map.Entry<GameBoard, List<Integer>> entry : connectedBoards.entrySet()) {

            if (entry.getValue().get(0) == horizontal && entry.getValue().get(1) == vertical){
                return entry.getKey();
            }
        }
        return null;
    }

    public int getPortalHorizontal(GameBoard connectedBoard) {
        return connectedBoard.getConnectedBoards().get(this).get(0);
    }

    public int getPortalVertical(GameBoard connectedBoard) {
        return connectedBoard.getConnectedBoards().get(this).get(1);
    }

    public Map<GameBoard, List<Integer>> getConnectedBoards(){
        return connectedBoards;
    }
}
