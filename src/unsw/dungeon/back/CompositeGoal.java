package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.back.event.Event;

/**
 * A type of {@link Goal} that uses a {@link CompositionRule} so as to only
 * become "satisfied" when some composition of its children are true.
 */
public class CompositeGoal implements Goal {
	/**
	 * Classes that implement this interface can be provided as a composition
	 * rule to a new {@link CompositeGoal} object.
	 */
	public interface CompositionRule {
		/**
		 * This function chooses whether this goal should be satisfied given
		 * the states of satisfaction for a list of subgoals. 
		 * @param subgoals list of subgoals
		 * @return true if the chosen composite condition is true on the
		 * subgoals
		 */
		boolean isSatisfied(List<Goal> subgoals);
	}
	
	/**
	 * {@link CompositionRule} that is true if all subgoals are true.
	 */
	public static CompositionRule and = (List<Goal> subgoals) -> {
		return subgoals.stream().allMatch((Goal g) -> g.isSatisfied());
	};

	/**
	 * {@link CompositionRule} that is true if any subgoals are true.
	 */
	public static CompositionRule or = (List<Goal> subgoals) -> {
		return subgoals.stream().anyMatch((Goal g) -> g.isSatisfied());
	};

	private List<Goal> children;
	private CompositionRule compositionRule;
	
	/**
	 * Construct a new CompositeGoal object with a given rule.
	 * @param compositionRule the composition rule to use
	 */
	public CompositeGoal(CompositionRule compositionRule) {
		this.compositionRule = compositionRule;
		this.children = new ArrayList<Goal>();
	}
	
	public void addChild(Goal g) {
		this.children.add(g);
	}

	@Override
	public boolean isSatisfied() {
		 return this.compositionRule.isSatisfied(this.children);
	}

	@Override
	public void trackEntity(Entity e) {
		for (Goal goal : this.children) {
			goal.trackEntity(e);
		}
	}
	
}
