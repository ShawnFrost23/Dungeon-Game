package unsw.dungeon.back;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Game g = Game.createGame(
			""
			+ "WWWWWWWWWWWWWWWWWW\n"
			+ "W                W\n"
			+ "W     W  BWW !   W\n"
			+ "W          W     W\n"
			+ "W  P  B          W\n"
			+ "W            !   W\n"
			+ "WWWWWWWWWWWWWWWWWW\n"
			);
		
		for (int i = 0; i < 40; ++i) {
			System.out.println("");
		}
		
		g.display();
		
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
			}
			
			g.display();
			
		}
		sc.close();
		System.out.println("Fin.");
	}
}
