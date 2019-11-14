package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class IntelligentMovementStrategy implements Enemy.MovementStrategy {

	// it's probably better to treat enemies as uncollidable (unless you're a boulder).
	// ...
	
	private static Integer heuristic(WorldState world) {
		int Dx = world.getMyX() - world.getGoalX();
		int Dy = world.getMyY() - world.getGoalY();
		
		return Math.abs(Dx) + Math.abs(Dy) + world.getDepth(); 
	}
	
	@Override
	public Direction chooseMove(WorldState world, boolean seek) {
		PriorityQueue<WorldState> pq = new PriorityQueue<WorldState>(
			(WorldState a, WorldState b) -> heuristic(a).compareTo(heuristic(b))
		);

		List<Direction> allDirections = new ArrayList<Direction>();
		allDirections.add(Direction.DOWN);
		allDirections.add(Direction.UP);
		allDirections.add(Direction.LEFT);
		allDirections.add(Direction.RIGHT);
		pq.add(world);
		
		int maxDepth = 10; // change this to max num nodes to generate ...
		
		
		// make sure the pq is doing what it's meant to be doing ...
		
		while ((pq.peek() != null)) {
			WorldState ws = pq.poll();
			if (ws.hasMetGoal()) {
				return ws.getStartDirection();
			}
			
			for (Direction d : allDirections) {
				WorldState next = ws.transition(d);
				if (next != null && next.getDepth() < maxDepth) {
					pq.add(next);
				}
			}
		}
		
		// don't generate past a certain depth ... 

		return null;
//		int x = world.getMyX();
//		int y = world.getMyY();
//
//		int Dx = x - world.getGoalX();
//		int Dy = y - world.getGoalY();
//		
//		if (!seek) {
//			Dx = -Dx;
//			Dy = -Dy;
//		}
//
//		if (Math.abs(Dx) > Math.abs(Dy)) {
//			if (Dx > 0) {
//				if (!world.getIsCollidable(x - 1, y)) {
//					return Direction.LEFT;
//				}
//			} else if (Dx < 0) {
//				if (!world.getIsCollidable(x + 1, y)) {
//					return Direction.RIGHT;
//				}
//			}
//		}
//
//		if (Dy > 0) {
//			if (!world.getIsCollidable(x, y - 1)) {
//				return Direction.UP;
//			}
//		} else if (Dy < 0) {
//			if (!world.getIsCollidable(x, y + 1)) {
//				return Direction.DOWN;
//			}
//		}
//
//		if (Dx > 0) {
//			if (!world.getIsCollidable(x - 1, y)) {
//				return Direction.LEFT;
//			}
//		} else if (Dx < 0) {
//			if (!world.getIsCollidable(x + 1, y)) {
//				return Direction.RIGHT;
//			}
//		}

	}
	
}




