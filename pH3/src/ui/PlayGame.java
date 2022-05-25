/* CODE USAGE : If you distribute any part of this code as source code or object code, the whole project must be open source, and credit this project. For more info read 'COPYING'
 * and 'COPYING.LESSER'. This is legally binding, and includes use as a library.
 * Author: @Lythd
 */

package ui;
//the ui (user interface) package is just for allowing the game to be played, and for the user to interact, designed to be as separate as pH as possible, so you could
//easily swap it for another engine, or just play human vs human very easily

//this class plays a 'Game', although its set to 'ChessGame' if you made your own (for instance timed chess, fairy chess, checkers etc) then it would be capable of playing that
//this is the simplest possible implementation, a more advanced one might run the game on a separate thread so that the main thread could continue, and allow access of the game
//during running
public class PlayGame {
	
	//launches itself with a default game if none provided for ease of use
	public void play() {
		play(new ChessGame()); //'ChessGame()' is the default game to be played, although it uses 'ChessGame()' the class is capable of playing any class extending 'Game'
	}
	
	//this is the core player function, and should be able to handle any input game to be a proper player class
	public void play(Game game) {
		boolean p1ToMove = true; //true means p1 starts
		
		while(game.shouldContinue(p1ToMove)) { //if game should continue
			game.move(p1ToMove); //makes a move for the current player
			p1ToMove = !p1ToMove; //goes to next player
		}
	}
	
	//test starting point
	public static void main(String[] args) {
		new PlayGame().play(); //launch itself
	}

}
