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
	
	private ChessGame(ChessGame g) {
		white=new NullPlayer();
		black=new NullPlayer();
		graphics=new NullGraphics();
		board=g.board;
		whiteToPlay=g.whiteToPlay;
		a1MovedOrCaptured=g.a1MovedOrCaptured;
		a8MovedOrCaptured=g.a8MovedOrCaptured;
		h1MovedOrCaptured=g.h1MovedOrCaptured;
		h8MovedOrCaptured=g.h8MovedOrCaptured;
		e1Moved=g.e1Moved;
		e8Moved=g.e8Moved;
		lastMoveDoublePawnPush=g.lastMoveDoublePawnPush;
	}
	
	public ChessGame() {
		white = new HumanPlayer();
//		black = new PH3Player();
		black = new HumanPlayer();
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
		graphics.updateState(board);
	}
	
	@Override
	public String input(boolean isP1) {
		return graphics.input(isP1);
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
		if(move.charAt(1)<'1'||move.charAt(1)>'8') return false;
		/* Not needed as it just needs to be able to be parsed as a valid specific functional move, it does not have to be one. Just a useless restriction.
		 * if(move.charAt(2)!='>') return false;
		 */
		if(move.charAt(3)<'a'||move.charAt(3)>'h') return false;
		if(move.charAt(4)<'1'||move.charAt(4)>'8') return false;
		//Parsing file and rank indexes from the move, indexes being 0 to 7. Rank indexes are also flipped.
		byte startfile = (byte) (((byte) move.charAt(0))-((byte) 'a')); // i hate how i have to recast as a byte, i dont want to use 4 byte integers when im only using numbers 0 to 7
		byte startrank = (byte) (7-(((byte) move.charAt(1))-((byte) '1')));
		byte endfile = (byte) (((byte) move.charAt(3))-((byte) 'a'));
		byte endrank = (byte) (7-(((byte) move.charAt(4))-((byte) '1')));
		//Puts the rest in another function to avoid redundancy for if that information is already available.
		return isValidMove(board,startfile,startrank,endfile,endrank,move);
	}
	
	private boolean isValidMove(String board, byte startfile, byte startrank, byte endfile, byte endrank, String move) {
		//remember all these inputs are guaranteed to be valid since within the class
		byte i1 = (byte) (startfile + 8 * startrank); // i hate how operations always return an integer, i want to use single byte numbers dammit
		byte i2 = (byte) (endfile + 8 * endrank); // also to clarify since i messed it up myself first, when i say file and rank i mean which file and rank, not where on the file and rank. so the first 8 positions in the board are changing file while keeping rank, thus the file is the smaller index.
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
			if(Math.abs(endfile-startfile)-Math.abs(endrank-startrank)!=0)return false;
			//Checks if there are any obstructions.
			if(Character.isUpperCase(board.charAt(8*endrank+endfile))) return false;
			for(int i=1;i<Math.abs(endrank-startrank);i+=1) {
				if(board.charAt((startfile+(endfile>startfile?i:-i))+8*(startrank+(endrank>startrank?i:-i)))!='-')return false;
			}
			break;
		case 'b': // black bishop
			//Checks if it is possible.
			if(Math.abs(endfile-startfile)-Math.abs(endrank-startrank)!=0)return false;
			//Checks if there are any obstructions.
			if(Character.isLowerCase(board.charAt(8*endrank+endfile))) return false;
			for(int i=1;i<Math.abs(endrank-startrank);i+=1) {
				if(board.charAt((startfile+(endfile>startfile?i:-i))+8*(startrank+(endrank>startrank?i:-i)))!='-')return false;
			}
			break;
		case 'R': // white rook
			//Checks if it is possible.
			if(endfile!=startfile&&endrank!=startrank)return false;
			//Checks if there are any obstructions.
			if(Character.isUpperCase(board.charAt(8*endrank+endfile))) return false;
			if(endfile==startfile) {
				for(int i=1;i<Math.abs(endrank-startrank);i+=1) {
					if(board.charAt(startfile+8*(startrank+(endrank>startrank?i:-i)))!='-')return false;
				}
			}
			else {
				for(int i=1;i<Math.abs(endfile-startfile);i+=1) {
					if(board.charAt((startfile+(endfile>startfile?i:-i))+8*startrank)!='-')return false;
				}
			}
			break;
		case 'r': // black rook
			//Checks if it is possible.
			if(endfile!=startfile&&endrank!=startrank)return false;
			//Checks if there are any obstructions.
			if(Character.isLowerCase(board.charAt(8*endrank+endfile))) return false;
			
			break;
		case 'Q': // white queen
			//Checks if it is possible.
			if(endfile!=startfile&&endrank!=startrank&&Math.abs(endfile-startfile)-Math.abs(endrank-startrank)!=0)return false;
			//Checks if there are any obstructions.
			if(Character.isUpperCase(board.charAt(8*endrank+endfile))) return false;
			if(endfile==startfile) {
				for(int i=1;i<Math.abs(endrank-startrank);i+=1) {
					if(board.charAt(startfile+8*(startrank+(endrank>startrank?i:-i)))!='-')return false;
				}
			}
			else if(endrank==startrank) {
				for(int i=1;i<Math.abs(endfile-startfile);i+=1) {
					if(board.charAt((startfile+(endfile>startfile?i:-i))+8*startrank)!='-')return false;
				}
			} else {
				for(int i=1;i<Math.abs(endrank-startrank);i+=1) {
					if(board.charAt((startfile+(endfile>startfile?i:-i))+8*(startrank+(endrank>startrank?i:-i)))!='-')return false;
				}
			}
			break;
		case 'q': // black queen
			//Checks if it is possible.
			if(endfile!=startfile&&endrank!=startrank&&Math.abs(endfile-startfile)-Math.abs(endrank-startrank)!=0)return false;
			//Checks if there are any obstructions.
			if(Character.isLowerCase(board.charAt(8*endrank+endfile))) return false;
			if(endfile==startfile) {
				for(int i=1;i<Math.abs(endrank-startrank);i+=1) {
					if(board.charAt(startfile+8*(startrank+(endrank>startrank?i:-i)))!='-')return false;
				}
			}
			else if(endrank==startrank) {
				for(int i=1;i<Math.abs(endfile-startfile);i+=1) {
					if(board.charAt((startfile+(endfile>startfile?i:-i))+8*startrank)!='-')return false;
				}
			} else {
				for(int i=1;i<Math.abs(endrank-startrank);i+=1) {
					if(board.charAt((startfile+(endfile>startfile?i:-i))+8*(startrank+(endrank>startrank?i:-i)))!='-')return false;
				}
			}
			break;
		case 'K': // white king
			//If its e1 to g1 and not e1Moved and not h1MovedOrCaptured
			System.out.println("a");
			if(startfile==4&&startrank==7&&endfile==6&&endrank==7&&!e1Moved&&!h1MovedOrCaptured) {
				System.out.println("b");
				//if either f1 or g1 are occupied then return false
				if(board.charAt(5+8*7)!='-'||board.charAt(6+8*7)!='-')return false;
				System.out.println("c");
				//if either e1 f1 or g1 are being attacked then return false (g1 is checked at the end)
				if(!checkKing(board,"")) return false; //checks e1
				System.out.println("d");
				if(!checkKing(board,"e1.f1")) return false; //checks f1
				System.out.println("e");
				//break
				break;
			}
			//If its e1 to c1 and not e1Moved and not a1MovedOrCaptured
			if(startfile==4&&startrank==7&&endfile==2&&endrank==7&&!e1Moved&&!a1MovedOrCaptured) {
				//if either d1, c1 or b1 are occupied then return false
				if(board.charAt(3+8*7)!='-'||board.charAt(2+8*7)!='-'||board.charAt(1+8*7)!='-')return false;
				//if either e1 d1 or c1 are being attacked then return false (c1 is checked at the end)
				if(!checkKing(board,"")) return false; //checks e1
				if(!checkKing(board,"e1.d1")) return false; //checks d1
				//break
				break;
			}
			//Checks if it is possible.
			if(endfile==startfile&&endrank==startrank||Math.abs(endrank-startrank)>1||Math.abs(endfile-startfile)>1) return false;
			//Checks if there are any obstructions.
			if(Character.isUpperCase(board.charAt(8*endrank+endfile))) return false;
			break;
		case 'k': // black king
			//If its e8 to g8 and not e8Moved and not h8MovedOrCaptured
			if(startfile==4&&startrank==0&&endfile==6&&endrank==0&&!e8Moved&&!h8MovedOrCaptured) {
				//if either f8 or g8 are occupied then return false
				if(board.charAt(5+8*0)!='-'||board.charAt(6+8*0)!='-')return false;
				//if either e8 f8 or g8 are being attacked then return false (g8 is checked at the end)
				if(!checkKing(board,"")) return false; //checks e8
				if(!checkKing(board,"e8.f8")) return false; //checks f8
				//break
				break;
			}
			//If its e8 to c8 and not e8Moved and not a8MovedOrCaptured
			if(startfile==4&&startrank==0&&endfile==2&&endrank==0&&!e8Moved&&!a8MovedOrCaptured) {
				//if either d8, c8 or b8 are occupied then return false
				if(board.charAt(3+8*0)!='-'||board.charAt(2+8*0)!='-'||board.charAt(1+8*0)!='-')return false;
				//if either e8 d8 or c8 are being attacked then return false (c8 is checked at the end)
				if(!checkKing(board,"")) return false; //checks e8
				if(!checkKing(board,"e8.d8")) return false; //checks d8
				//break
				break;
			}
			//Checks if it is possible.
			if(endfile==startfile&&endrank==startrank||Math.abs(endrank-startrank)>1||Math.abs(endfile-startfile)>1) return false;
			//Checks if there are any obstructions.
			if(Character.isLowerCase(board.charAt(8*endrank+endfile))) return false;
			break;
		case 'P': // white pawn
			//If 2 to 4
			if(startrank==6&&endrank==4) {
				//Check any obstructions.
				if(board.charAt(8*endrank+endfile)!='-'||board.charAt(8*endrank+8+endfile)!='-')return false;
				//Break at the end.
				break;
			}
			//If diagonal thing.
			if(endrank==startrank-1&&Math.abs(endfile-startfile)==1) {
				//Check if something to capture or something to en passant
				if(((lastMoveDoublePawnPush-'a')!=endrank||endfile!=2)&&!Character.isLowerCase(board.charAt(8*endrank+endfile)))return false;
				//Break at the end.
				break;
			}
			//Checks if it is possible.
			if(endfile!=startfile||endrank!=startrank-1)return false;
			//Checks if there are any obstructions.
			if(board.charAt(8*endrank+endfile)!='-')return false;
			break;
		case 'p': // black pawn
			//If 7 to 5
			if(startrank==1&&endrank==3) {
				//Check any obstructions.
				if(board.charAt(8*endrank+endfile)!='-'||board.charAt(8*endrank-8+endfile)!='-')return false;
				//Break at the end.
				break;
			}
			//If diagonal thing.
			if(endrank==startrank+1&&Math.abs(endfile-startfile)==1) {
				//Check if something to capture or something to en passant
				if(((lastMoveDoublePawnPush-'a')!=endrank||endfile!=6)&&!Character.isUpperCase(board.charAt(8*endrank+endfile)))return false;
				//Break at the end.
				break;
			}
			//Checks if it is possible.
			if(endfile!=startfile||endrank!=startrank+1)return false;
			//Checks if there are any obstructions.
			if(board.charAt(8*endrank+endfile)!='-')return false;
			break;
		case 'N': // white knight
			//Checks if it is possible.
			if((Math.abs(endfile-startfile)!=2||Math.abs(endrank-startrank)!=1)&&(Math.abs(endfile-startfile)!=1||Math.abs(endrank-startrank)!=2))return false;
			//Checks if there are any obstructions.
			if(Character.isUpperCase(board.charAt(8*endrank+endfile))) return false;
			break;
		case 'n': // black knight
			//Checks if it is possible.
			if((Math.abs(endfile-startfile)!=2||Math.abs(endrank-startrank)!=1)&&(Math.abs(endfile-startfile)!=1||Math.abs(endrank-startrank)!=2))return false;
			//Checks if there are any obstructions.
			if(Character.isLowerCase(board.charAt(8*endrank+endfile))) return false;
			break;
		default:
			return false;
		}
		//check if king is being attacked, if it is then return false
		if(!move.equals("")&&!checkKing(board,move))return false;
		return true;
	}

	private boolean checkKing(String board, String move) {
		ChessGame tmpg = new ChessGame(this);
		if(!move.equals(""))tmpg.move(move);
		tmpg.whiteToPlay=!whiteToPlay;
		int tmpin = board.indexOf(whiteToPlay ? "K" : "k");
		for(int f=0;f<8;f++)for(int r=0;r<8;r++) {
			char tmpc = board.charAt(f+r*8);
			if((whiteToPlay && tmpc >= 'a' && tmpc <= 'z' || !whiteToPlay && tmpc >= 'A' && tmpc <= 'Z')&&tmpg.isValidMove(tmpg.board, (byte)f, (byte)r, (byte)(tmpin%8), (byte)(tmpin/8), "")) {tmpg=null;return false;}
		}
		tmpg=null;
		return true;
	}
	
	@Override
	public void move(boolean whiteToMove) {
		this.whiteToPlay = whiteToMove;
		
		if(graphics == null) return;
		
		String move = "";
		if(whiteToMove) move = white.makeMove(this);
		else move = black.makeMove(this);
		move(move);
	}
	
	private void move(String move) {
		//Process move, remember its guaranteed to be valid by the interface so no need for checking.
		String pos1 = move.substring(0,2);
		String pos2 = move.substring(3,5);
		if(lastMoveDoublePawnPush!='-') {
			if(pos2.charAt(0)==lastMoveDoublePawnPush) {
				if(getPiece(pos1)=='p'&&pos2.charAt(1)=='3') setPiece(pos2.charAt(0)+"4",'-');
				else if(getPiece(pos1)=='P'&&pos2.charAt(1)=='6') setPiece(pos2.charAt(0)+"5",'-');
			}
		}
		lastMoveDoublePawnPush='-';
		if(getPiece(pos1)=='p') {
			if(pos1.charAt(0)==pos2.charAt(0)&&pos1.charAt(1)=='7'&&pos2.charAt(1)=='5') lastMoveDoublePawnPush=pos1.charAt(0);
		} else if(getPiece(pos1)=='P') {
			if(pos1.charAt(0)==pos2.charAt(0)&&pos1.charAt(1)=='2'&&pos2.charAt(1)=='4') lastMoveDoublePawnPush=pos1.charAt(0);
		}
		if(pos1.equals("e1")&&pos2.equals("g1")&&getPiece(pos1)=='K') {setPiece("f1",'R');setPiece("h1",'-');}
		if(pos1.equals("e1")&&pos2.equals("c1")&&getPiece(pos1)=='K') {setPiece("d1",'R');setPiece("a1",'-');}
		if(pos1.equals("e8")&&pos2.equals("g8")&&getPiece(pos1)=='k') {setPiece("f8",'r');setPiece("h8",'-');}
		if(pos1.equals("e8")&&pos2.equals("c8")&&getPiece(pos1)=='k') {setPiece("d8",'r');setPiece("a8",'-');}
		setPiece(pos2,getPiece(pos1));
		setPiece(pos1,'-');
		if(getPiece("a1")!='R')a1MovedOrCaptured=true;
		if(getPiece("h1")!='R')h1MovedOrCaptured=true;
		if(getPiece("a8")!='r')a8MovedOrCaptured=true;
		if(getPiece("h8")!='r')h8MovedOrCaptured=true;
		if(getPiece("e1")!='K')e1Moved=true;
		if(getPiece("e8")!='k')e8Moved=true;
		board = board.substring(0,64)+(whiteToPlay ? 't' : 'f')+(a1MovedOrCaptured ? 't' : 'f')+(a8MovedOrCaptured ? 't' : 'f')+(h1MovedOrCaptured ? 't' : 'f')+(h8MovedOrCaptured ? 't' : 'f')+(e1Moved ? 't' : 'f')+(e8Moved ? 't' : 'f')+lastMoveDoublePawnPush;
		
		graphics.updateState(board); // update graphics
	}
	
	//expects valid place, the only lenience is that the letter in place can be uppercase
	public char getPiece(String place) {
		return getPiece(board,place);
	}

	//expects valid place and piece, the only lenience is that the letter in place can be uppercase
	public void setPiece(String place, char piece) {
		board = setPiece(board,place,piece);
	}
	
	//expects valid place, the only lenience is that the letter in place can be uppercase
	private char getPiece(String board, String place) {
		place = place.toLowerCase();
		int i = 7-(place.charAt(1)-'1');
		int j = place.charAt(0)-'a';
		return board.charAt(8*i+j);
	}

	//expects valid place and piece, the only lenience is that the letter in place can be uppercase
	private String setPiece(String board, String place, char piece) {
		place = place.toLowerCase();
		int i = 7-(place.charAt(1)-'1');
		int j = place.charAt(0)-'a';
		return board.substring(0,8*i+j)+piece+board.substring(8*i+j+1);
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

	@Override
	public boolean isCompatible(String system) {
		if(SYSTEM.equals("") || system.equals("")) return true;
		if(!SYSTEM.substring(0, SYSTEM.length()-2).equals(system.subSequence(0, system.length()-2))) return false;
		return true;
	}
}
