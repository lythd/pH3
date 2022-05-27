/* CODE USAGE : If you distribute any part of this code as source code or object code, the whole project must be open source, and credit this project. For more info read 'COPYING'
 * and 'COPYING.LESSER'. This is legally binding, and includes use as a library.
 * Author: @Lythd
 */

package ui.system.interfaces;
//The ui (user interface) package is just for allowing the game to be played, and for the user to interact, designed to be as separate as pH as possible, so you could
//easily swap it for another engine, or just play human vs human very easily.
//The system package just refers to all the classes based on the system protocol, general information about the system protocol is in 'Game'.
//The interfaces package just refers to all of the classes that are interfaces for implementing the system protocol.

//This class is an interface for displaying the game to the user, graphics should be able to run multiple games not just one so should be flexible and not tied to a specific game.
public interface Graphics {
	
	//private static final String SYSTEM; This should be defined for the compatibility, check the 'Game' interface for more information on the compatibility.
	
	//The constructor may need to launch a separate thread to handle doing the graphics while the code is running.
	
	//Depending on the graphics system this could either:
	// 1) Display the state. Useful for clis.
	// 2) Update the state stored in the class, so that the thread that handles displaying can display the state. Useful for guis.
	public void updateState(String state);
	
	//Displays a gameover result, state should be 1 if p1 wins, 0.5 if draw, and 0 if p2 wins. Any other state value should also be handled as a draw though should still not happen.
	public void gameOver(float state);
	
	//Gets a string from the user, the current player is passed in incase a prompt is needed. The input does not need to be the string itself, the string could be constructed
	//based on mouse clicks for instance.
	public String input(boolean isP1);
	
	//Checks compatibility
	public boolean isCompatible(String system);
	
}
