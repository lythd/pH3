/* CODE USAGE : If you distribute any part of this code as source code or object code, the whole project must be open source, and credit this project. For more info read 'COPYING'
 * and 'COPYING.LESSER'. This is legally binding, and includes use as a library.
 * Author: @Lythd
 */

package ui.system.examples;
//The ui (user interface) package is just for allowing the game to be played, and for the user to interact, designed to be as separate as pH as possible, so you could
//easily swap it for another engine, or just play human vs human very easily.
//The system package just refers to all the classes based on the system protocol, general information about the system protocol is in 'Game'.
//The examples package just refers to all of the classes that implement the system protocol, rather than being the interfaces for it.

import ui.system.interfaces.Graphics;

//This class is just so you can put in a graphics class, but without doing anything.
public class NullGraphics implements Graphics {

	private static final String SYSTEM = "";
	
	public NullGraphics() {}
	
	@Override
	public void updateState(String board) {}

	@Override
	public void gameOver(float state) {}

	@Override
	public String input(boolean isWhite) {return "";}

	@Override
	public boolean isCompatible(String system) {
		if(SYSTEM.equals("") || system.equals("")) return true;
		if(!SYSTEM.substring(0, SYSTEM.length()-2).equals(system.subSequence(0, system.length()-2))) return false;
		return true;
	}

}
