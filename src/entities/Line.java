package entities;
import interfaces.*;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.*;

public class Line 
{
	private double verticalSpeed;
	int lineNo;
	int noOfPlayers;
	public ArrayList<Player> players;
	CoOrdinates start;
	CoOrdinates end;
	private Line2D.Double line; 
	private Team team;
	
	public Line(int no,int noOfPlayers, CoOrdinates starting ,CoOrdinates ending, Team team)
	{
		this.lineNo = no;
		this.noOfPlayers = noOfPlayers;
		players= new ArrayList<Player>(noOfPlayers);
		this.start = new CoOrdinates(starting.x, starting.y);
		this.end = new CoOrdinates(ending.x, ending.y);
		line = new Line2D.Double(start.x, start.y, end.x, end.y);
		this.team = team;
	}
	
	public void drawLine(Graphics2D g2d)
	{
		if(line!=null)
		{
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.draw(line);
		}
	}
	
	
	public void addPlayers(BufferedImage img)
	{
		for(int i =0 ;i <noOfPlayers ; i++)
		{
			double ordinate;
			ordinate = this.start.y+((this.end.y-this.start.y)/(noOfPlayers+1))*(i+1);
			CoOrdinates position = new CoOrdinates(this.start.x,ordinate);
			if(this.lineNo==0){
				GoalKeeper player = new Player(team.name+"line"+lineNo+"player"+i,position,this, team, img);
				players.add((Player)player);
				((Player)player).startPlaying();
				((Player)player).start();
			}
			else if(this.lineNo==1){
				Defender player = new Player(team.name+"line"+lineNo+"player"+i,position,this, team, img);
				players.add((Player)player);
				((Player)player).startPlaying();
				((Player)player).start();
			}
			else if(this.lineNo==2){
				MidFielder player = new Player(team.name+"line"+lineNo+"player"+i,position,this, team, img);
				players.add((Player)player);
				((Player)player).startPlaying();
				((Player)player).start();
			}
			else if(this.lineNo==3){
				Attacker player = new Player(team.name+"line"+lineNo+"player"+i,position,this, team, img);
				players.add((Player)player);
				((Player)player).startPlaying();
				((Player)player).start();
			}
		}
	}
}
