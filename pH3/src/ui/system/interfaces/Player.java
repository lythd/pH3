/* CODE USAGE : If you distribute any part of this code as source code or object code, the whole project must be open source, and credit this project. For more info read 'COPYING'
 * and 'COPYING.LESSER'. This is legally binding, and includes use as a library.
 * Author: @Lythd
 */

package ui.system.interfaces;
//The ui (user interface) package is just for allowing the game to be played, and for the user to interact, designed to be as separate as pH as possible, so you could
//easily swap it for another engine, or just play human vs human very easily.
//The system package just refers to all the classes based on the system protocol, general information about the system protocol is in 'Game'.
//The interfaces package just refers to all of the classes that are interfaces for implementing the system protocol.

//This class is designed to be able to make a move based on the game, it is not required to store any information about the game but can if it wishes.
public interface Player {
	
	//private static final String SYSTEM; This should be defined for the compatibility, check the 'Game' interface for more information on the compatibility.

	//Outputs its respective implementation of makeMove(), expected to use the games 'isValidMove()' to ensure that the move is valid before returning it.
	public String makeMove(Game game);
	
	//Checks compatibility
	public boolean isCompatible(String system);
	
}
