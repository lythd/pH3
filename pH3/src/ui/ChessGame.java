/* CODE USAGE : If you distribute any part of this code as source code or object code, the whole project must be open source, and credit this project. For more info read 'COPYING'
 * and 'COPYING.LESSER'. This is legally binding, and includes use as a library.
 * Author: @Lythd
 */

package ui;
//The ui (user interface) package is just for allowing the game to be played, and for the user to interact, designed to be as separate as pH as possible, so you could
//easily swap it for another engine, or just play human vs human very easily.

//This class allows you to play a standard untimed game of chess, in a basic console interface. For anything more complicated a different implementation of 'Game' is needed.
public class ChessGame implements Game {

	Player white;
	Player black;
	String board;
	
	private Graphics graphics;
	
	public ChessGame() {
		white = new HumanPlayer();
		black = new PH3Player();
		board = "rnbqkbnrpppppppp--------------------------------PPPPPPPPRNBQKBNR"; // a -> h, 8 -> 1, lowercase = black, uppercase = white
	}
	
	//This System expects that if a string of length 64 is passed as board_ that it is a valid board, with no invalid characters or positions
	public ChessGame(Player white_, Player black_, String board_) {
		white=white_;
		black=black_;
		board = "rnbqkbnrpppppppp--------------------------------PPPPPPPPRNBQKBNR"; // a -> h, 8 -> 1, lowercase = black, uppercase = white
		if(board_.length()==64)board=board_;// if invalid length then it will use default instead of the invalid baord, also allows you to pass in like "" to just use the default
	}

	@Override
	public void setGraphics(Graphics graphics_) {
		graphics = graphics_;
	}
	
	@Override
	public boolean shouldContinue(boolean whiteToMove) {
		return false;
	}

	@Override
	public void isValidMove(String move) {
		
	}

	@Override
	public void move(boolean whiteToMove) {
		String move = "";
		if(whiteToMove) move = white.makeMove(this);
		else move = black.makeMove(this);
		
		graphics.updateState(board); // update graphics
	}

}
