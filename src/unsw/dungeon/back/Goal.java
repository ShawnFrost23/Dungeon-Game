package unsw.dungeon.back;

import org.json.JSONArray;
import org.json.JSONObject;

import unsw.dungeon.back.event.Observer;

/**
 * Objects that extend this interface can be provided to
 * {@link Game#createMockGame(Goal, String...)}. If the Goal at root level becomes
 * satisfied, the game is won.
 */
public interface Goal {
	/**
	 * Returns true if this goal is satisfied, false otherwise.
	 * @return true if this goal is satisfied
	 */
	boolean isSatisfied();
	
	/**
	 * This function is called for every Entity that is added to a Board during
	 * its creation. Implementors of this interface should selectively listen to
	 * Entities that produce events relevant to the satisfaction of the goal.
	 * @param e entity to track
	 */
	void trackEntity(Entity e);
	
	/**
	 * A Goal factory.
	 * @param jsonGoal json representation of the goal to create.
	 * @return a Goal object of type and structure appropriate for the argument.
	 */
	public static Goal createGoal(JSONObject jsonGoal) {
		String type = jsonGoal.getString("goal");
		if (type.equals("exit")) {
			return new MazeGoal();
		} else if (type.equals("enemies")) {
			return new EnemiesGoal();
		} else if (type.equals("boulders")) {
			return new PuzzleGoal();
		} else if (type.equals("treasure")) {
			return new TreasureGoal();
		} else if (type.equals("AND") || type.equals("OR")) {
			CompositeGoal goal = null;
			if (type.equals("AND")) {
				goal = new CompositeGoal(CompositeGoal.and);
			} else if (type.equals("OR")) {
				goal = new CompositeGoal(CompositeGoal.or);
			}
			JSONArray jsonSubGoals = jsonGoal.getJSONArray("subgoals");
			
			for (int i = 0; i < jsonSubGoals.length(); ++i) {
				Goal subGoal = Goal.createGoal(jsonSubGoals.getJSONObject(i));
				goal.addChild(subGoal);
			}
			
			return goal;
		} else {
			throw new Error("Unrecognised goal type \"" + type + "\".");
		}
	}
}
