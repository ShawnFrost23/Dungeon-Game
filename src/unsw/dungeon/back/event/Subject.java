package unsw.dungeon.back.event;

public interface Subject {
	public void attachListener(Observer observer);
	public void detachListener(Observer observer);
	public void notifyAllOf(Event event);
}
