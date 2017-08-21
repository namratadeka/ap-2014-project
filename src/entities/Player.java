package entities;
import events.*;
import interfaces.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import events.Event;


public class Player extends Thread implements  Observer, GoalKeeper, Defender, MidFielder, Attacker
{
	private String name;
	Ball ball;
	private Team team;
	CoOrdinates position;
	private Line line;
	private double minKickSpeed;
	private double maxKickSpeed;
	private BufferedImage playerImage;
	
	public void setPosition(double x, double y){
		position.setX(x);
		position.setY(y);
	}
	
	
	
	public Player(String name,CoOrdinates position ,Line line, Team team, BufferedImage img)
	{
		this.playerImage = img;
		this.name = name;
		this.position = new CoOrdinates(position.x,position.y);
		this.line = line;
		this.minKickSpeed =(int)Math.random()*40+1;
		this.maxKickSpeed = (int)Math.random()*40+50;
		this.team = team;
		this.ball = Ball.getInstance();
		
	}

	
	
	
	public void kickBall(Ball ball) {
		CoOrdinates p =null;
		p = searchTeamMate();
		ball.deflect(p);
	}
	
	public CoOrdinates searchTeamMate(){
		CoOrdinates p = null;
		Random random = new Random();

		if(this instanceof GoalKeeper){
			p = passToDefender();
		}
		else if(this instanceof Defender){
			p = passToMidFielder();
		}
		else if(this instanceof MidFielder){
			if(random.nextInt(2)==0){
				p = passToAttacker();
			}
			else
				p = shootGoal();
		}
		else if(this instanceof Attacker){

			p = goal();

		}
		
		return p;
	}


	@Override
	public void startObserving(Observable observable) {
		// TODO Auto-generated method stub
		observable.addObserver(this, CollisionEvent.class);
		
	}




	public void handleCollisionEvent(CollisionEvent event) {
		// TODO Auto-generated method stub
		kickBall(ball);
		
	}
	public void handlePositionChangeEvent(PositionChangeEvent event){
		
	}
	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		if(event instanceof CollisionEvent){
			handleCollisionEvent((CollisionEvent)event);
		}
	}

	public void startPlaying(){
		startObserving(ball);
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(true){
			try
			{
				Thread.sleep(1);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			if(checkForCollision()){
				ball.notifyAllObservers();
				
			}
		}
		
	}
	
	

	boolean checkForCollision(){
		if((ball.position.x >= this.position.x -3) && (ball.position.x <= (this.position.x + this.playerImage.getWidth()) + 3) && (ball.position.y >= this.position.y -3) && (ball.position.y<= (this.position.y + this.playerImage.getHeight() + 3))){
				ball.lastPlayer = this;
				ball.newEvent = new CollisionEvent(ball);
				return true;
			
		}
		return false;
	}



	@Override
	public CoOrdinates passToAttacker() {
		// TODO Auto-generated method stub
		Random random = new Random();
		int n = random.nextInt(this.team.line[3].noOfPlayers);
		return this.team.line[3].players.get(n).position;

	}



	@Override
	public CoOrdinates shootGoal() {
		// TODO Auto-generated method stub
		CoOrdinates p = null;
		Random random = new Random();
		double y = 255 + random.nextInt(165);
		if(this.team.name.equals("TeamA")){
			p = new CoOrdinates(1000,y);
			return p;
		}
		else if(this.team.name.equals("TeamB")){
			p = new CoOrdinates(0,y);
			return p;
		}
		return p;
			
	}



	@Override
	public CoOrdinates passToMidFielder() {
		// TODO Auto-generated method stub

		Random random = new Random();
		int n = random.nextInt(this.team.line[2].noOfPlayers);
		return this.team.line[2].players.get(n).position;

	}



	@Override
	public CoOrdinates passToDefender() {
		// TODO Auto-generated method stub

		Random random = new Random();
		int n = random.nextInt(team.line[1].noOfPlayers);
		return team.line[1].players.get(n).position;
	}



	@Override
	public CoOrdinates goal() {
		// TODO Auto-generated method stub
		CoOrdinates p = null;
		Random random = new Random();
		double y = 255 + random.nextInt(165);
		if(team.name.equals("TeamA")){
			p = new CoOrdinates(1000,y);
			return p;
		}
		else if(team.name.equals("TeamB")){
			p = new CoOrdinates(0.0,y);
			return p;
		}
		return p;
		
	}
}
