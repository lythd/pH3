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
import ui.system.interfaces.Graphics;
import ui.system.interfaces.Player;

//This class allows you to play a standard untimed game of chess, in a basic console interface. For anything more complicated a different implementation of 'Game' is needed.
public class ChessGame implements Game {

	private static final String SYSTEM = "ch000";

	private Player white;
	private Player black;
	private Graphics graphics;

	private String board;
	private boolean whiteToPlay; // whether white is playing or not, mainly used for storing the information on the board, and normally the parameter whiteToMove is used
	private boolean a1MovedOrCaptured; // has the rook on a1 been moved or captured, this is important for castling
	private boolean a8MovedOrCaptured; // has the rook on a8 been moved or captured, this is important for castling
	private boolean h1MovedOrCaptured; // has the rook on h1 been moved or captured, this is important for castling
	private boolean h8MovedOrCaptured; // has the rook on h8 been moved or captured, this is important for castling
	private boolean e1Moved; // has the king on e1 been moved, this is important for castling
	private boolean e8Moved; // has the king on e8 been moved, this is important for castling
	private char lastMoveDoublePawnPush; // ie if black pushed its a pawn two square it would be 'a', if no double pawn push last turn then its '-', used for en passant
	
	public ChessGame() {
		white = new HumanPlayer();
		black = new PH3Player();
		/* The board string stores pretty much all the information about the board
		 * - First 64 chars are the board itself: a -> h, 8 -> 1, lowercase = black, uppercase = white
		 * - Next char represents the boolean whiteToPlay (when the boolean is set it is set here in the board too) f: false, t: true
		 * - Next char represents the boolean a1MovedOrCaptured (when the boolean is set it is set here in the board too) f: false, t: true
		 * - Next char represents the boolean a8MovedOrCaptured (when the boolean is set it is set here in the board too) f: false, t: true
		 * - Next char represents the boolean h1MovedOrCaptured (when the boolean is set it is set here in the board too) f: false, t: true
		 * - Next char represents the boolean h8MovedOrCaptured (when the boolean is set it is set here in the board too) f: false, t: true
		 * - Next char represents the boolean e1Moved (when the boolean is set it is set here in the board too) f: false, t: true
		 * - Next char represents the boolean e8Moved (when the boolean is set it is set here in the board too) f: false, t: true
		 * - Next char represents lastMoveDoublePawnPush
		 */
		board = "rnbqkbnrpppppppp--------------------------------PPPPPPPPRNBQKBNRtffffff-";
		//Set these joint variable things.
		whiteToPlay = board.charAt(64) == 't' ? true : false;
		a1MovedOrCaptured = board.charAt(65) == 't' ? true : false;
		a8MovedOrCaptured = board.charAt(66) == 't' ? true : false;
		h1MovedOrCaptured = board.charAt(67) == 't' ? true : false;
		h8MovedOrCaptured = board.charAt(68) == 't' ? true : false;
		e1Moved = board.charAt(69) == 't' ? true : false;
		e8Moved = board.charAt(70) == 't' ? true : false;
		lastMoveDoublePawnPush = board.charAt(71);
	}
	
	//This System expects that if a string of length 72 is passed as board_ that it is a valid board, with no invalid characters or positions
	public ChessGame(Player white_, Player black_, String board_) {
		white=white_;
		black=black_;
		/* The board string stores pretty much all the information about the board
		 * - First 64 chars are the board itself: a -> h, 8 -> 1, lowercase = black, uppercase = white
		 * - Next char represents the boolean whiteToPlay (when the boolean is set it is set here in the board too) f: false, t: true
		 * - Next char represents the boolean a1MovedOrCaptured (when the boolean is set it is set here in the board too) f: false, t: true
		 * - Next char represents the boolean a8MovedOrCaptured (when the boolean is set it is set here in the board too) f: false, t: true
		 * - Next char represents the boolean h1MovedOrCaptured (when the boolean is set it is set here in the board too) f: false, t: true
		 * - Next char represents the boolean h8MovedOrCaptured (when the boolean is set it is set here in the board too) f: false, t: true
		 * - Next char represents the boolean e1Moved (when the boolean is set it is set here in the board too) f: false, t: true
		 * - Next char represents the boolean e8Moved (when the boolean is set it is set here in the board too) f: false, t: true
		 * - Next char represents lastMoveDoublePawnPush
		 */
		board = "rnbqkbnrpppppppp--------------------------------PPPPPPPPRNBQKBNRtffffff-";
		if(board_.length()==72)board=board_;// if invalid length then it will use default instead of the invalid board, also allows you to pass in like "" to just use the default
		//Set these joint variable things.
		whiteToPlay = board.charAt(64) == 't' ? true : false;
		a1MovedOrCaptured = board.charAt(65) == 't' ? true : false;
		a8MovedOrCaptured = board.charAt(66) == 't' ? true : false;
		h1MovedOrCaptured = board.charAt(67) == 't' ? true : false;
		h8MovedOrCaptured = board.charAt(68) == 't' ? true : false;
		e1Moved = board.charAt(69) == 't' ? true : false;
		e8Moved = board.charAt(70) == 't' ? true : false;
		lastMoveDoublePawnPush = board.charAt(71);
	}

	@Override
	public void setGraphics(Graphics graphics_) {
		graphics = graphics_;
	}

	@Override
	public boolean isP1Turn() {
		return whiteToPlay;
	}
	
	@Override
	public boolean shouldContinue(boolean whiteToMove) {
		if(graphics == null) return false; // although this function doesn't require graphics, this function being called means graphics havent been set when its run, so will be stuck otherwise
		
		return true;
	}

	@Override
	public boolean isValidMove(String move) {
		//Checking if the move itself can be parsed as a valid specific functional move. This means its of the form 'g1>g3'.
		if(move.length()!=5) return false;
		if(move.charAt(0)<'a'||move.charAt(0)>'h') return false;
		if(move.charAt(1)<'0'||move.charAt(1)>'9') return false;
		/* Not needed as it just needs to be able to be parsed as a valid specific functional move, it does not have to be one. Just a useless restriction.
		 * if(move.charAt(2)!='>') return false;
		 */
		if(move.charAt(3)<'a'||move.charAt(3)>'h') return false;
		if(move.charAt(4)<'0'||move.charAt(4)>'9') return false;
		//Parsing file and rank indexes from the move, indexes being 0 to 7.
		byte startfile = (byte) (((byte) move.charAt(0))-((byte) 'a')); // i hate how i have to recast as a byte, i dont want to use 4 byte integers when im only using numbers 0 to 7
		byte startrank = (byte) (((byte) move.charAt(1))-((byte) '0'));
		byte endfile = (byte) (((byte) move.charAt(3))-((byte) 'a'));
		byte endrank = (byte) (((byte) move.charAt(4))-((byte) '0'));
		//Puts the rest in another function to avoid redundancy for if that information is already available.
		return isValidMove(startfile,startrank,endfile,endrank);
	}
	
	private boolean isValidMove(byte startfile, byte startrank, byte endfile, byte endrank) {
		byte i1 = (byte) (startfile * 8 + startrank); // i hate how operations always return an integer, i want to use single byte numbers dammit
		byte i2 = (byte) (endfile * 8 + endrank);
	/* I still wanted to use the file and rank indexes, also I dislike having so many functions for the same well function.
	 * 	//Puts the rest in another function to avoid redundancy for if that information is already available.
	 * 	return isValidMove(i1,i2);
	 * }
	 * 
	 * private boolean isValidMove(byte i1, byte i2) {
	 */
		char c1 = board.charAt(i1);
		char c2 = board.charAt(i2);
		//Little tangent, I was checking the toLowerCase() to make sure it ran in linear time per character, its still not ideal seems like it goes through so much extra work,
		//but its not bad enough to justify my making my own. Anyways they use integers for the representation of them, which annoys me, java please just let operations return
		//the types they use so that int isn't just the normal. I hate how its just wasteful, I mean I know its only a few bytes here and there but when standard practice is
		//being wasteful then why don't you just fix it. And if anyone says oh thats just cause unicode, its not. They have two representations of strings latin1 and utf18, latin1
		//if you don't know only has 256 characters, meaning it only needs a single byte to represent it.
		/* I got annoyed and removed it anyway so that I could do my own, not a big difference but its easy so why not.
		 * if(!(c1+"").toLowerCase().equals(c1+"") && !(c2+"").toLowerCase().equals(c2+"")) return false; // both squares have white pieces so invalid
		 * if(!(c1+"").toUpperCase().equals(c1+"") && !(c2+"").toUpperCase().equals(c2+"")) return false; // both squares have black pieces so invalid
		 */
		boolean c1white = c1 >= 'A' && c1 <= 'Z';
		boolean c2white = c2 >= 'A' && c2 <= 'Z';
		boolean c1black = c1 >= 'a' && c1 <= 'z'; // its not redundant as a piece can be empty making it neither white nor black
		boolean c2black = c2 >= 'a' && c2 <= 'z';
		if(c1white && c2white) return false; // both squares have white pieces so invalid
		if(c1black && c2black) return false; // both squares have black pieces so invalid
		switch(c1) {
		case 'B': // white bishop
			//Checks if it is possible.
			//Checks if there are any obstructions.
			//Check if its breaking a pin.
			break;
		case 'b': // black bishop
			//Checks if it is possible.
			//Checks if there are any obstructions.
			//Check if its breaking a pin.
			break;
		case 'R': // white rook
			//Checks if it is possible.
			//Checks if there are any obstructions.
			//Check if its breaking a pin.
			break;
		case 'r': // black rook
			//Checks if it is possible.
			//Checks if there are any obstructions.
			//Check if its breaking a pin.
			break;
		case 'Q': // white queen
			//Checks if it is possible.
			//Checks if there are any obstructions.
			//Check if its breaking a pin.
			break;
		case 'q': // black queen
			//Checks if it is possible.
			//Checks if there are any obstructions.
			//Check if its breaking a pin.
			break;
		case 'K': // white king
			//If its e1 to g1 and not e1Moved and not a1MovedOrCaptured
				//if either f1 or g1 or occupied then return false
				//if either e1 f1 or g1 are being attacked then return false
				//return true
			//If its e1 to c1 and not e1Moved and not h1MovedOrCaptured
				//if either d1 or c1 or occupied then return false
				//if either e1 d1 or c1 are being attacked then return false
				//return false before continuing on
			//Checks if it is possible.
			//Checks if there are any obstructions.
			//Check if it is being attacked.
			break;
		case 'k': // black king
			//If its e8 to g8 and not e8Moved and not a8MovedOrCaptured
				//if either f8 or g8 or occupied then return false
				//if either e8 f8 or g8 are being attacked then return false
				//return true
			//If its e8 to c8 and not e8Moved and not h8MovedOrCaptured
				//if either d8 or c8 or occupied then return false
				//if either e8 d8 or c8 are being attacked then return false
				//return false before continuing on
			//Checks if it is possible.
			//Checks if there are any obstructions.
			//Check if it is being attacked.
			break;
		case 'P': // white pawn
			//If 2 to 4
				//Check any obstructions.
				//Check if its pinned.
				//Return at the end
			//If diagonal thing.
				//check if something to capture or something to en passant
				//Check if its breaking a pin.
				//Return at the end.
			//Checks if it is possible.
			//Checks if there are any obstructions.
			//Check if its breaking a pin.
			break;
		case 'p': // black pawn
			//If 7 to 5
				//Check any obstructions.
				//Check if its pinned.
				//Return at the end
			//If diagonal thing.
				//check if something to capture or something to en passant
				//Check if its breaking a pin.
				//Return at the end.
			//Checks if it is possible.
			//Checks if there are any obstructions.
			//Check if its breaking a pin.
			break;
		case 'N': // white knight
			//Checks if it is possible.
			//Checks if there are any obstructions.
			//Check if its breaking a pin.
			break;
		case 'n': // black knight
			//Checks if it is possible.
			//Checks if there are any obstructions.
			//Check if its breaking a pin.
			break;
		default:
			return false;
		}
		return true;
	}

	@Override
	public void move(boolean whiteToMove) {
		whiteToPlay = whiteToMove;
		setBoardChar((byte) 64, whiteToPlay ? 't' : 'f');
		
		if(graphics == null) return;
		
		String move = "";
		if(whiteToMove) move = white.makeMove(this);
		else move = black.makeMove(this);
		
		//Process move, remember its guaranteed to be valid by the interface so no need for checking.
		
		
		graphics.updateState(board); // update graphics
	}

	//Takes in any general functional move, or any general display move, and converts it into a specific display move.
	//   Example: "f1-g3" -> "Ng3"
	/* General Functional Move
	 *   start and end square in one of the following formats "f1>g3", "f1.g3", ".f1g3", "f1-g3", "f1g3"
	 * General Display Move
	 *   this is the normal way of writing a move, "abcdefgh"
	 * 		a: piece (omitted for pawn), NBRQK
	 * 		b: starting file, will be omitted if its not needed to uniquely specify the move
	 * 		c: starting rank, will be omitted if its not needed to uniquely specify the move
	 * 		d: 'x' if it is capture, otherwise its omitted
	 * 		e: ending file
	 * 		f: ending rank
	 * 		g: # if checkmate, + if check, (sometimes ++ is used for double check)
	 * 		h: !! if brilliant move, ! if great move, ?! if inaccuracy , ? if mistake, ?? if blunder
	 * Specific Display Move
	 *   this is the normal way of writing a move, "abcdefgh"
	 * 		a: piece (omitted for pawn), NBRQK
	 * 		b: starting file, can be omitted if its not needed to uniquely specify the move
	 * 		c: starting rank, can be omitted if its not needed to uniquely specify the move
	 * 		d: 'x' if it is capture, otherwise its omitted, and can always be omitted
	 * 		e: ending file
	 * 		f: ending rank
	 * 		g: # if checkmate, + if check, (sometimes ++ is used for double check), can always be omitted and doesn't care if the wrong one is used
	 * 		h: !! if brilliant move, ! if great move, ?! if inaccuracy , ? if mistake, ?? if blunder, can always be omitted and doesn't care if the wrong one is used
	 */
	public String getDisplayMove(String move) {
		if(true);//do cases for each possible form and stuff
		
		return "";
	}
	
	
	//Takes in any general functional move, or any general display move, and converts it into a specific functional move.
	//   Example: "Ng3" -> "f1-g3"
	/* General Functional Move
	 *   start and end square in one of the following formats "f1>g3", "f1.g3", ".f1g3", "f1-g3", "f1g3"
	 * General Display Move
	 *   this is the normal way of writing a move, "abcdefgh"
	 * 		a: piece (omitted for pawn), NBRQK
	 * 		b: starting file, can be omitted if its not needed to uniquely specify the move
	 * 		c: starting rank, can be omitted if its not needed to uniquely specify the move
	 * 		d: 'x' if it is capture, otherwise its omitted, and can always be omitted
	 * 		e: ending file
	 * 		f: ending rank
	 * 		g: # if checkmate, + if check, (sometimes ++ is used for double check), can always be omitted and doesn't care if the wrong one is used
	 * 		h: !! if brilliant move, ! if great move, ?! if inaccuracy , ? if mistake, ?? if blunder, can always be omitted and doesn't care if the wrong one is used
	 * Specific Functional Move
	 *   start and end square in this specific format "f1-g3"
	 */
	public String getFunctionalMove(String move) {
		return "";
	}
	
	private void setBoardChar(byte i, char c) {
		if(i<0 || i>=72) return;
		board = board.substring(0,i) + c + board.substring(i+1);
	}

	@Override
	public boolean isCompatible(String system) {
		if(SYSTEM.equals("") || system.equals("")) return true;
		if(!SYSTEM.substring(0, SYSTEM.length()-2).equals(system.subSequence(0, system.length()-2))) return false;
		return true;
	}
}
