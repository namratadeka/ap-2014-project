package entities;
import interfaces.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import events.Event;
import events.PositionChangeEvent;

public class TeamComputer extends Team implements Observer,Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage img;
	public TeamComputer(String coin, Ball ball)
	{
		super("Computer", coin, ball);
		try
		{
			img = ImageIO.read(new File("Resources/BluePlayer.png"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public void setFormation(int l1, int l2, int l3)
	{
		CoOrdinates s0 = new CoOrdinates(925,0);
		CoOrdinates s1 = new CoOrdinates(75+121.4*5,0);
		CoOrdinates s2 = new CoOrdinates(75+121.4*3,0);
		CoOrdinates s3 = new CoOrdinates(75+121.4*1,0);
		CoOrdinates e0 = new CoOrdinates(925,627);
		CoOrdinates e1 = new CoOrdinates(75+121.4*5,627);
		CoOrdinates e2 = new CoOrdinates(75+121.4*3,627);
		CoOrdinates e3 = new CoOrdinates(75+121.4*1,627);
		
		line[0] = new Line(0,1, s0, e0, this);  //Goalkeeper
		line[1] = new Line(1,l1, s1, e1, this); //Defender
		line[2] = new Line(2,l2, s2, e2, this); //Midfielder
		line[3] = new Line(3,l3, s3, e3, this); //Attacker	
		
		for(int i=0 ;i<4 ; i ++)
		{
			line[i].addPlayers(img);
		}
	}
	@Override
	public void drawPlayer(Graphics g)
	{
		if(this!=null)
		{
			
			for(int i = 0 ; i<4 ; i++)
			{
				for(int j= 0; j<line[i].players.size();j++)
				{
					g.drawImage(img,(int)line[i].players.get(j).position.x -10,(int)line[i].players.get(j).position.y -20,MainFrame.panel);
				}
			}
			
		}
	}

	public void moveUp() {
		// TODO Auto-generated method stub
		
		for(int i=0; i<4; i++){
			for(int j=0; j<line[i].players.size();j++){
				line[i].players.get(j).position.y -= 10;
			}
		}
	}

	public void moveDown() {
		// TODO Auto-generated method stub
		for(int i=0; i<4; i++){
			for(int j=0; j<line[i].players.size();j++){
				line[i].players.get(j).position.y += 10;
			}
		}
	}
	@Override
	public void startObserving(Observable observable) {
		// TODO Auto-generated method stub
		observable.addObserver((Observer) this, PositionChangeEvent.class);
	}
	
	public void handlePositionChangeEvent(PositionChangeEvent newEvent) {
		// TODO Auto-generated method stub
		if(ball.newy < ball.oldy){
				moveUp();
				MainFrame.panel.repaint();
		}
		else if(ball.newy > ball.oldy){
			
				moveDown();
				MainFrame.panel.repaint();
			
		}
		
	}
	
	public void startPlaying(){
		startObserving(ball);
	}

	public boolean checkForPositionChange(){
		if(ball.newy != ball.oldy){
			ball.newEvent = new PositionChangeEvent(ball);
			return true;
		}
		return false;
	}
	@Override
	public void run() {
		while(true){
			try{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			if(checkForPositionChange()){
				ball.notifyAllObservers();
			}
		}
		
	}
	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		if(event instanceof PositionChangeEvent){
			handlePositionChangeEvent((PositionChangeEvent)event);
		}
	}
}
