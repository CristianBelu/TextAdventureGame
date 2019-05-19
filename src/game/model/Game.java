package game.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<GameBoard> gameBoards;
    private int currentBoadIndex;

    /**
     * Create a game object
     *
     */
    public Game() {
        // initialize the list that will contain all the Game Boards of the game
        gameBoards = new ArrayList<>();

        // ToDo load game boards

        // just for test - ToDo: remove in production
        GameBoard gameBoardForest = new GameBoard("Forest",10);
        GameBoard gameBoardDungeon = new GameBoard("Dungeon",9);
        gameBoards.add(gameBoardForest);
        gameBoards.add(gameBoardDungeon);

        gameBoardDungeon.addConnectedBoard(gameBoardForest, 0, 4);
        gameBoardForest.addConnectedBoard(gameBoardDungeon, 9, 9);

        currentBoadIndex = 0;
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
        return gameBoards.get(currentBoadIndex).placeOnBoard(obj,horizontal,vertical);
    }

    /**
     * Removes an object from the board
     *
     * @param vertical   position
     * @param horizontal position
     * @return the object to remove; null if the cell is empty
     */
    public Object removeFromBoard(int horizontal, int vertical) {
        return gameBoards.get(currentBoadIndex).removeFromBoard(horizontal,vertical);
    }


    /**
     * Moves the object from the gameBoard to a new position
     *
     * @param player     to move
     * @param horizontal position
     * @param vertical   position
     *                   return true if you can move
     */
    public boolean movePlayer(Player player, int horizontal, int vertical) {
        // ToDo: Allow player and collectable to be on the same box

        if (vertical >= gameBoards.get(currentBoadIndex).getBoardSize() ||
                horizontal >= gameBoards.get(currentBoadIndex).getBoardSize())
        {
            throw new IllegalArgumentException("Should be smaller than " + gameBoards.get(currentBoadIndex).getBoardSize());
        }



        boolean playerCanMove; // =false


        if(gameBoards.get(currentBoadIndex).getGameBoardObject(horizontal, vertical) == null) {

            playerCanMove = true;
        }

        else if (gameBoards.get(currentBoadIndex).getGameBoardObject(horizontal, vertical) instanceof CollectibleItem) {
            player.collect( (CollectibleItem) gameBoards.get(currentBoadIndex).getGameBoardObject(horizontal, vertical) );
            gameBoards.get(currentBoadIndex).removeFromBoard(horizontal,vertical); // remove collectable from board
            playerCanMove = true;
        }
        else {
            playerCanMove = false;
        }

        if (playerCanMove){
            gameBoards.get(currentBoadIndex).removeFromBoard(player.getHorizontal(),player.getVertical());// remove player from his old position

            GameBoard connectedBoard =  gameBoards.get(currentBoadIndex).isAPortalToOtherGameBoard(horizontal,vertical);
            if (null != connectedBoard){
                for (int i = 0; i < gameBoards.size() ; i++) {
                    if (gameBoards.get(i).equals(connectedBoard)){
                        int newHorizontal = gameBoards.get(currentBoadIndex).getPortalHorizontal(connectedBoard);
                        int newVertical = gameBoards.get(currentBoadIndex).getPortalVertical(connectedBoard);
                        currentBoadIndex = i;

                        gameBoards.get(currentBoadIndex).placeOnBoard(player,newHorizontal,newVertical);
                        player.setPosition(newHorizontal, newVertical);
                        break;
                    }
                }
            }
            else {
                gameBoards.get(currentBoadIndex).placeOnBoard(player,horizontal,vertical);
                player.setPosition(horizontal, vertical);
            }
        }

        return playerCanMove;

    }


    public String displayBoard() {
       return gameBoards.get(currentBoadIndex).toString();
    }
}
