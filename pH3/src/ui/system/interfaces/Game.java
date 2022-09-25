/* CODE USAGE : If you distribute any part of this code as source code or object code, the whole project must be open source, and credit this project. For more info read 'COPYING'
 * and 'COPYING.LESSER'. This is legally binding, and includes use as a library.
 * Author: @Lythd
 */

package ui.system.interfaces;
//The ui (user interface) package is just for allowing the game to be played, and for the user to interact, designed to be as separate as pH as possible, so you could
//easily swap it for another engine, or just play human vs human very easily.
//The system package just refers to all the classes based on the system protocol, general information about the system protocol is in 'Game'.
//The interfaces package just refers to all of the classes that are interfaces for implementing the system protocol.

//This class is an interface so that any playing system (ie PlayGame) can work with any game, not just that specific game.
public interface Game {
	
	//private static final String SYSTEM; This should be defined for the compatibility. Read more about compatibility and systems below.
	 
	/* Game Interface System Protocol (Often referred to as just GISP, System Protocol, or somewhat incorrectly as System):
	 * 
	 * Basically its meant to be a modular interface for systems, with the purpose of playing games. Maybe someone made a checkers ai and you want to see if you can make your own that
	 * is better now you can very easily. You only have to make the parts you want to make with this, everything else you can use from someone else, even mix and match if you so want.
	 * 
	 * There are 4 parts to this.
	 * PlayingSystem - the controller essentially, can be very simple but sometimes it might need a more advanced purpose.
	 * Game - does all the game logic.
	 * Player - decides what moves to make, doesn't have to be a human player.
	 * Graphics - displays the game.
	 * 
	 * GISP is just the standard way of setting up these components so that they can communicate with each other, if there's no standard then no point in it being modular.
	 */
	
	/* Compatibility and Systems:
	 * 
	 * Game, Player, and Graphics all use some form of the game state, Game and Play also use some form of move; the problem with that is they need the same format as otherwise
	 * they won't understand each other. Its foolish to think there could be a format that works for everything, and especially when it has to be feasible for use. So if you want
	 * to ensure something uses a certain format then put it in a system.
	 * 
	 * Each class that implements GISP will need to have a 'isCompatible()' function, which checks if it is compatible with a certain system. It should use its own system to compare.
	 * 
	 * Ideally the compatibility should go as follows:
	 *  - If either its own system or the system its checking against is the blank system "", then it should automatically accept.
	 *  - If all characters excluding the last two do not match, then it should automatically reject.
	 *  - The last two characters can be interpreted as it wills, but should be done according to the general system.
	 * 
	 * I say ideally as maybe there is some multiplayer thing and the playing system needs to start it up for instance. It will need to make sure it is a specific playing system not
	 * just the blank system, as otherwise it won't implement that specific behavior. So its not strict as I don't want to impede on these things making it harder to develop.
	 * 
	 * An example of a system is "ch000"
	 *  - 5 characters, and the last 3 being numbers, are both just convention, and not required.
	 *  - When you exclude the last two you get the general system, though it most often ends in a number to signify versions. For instance if you wanted to implement the 50 move rule
	 *    in chess then you would need to expand the board state, this makes any other class incompatible, so even though its based on the same general system it has to be a new
	 *    version.
	 *  - The last two characters are normally used for some form of backwards compatibility, like maybe ch003 is actually compatible with graphics from ch002, obviously it cannot
	 *    include full compatibility as that would mean no need for a new system.
	 * 
	 * With that being said here is a list of systems in use here:
	 *  - "ch000" - the first system, used for chess. uses 72 characters for board, and a simple 5 character for move (ie "f1>g3")
	 */
	
	//Given the whole system aims for both ease of use, and flexibility, there should be a minimum of these two constructors:
	// 1) This one is just a blank 'game()', this is for ease of use.
	// 2) This one should take in all the parameters set in the initialization, allowing for maximum customization, to take in custom players, and to continue where you left off.
	
	//Detects whether the game should continue, if it shouldn't then output the appropriate ending message, returns boolean of whether it should.
	//In the implementation it is expected that that the player being passed in has not moved yet.
	public boolean shouldContinue(boolean p1ToMove);
	
	//The constructor might have it start on p2's turn, but the playing system would be unaware, so this function is meant to be used at the start so the playing system knows who's
	//turn it is.
	public boolean isP1Turn();
	
	//To be used by the player before returning the move they make, so that in 'move()' it is guaranteed to receive a valid move.
	public boolean isValidMove(String move);
	
	//Requests a move from either p1 or p2 depending on p1ToMove, and then applies that move in the game.
	public void move(boolean p1ToMove);

	//Sets the graphics manager of the game, this does allow it to be switched mid game seamlessly.
	public void setGraphics(Graphics graphics);

	//Although access to the whole graphics is not permitted beyond the Game class, the Player classes will need access to the input. So it is provided through this proxy.
	//It also allows the Game class to provide any modification needed specific to the game.
	public String input(boolean isP1);
	
	//Checks compatibility
	public boolean isCompatible(String system);
	
}
