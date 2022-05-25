/* CODE USAGE : If you distribute any part of this code as source code or object code, the whole project must be open source, and credit this project. For more info read 'COPYING'
 * and 'COPYING.LESSER'. This is legally binding, and includes use as a library.
 * Author: @Lythd
 */

package ui;
//The ui (user interface) package is just for allowing the game to be played, and for the user to interact, designed to be as separate as pH as possible, so you could
//easily swap it for another engine, or just play human vs human very easily.

//This class is an interface so that any playing system (ie PlayGame) can work with any game, not just that specific game.
public interface Game {
	
	//Given the whole system aims for both ease of use, and flexibility, there should be a minimum of these two constructors:
	// 1) This one is just a blank 'game()', this is for ease of use.
	// 2) This one should take in all the parameters set in the initialization, allowing for maximum customization, to take in custom players, and to continue where you left off.
	
	//Detects whether the game should continue, if it shouldn't then output the appropriate ending message, returns boolean of whether it should.
	//In the implementation it is expected that that the player being passed in has not moved yet.
	public boolean shouldContinue(boolean p1ToMove);
	
	//To be used by the player before returning the move they make, so that in 'move()' it is guaranteed to receive a valid move.
	public void isValidMove(String move);
	
	//Requests a move from either p1 or p2 depending on p1ToMove, and then applies that move in the game.
	public void move(boolean p1ToMove);

	//Sets the graphics manager of the game, this does allow it to be switched mid game seamlessly.
	void setGraphics(Graphics graphics);
	
}
