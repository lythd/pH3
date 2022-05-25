/* CODE USAGE : If you distribute any part of this code as source code or object code, the whole project must be open source, and credit this project. For more info read 'COPYING'
 * and 'COPYING.LESSER'. This is legally binding, and includes use as a library.
 * Author: @Lythd
 */

package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//The ui (user interface) package is just for allowing the game to be played, and for the user to interact, designed to be as separate as pH as possible, so you could
//easily swap it for another engine, or just play human vs human very easily.

//This class is just a simple console chess ui, it has the advantage of being simple and not using up another thread. 
public class ConsoleChessGraphics implements Graphics {
	
	private BufferedReader reader;
	
	public ConsoleChessGraphics() {
		reader = new BufferedReader(new InputStreamReader(System.in));
	}
	
	@Override
	public void updateState(String board) {
		for(int i = 0; i < 8;) System.out.println(board.substring(i*8,(++i)*8)); //since id use i+1 there anyway, might aswell use ++i which increments and then returns i
		System.out.println("\n\n\n\n");
	}

	@Override
	public void gameOver(float state) {
		if(state == 1) System.out.println("White wins!");
		else if(state == 0) System.out.println("Black wins!");
		else System.out.println("Tie!");
	}

	@Override
	public String input(boolean isWhite) {
		if(isWhite) System.out.println("White make your move,\n> ");
		else System.out.println("Black make your move,\n> ");
		try {
			return reader.readLine();
		} catch (IOException e) {
			return "";
		}
	}

}
