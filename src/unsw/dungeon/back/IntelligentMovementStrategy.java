package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class IntelligentMovementStrategy implements Enemy.MovementStrategy {

	private static Direction[] allDirections = {
		Direction.LEFT, Direction.RIGHT, Direction.DOWN, Direction.UP
	};
	
	private interface Heuristic {
		public int calculate(WorldState world);
	}
	
	public Direction chooseMove(WorldState world, Heuristic h) {
		PriorityQueue<WorldState> pq = new PriorityQueue<WorldState>(
			(WorldState a, WorldState b) -> Integer.compare(h.calculate(a) + a.getDepth(), h.calculate(b) + b.getDepth())
		);

		
		List<Direction> directionPreferenceList = Arrays.asList(allDirections);
		directionPreferenceList.sort(
			(Direction a, Direction b) -> {
				WorldState wa = world.transition(a, false);
				WorldState wb = world.transition(b, false);
				if (wa == null) {
					return -1;
				} else if (wb == null) {
					return 1;
				}
				Double L2a = wa.L2();
				Double L2b = wb.L2();
				return L2a.compareTo(L2b);
			}
		);
		pq.add(world);
		
		int maxNodes = 100;
		
		boolean[][] visited = new boolean[world.getWidth()][world.getHeight()];
		
		visited[world.getMyX()][world.getMyY()] = true;
		
		Direction bestDirection = null;
		int bestHeuristic = h.calculate(world); 
		int n = 0;
		while ((pq.peek() != null)) {
			n += 1;
			if (n > maxNodes) {
				return bestDirection;
			}
			
			WorldState curr = pq.poll();
			
			int currHeuristic = h.calculate(curr);
			
			if (currHeuristic == 0) {
				return curr.getStartDirection();
			} else {
				if (currHeuristic < bestHeuristic) {
					bestDirection = curr.getStartDirection();
					bestHeuristic = currHeuristic;
				}
			}
			
			for (Direction d : directionPreferenceList) {
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
	
	@Override
	public Direction chooseMove(WorldState world, boolean seek) {
		Direction chosenDirection = this.chooseMove(world,
			(WorldState w) ->  w.L1()
		);
		if (seek) {
			return chosenDirection; 
		} else {
			if (chosenDirection == null ) {
				return null;
			}
			Direction bestDirection = null;
			double bestL2 = world.L2();
			
			for (Direction d : allDirections) {
				WorldState next = world.transition(d, false); 
				if (next != null && next.L2() > bestL2) {
					bestL2 = world.L2();
					bestDirection = d;
				}
				
			}
			return bestDirection;
		}
	}
}
