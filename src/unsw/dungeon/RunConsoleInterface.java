package unsw.dungeon;

import java.util.Scanner;
import unsw.dungeon.back.*;

public class RunConsoleInterface {
	public static void main(String[] args) {
		Game g = Game.createGame(new PuzzleGoal(), ""
			+ "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n"
			+ "W    T                 W  !    T W\n"
			+ "W  T  W  BWW    ! WWW  W  W B    W\n"
			+ "W     ~   SW      W_W  W WWO     W\n"
			+ "W  P  B _              W  W      W\n"
			+ "W  S       # ! _   O   W    W    W\n"
			+ "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n"
		);
		
		for (int i = 0; i < 40; ++i) {
			System.out.println("");
		}
		
		System.out.println(g.getBoardString());
		
		System.out.println("Your move: ");
		Scanner sc = new Scanner(System.in);
		String line;
		while ((line = sc.nextLine()) != null)
		{
			if (line.equals("")) {
				break;
			}
			
			if (line.charAt(0) == 'w') {
				g.movePlayer(Direction.UP);
			} else if (line.charAt(0) == 'a') {
				g.movePlayer(Direction.LEFT);
			} else if (line.charAt(0) == 's') {
				g.movePlayer(Direction.DOWN);
			} else if (line.charAt(0) == 'd') {
				g.movePlayer(Direction.RIGHT);
			} else if (line.charAt(0) == 'e') {
				g.moveEnemies();
			} else if (line.charAt(0) == 'W') {
				g.swingSword(Direction.UP);
			} else if (line.charAt(0) == 'A') {
				g.swingSword(Direction.LEFT);
			} else if (line.charAt(0) == 'S') {
				g.swingSword(Direction.DOWN);
			} else if (line.charAt(0) == 'D') {
				g.swingSword(Direction.RIGHT);
			} else if (line.charAt(0) == 'q') {
				g.dropKey();
			}
			
			
			System.out.println(g.getBoardString());
			
		}
		sc.close();
		System.out.println("Fin.");
	}
}
