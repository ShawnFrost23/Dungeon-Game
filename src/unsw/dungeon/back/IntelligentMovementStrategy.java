package unsw.dungeon.back;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * An enemy movement strategy whereby Enemies use A* search up to
 * <b>maxNodes</b> many nodes of effort in order to choose a move. If this depth
 * is sufficient that they realise reaching the player is
 * impossible, they stay still.
 * 
 * <br />
 * <br />
 * 
 * If the a move is requested with the <b>seek</b> set to false, they will
 * greedily run away from the player so long as they feel threatened -- so long
 * as their usual A* search returns that there is a possible move they could
 * make to bring them closer to the player. 
 */
public class IntelligentMovementStrategy implements Enemy.MovementStrategy {
	private boolean canUsePortals = false;
	private int maxNodes = 100;
	private static Direction[] allDirections = {
		Direction.LEFT, Direction.RIGHT, Direction.DOWN, Direction.UP
	};
	
	/**
	 * Get a list of the directions {left, right, down, up} sorted increasingly
	 * in the L2 distance between the player and enemy they would result in.
	 * @param world WorldState object to check distance within
	 * @return sorted list of directions in order of L2 distance they would
	 * result in.
	 */
	private List<Direction> DirectionsL2Order(WorldState world) {
		List<Direction> orderedDirections = Arrays.asList(allDirections);
		orderedDirections.sort(
			(Direction a, Direction b) -> {
				WorldState wa = world.transition(a, this.canUsePortals);
				WorldState wb = world.transition(b, this.canUsePortals);
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
		return orderedDirections;
	}
	
	/**
	 * Classes that implement this interface can be used as heuristics for the
	 * {@link IntelligentMovementStrategy#chooseSeekMove(WorldState, Heuristic)
	 * chooseSeekMove(WorldState, Heuristic)} function.
	 */
	private interface Heuristic {
		public int calculate(WorldState world);
	}
	
	/**
	 * Node-limited A* search to bring the enemy to a state with a better
	 * heuristic.
	 * @param world WorldState to perform search on
	 * @param h heuristic to use
	 * @return best found move (<code>can be null</code>)
	 */
	private Direction chooseSeekMove(WorldState world, Heuristic h) {
		List<Direction> directionPreferenceList = this.DirectionsL2Order(world);
		
		boolean[][] visited = new boolean[world.getWidth()][world.getHeight()];
		visited[world.getMyX()][world.getMyY()] = true;
		
		PriorityQueue<WorldState> pq = new PriorityQueue<WorldState>(
			(WorldState a, WorldState b) -> Integer.compare(h.calculate(a) + a.getDepth(), h.calculate(b) + b.getDepth())
		);
		pq.add(world);

		Direction bestDirection = null;
		int bestHeuristic = h.calculate(world); 
		int numNodesExplored = 0;
		while ((pq.peek() != null)) {
			
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

			numNodesExplored += 1;
			if (numNodesExplored > this.maxNodes) {
				return bestDirection;
			}
			
			for (Direction d : directionPreferenceList) {
				WorldState next = curr.transition(d, this.canUsePortals);
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
	
	/**
	 * Choose a move that will immediately increase the L1 distance between us
	 * and the player, breaking ties preferencing those which maximise L2
	 * distance.
	 * @param world WorldState to choose with
	 * @return best found move (<code>can be null</code>)
	 */
	private Direction chooseAvoidMove(WorldState world) {
		List<Direction> directionPreferenceList = this.DirectionsL2Order(world);
		Collections.reverse(directionPreferenceList);
		
		Direction bestDirection = null;
		int bestL1 = world.L1();

		for (Direction d : directionPreferenceList) {
			WorldState next = world.transition(d, this.canUsePortals); 
			if (next != null && next.L1() > bestL1) {
				bestL1 = world.L1();
				bestDirection = d;
			}
			
		}
		return bestDirection;
	}
	
	@Override
	public Direction chooseMove(WorldState world, boolean seek) {
		Direction chosenDirection = this.chooseSeekMove(world,
			(WorldState w) ->  w.L1()
		);
		if (seek) {
			return chosenDirection; 
		} else {
			if (chosenDirection == null ) {
				return null;
			}
			return this.chooseAvoidMove(world);
		}
	}
}
