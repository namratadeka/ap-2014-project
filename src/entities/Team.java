package entities;
import java.awt.Graphics;

import javax.swing.JComponent;

import interfaces.*;

public abstract class Team extends JComponent implements Formation
{
	String name;
	Line[] line = new Line[4];
	int score;
	String coinFace;
	Ball ball;
	
	public Team(String name, String coin, Ball ball)
	{
		this.name = name;
		this.coinFace =coin;
		this.score = 0;
		this.ball = ball;
	}
	
	public  abstract void drawPlayer(Graphics g);
	
	public void reInitialize(){
		for(int i = 0; i<4; i++){
			for(int j = 0; j<line[i].players.size(); j++){
				line[i].players.get(j).position.y = line[i].start.y+((line[i].end.y-line[i].start.y)/(line[i].noOfPlayers+1))*(j+1);
				line[i].players.get(j).position.x = line[i].start.x;
			}
		}
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		MainFrame.panel.repaint();
	}
}
