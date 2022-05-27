/* CODE USAGE : If you distribute any part of this code as source code or object code, the whole project must be open source, and credit this project. For more info read 'COPYING'
 * and 'COPYING.LESSER'. This is legally binding, and includes use as a library.
 * Author: @Lythd
 */

package ui.system.examples;
//The ui (user interface) package is just for allowing the game to be played, and for the user to interact, designed to be as separate as pH as possible, so you could
//easily swap it for another engine, or just play human vs human very easily.
//The system package just refers to all the classes based on the system protocol, general information about the system protocol is in 'Game'.
//The examples package just refers to all of the classes that implement the system protocol, rather than being the interfaces for it.

import ui.system.interfaces.Game;
import ui.system.interfaces.PlayingSystem;

//This class plays a 'Game', although its set to 'ChessGame' if you made your own (for instance timed chess, fairy chess, checkers etc) then it would be capable of playing that.
//This is the simplest possible implementation, a more advanced one might run the game on a separate thread so that the main thread could continue, and allow access of the game
//during running.
public class PlayGame implements PlayingSystem {

	private static final String SYSTEM = "ch000";
	
	@Override
	public void play() {
		Game game = new ChessGame(); // 'ChessGame()' is the default game to be played, although it uses 'ChessGame()' the class is capable of playing any class extending 'Game'
		game.setGraphics(new ConsoleChessGraphics());
		play(game);
	}
	
	@Override
	public void play(Game game) {
		boolean p1ToMove = game.isP1Turn(); // true means p1 starts
		
		while(game.shouldContinue(p1ToMove)) { // if game should continue
			game.move(p1ToMove); // makes a move for the current player
			p1ToMove = !p1ToMove; // goes to next player
		}
	}
	
	//Test starting point.
	public static void main(String[] args) {
		new PlayGame().play(); // launch itself
	}

	@Override
	public boolean isCompatible(String system) {
		if(SYSTEM.equals("") || system.equals("")) return true;
		if(!SYSTEM.substring(0, SYSTEM.length()-2).equals(system.subSequence(0, system.length()-2))) return false;
		return true;
	}

}
