import chess.ChessController;
import chess.ChessView;
import chess.views.gui.GUIView;
import chess.views.console.ConsoleView;
import engine.GameEngine;


/**
 * This class is the main class of the chess game
 *
 * @author Rayburn Nathan, Harun Ouweis
 */
public class StudentChess {
    public static void main(String[] args) {
        boolean isGui = false;
        if (args.length > 0) isGui = args[0].equals("gui");
        ChessController engine = new GameEngine();
        ChessView ctrl = isGui ? new GUIView(engine) : new ConsoleView(engine);
        engine.start(ctrl);
    }
}