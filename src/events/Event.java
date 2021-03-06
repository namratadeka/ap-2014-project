package events;
import interfaces.*;
public abstract class Event {
	private Observable source;
	
	public Event(Observable source){
		this.source = source;
	}
	
	public Observable getSource(){
		return source;
	}
}
