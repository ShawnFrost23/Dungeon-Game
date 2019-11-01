package unsw.dungeon.back;

import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.back.event.Event;

public class CompositeGoal implements Goal {
	private interface CompositionRule {
		boolean isSatisfied(List<Goal> subgoals);
	}
	public static CompositionRule and = (List<Goal> subgoals) -> {
		return subgoals.stream().allMatch((Goal g) -> g.isSatisfied());
	};
	public static CompositionRule or = (List<Goal> subgoals) -> {
		return subgoals.stream().anyMatch((Goal g) -> g.isSatisfied());
	};

	private List<Goal> children;
	private CompositionRule compositionRule;
	
	public CompositeGoal(CompositionRule compositionRule) {
		this.compositionRule = compositionRule;
		this.children = new ArrayList<Goal>();
	}
	
	public void addChild(Goal g) {
		this.children.add(g);
	}
	

	// TODO: why is this getting no test coverage?
	@Override
	public void notifyOf(Event event) {
		for (Goal goal : this.children) {
			goal.notifyOf(event);
		}
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
