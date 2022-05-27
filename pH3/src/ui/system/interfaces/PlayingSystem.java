/* CODE USAGE : If you distribute any part of this code as source code or object code, the whole project must be open source, and credit this project. For more info read 'COPYING'
 * and 'COPYING.LESSER'. This is legally binding, and includes use as a library.
 * Author: @Lythd
 */

package ui.system.interfaces;
//The ui (user interface) package is just for allowing the game to be played, and for the user to interact, designed to be as separate as pH as possible, so you could
//easily swap it for another engine, or just play human vs human very easily.
//The system package just refers to all the classes based on the system protocol, general information about the system protocol is in 'Game'.
//The interfaces package just refers to all of the classes that are interfaces for implementing the system protocol.

//This class plays a 'Game', it should be capable with playing any form of 'Game'.
public interface PlayingSystem {
	
	//private static final String SYSTEM; This should be defined for the compatibility, check the 'Game' interface for more information on the compatibility.
	
	//Launches itself with a default game if none provided for ease of use. Remember when creating a game you need to manually set its graphics component
	public void play();
	
	//This is the core player function, and should be able to handle any input game to be a proper PlayingSystem implementation.
	//The 'PlayGame' class is a basic implementation, and is all that is really needed.
	public void play(Game game);
	
	//Checks compatibility
	public boolean isCompatible(String system);
	
}
