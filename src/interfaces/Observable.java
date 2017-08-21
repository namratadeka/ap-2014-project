package interfaces;


public interface Observable {
	public void addObserver(Observer observer, Class<?> eventClass);
	public void notifyAllObservers();
}
