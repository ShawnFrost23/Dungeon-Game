package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class IntelligentMovementStrategy implements Enemy.MovementStrategy {
	private static Integer heuristicPlusDepth(WorldState world) {
		return heuristic(world) + world.getDepth(); 
	}
	private static int heuristic(WorldState world) {
		int Dx = world.getMyX() - world.getGoalX();
		int Dy = world.getMyY() - world.getGoalY();
		return Math.abs(Dx) + Math.abs(Dy); 
	}
	
	@Override
	public Direction chooseMove(WorldState world, boolean seek) {
		PriorityQueue<WorldState> pq = new PriorityQueue<WorldState>(
			(WorldState a, WorldState b) -> heuristicPlusDepth(a).compareTo(heuristicPlusDepth(b))
		);

		// TODO: the order of this list is what resolves ties ... 
		// should we shuffle this? 
		List<Direction> allDirections = new ArrayList<Direction>();
		allDirections.add(Direction.LEFT);
		allDirections.add(Direction.RIGHT);
		allDirections.add(Direction.DOWN);
		allDirections.add(Direction.UP);
		pq.add(world);
		
		int maxNodes = 100;
		
		boolean[][] visited = new boolean[world.getWidth()][world.getHeight()];
		
		visited[world.getMyX()][world.getMyY()] = true;
		
		Direction bestDirection = null;
		int bestHeuristic = heuristic(world); 
		int n = 0;
		while ((pq.peek() != null)) {
			n += 1;
			
			if (n > maxNodes) {
				return bestDirection;
			}
			
			WorldState curr = pq.poll();
			
			if (curr.hasMetGoal()) {
				return curr.getStartDirection();
			} else {
				int currHeuristic = heuristic(curr); 
				if (currHeuristic < bestHeuristic) {
					bestDirection = curr.getStartDirection();
					bestHeuristic = currHeuristic;
				}
			}
			for (Direction d : allDirections) {
				WorldState next = curr.transition(d, false);
				if (next != null) {
					if (!visited[next.getMyX()][next.getMyY()]) {
						visited[next.getMyX()][next.getMyY()] = true;
						pq.add(next);
					}
				}
			}
		}
		return null;
	}
	
}




