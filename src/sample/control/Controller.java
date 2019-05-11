package sample.control;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.model.Door;
import sample.model.Game;
import sample.model.Key;
import sample.model.Player;

public class Controller {


    private Game game;

    public TextField txtFieldCommand;
    public TextArea txtAreaGameOutput;

    public void initialize(){
        System.out.println("test");
        game = new Game(10);
        Key key = new Key();
        Door door = new Door(key);
        Player player = new Player();

        game.placeOnBoard(key,2, 3);
        game.placeOnBoard(door, 1, 5);
        game.placeOnBoard(player,5,5);
        player.setPosition(5,5);


        System.out.println(game.displayBoard());

        game.removeFromBoard(2, 3);
        System.out.println(game.displayBoard());

        game.placeOnBoard(key,2, 3);
        game.movePlayer(player,2, 3);
        System.out.println(game.displayBoard());

        game.movePlayer(player,2, 3);
        System.out.println(game.displayBoard());
        txtAreaGameOutput.appendText(game.displayBoard() + "\n");
        System.out.println(player.getArtifacts());
        txtAreaGameOutput.appendText(player.getArtifacts() + "\n") ;

        game.movePlayer(player,1, 5);
        System.out.println(game.displayBoard());
        txtAreaGameOutput.appendText(game.displayBoard() + "\n");
        System.out.println(player.getArtifacts());
        txtAreaGameOutput.appendText(player.getArtifacts()+ "\n");
    }

}
