package interfaces;
//import entities.CollisionEvent;
import events.*;


public interface Observer {
	public void startObserving(Observable observable);
	public void handleEvent(Event event);
	//public void handleCollisionEvent(CollisionEvent newEvent);
}
