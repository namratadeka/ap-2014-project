package entities;
import interfaces.*;
import events.*;
import events.Event;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Ball extends Thread implements Observable
{
	private Ellipse2D.Double ball;
	CoOrdinates position;
	Player lastPlayer; 
	private int xspeed;
	private int yspeed;
	private int resultspeed;
	double dx, dy, size, oldx, oldy, newx, newy; //smaller dx and dy 
	private Color color;
	private boolean isMoving;
	Event newEvent;
	Map<Class,List<Observer>> eventMap = new HashMap<Class, List<Observer>>();
	private static Ball instance = new Ball();
	
	
	private Ball()
	{
		this.isMoving = true;
		size= 15;
		this.xspeed = (int) (100000 + (Math.random()*60));
		this.yspeed = (int) (100000+ (Math.random()*60));
		this.resultspeed = (int) Math.sqrt(xspeed*xspeed + yspeed*yspeed);
		this.position = new CoOrdinates(100,100);
		dx = 100;
		dy = 100;
		ball = new Ellipse2D.Double(position.x,position.y, size, size);
		color = new Color(255,255,255);
		
	}
	private boolean goalOccured(){
		if(position.x <= 20 || position.x >= 970){
			if(position.y >=225 && position.y<=440){
				System.out.println(position.x + " " + position.y);
				return true;
			}
				return false;
		}
		return false;
	}
	public void reInitialize(){
		position.x = 500;
		position.y = 310;
		MainFrame.panel.repaint();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void run()
	{
		while(isMoving)
		{
			try
			{
				Thread.sleep(5);		
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			oldx = ball.getX();
			oldy = ball.getY();
			if(dx==0)
				dx = 100;
			newx = oldx + dx;
			
			if(newx+size>MainFrame.panel.getWidth() || newx<0)
				dx = -dx;
			if(dy==0)
				dy = 50;
			newy = oldy + dy ;
			if(newy+size>MainFrame.panel.getHeight() || newy<0)
				dy = -dy;
			position.x = newx;
			position.y = newy;
			if(goalOccured()){
				newEvent = new GoalEvent(this);
				notifyAllObservers();
			}
			ball.setFrame(position.x,position.y , size, size); 
			MainFrame.panel.repaint();
			
		}
	}
	public void deflect(CoOrdinates target){
		double beta = Math.atan((target.y - ball.getY())/(target.x - ball.getX()));
		dx = 2*Math.cos(beta);
		if(dx==0.0)
			dx = 10;
		dy = 2*Math.sin(beta);
		if(dy==0.0)
			dy = 10;
	}
	public static Ball getInstance()
	{
		return instance;		
	}
	
	public void draw(Graphics2D g2d)
	{
		if(ball!=null)
		{
			g2d.setColor(color);
			g2d.fill(ball);
		}
	}
	@Override
	public void addObserver(Observer observer, Class<?> eventClass) {
		// TODO Auto-generated method stub
		List<Observer> list = eventMap.get(eventClass);
		if(list==null){
			list = new ArrayList<Observer>();
			eventMap.put(eventClass, list);
		}
		list.add(observer);
	}
	@Override
	public void notifyAllObservers() {
		// TODO Auto-generated method stub
		List<Observer> list = eventMap.get(newEvent.getClass());
		if(list!=null){
			for(Observer observer : list){
				observer.handleEvent(newEvent);
			}
		}
	}	
}
