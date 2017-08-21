package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class TeamA extends UserDefinedTeam implements Runnable
{
	BufferedImage img;
	CoOrdinates s0,s1,s2,s3;
	CoOrdinates e0,e1,e2,e3;
	public TeamA(String name, String coin, Ball ball) 
	{
		super(name, coin, ball);
		try
		{
			img = ImageIO.read(new File("Resources/RedPlayer.png"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setFormation(int l1, int l2, int l3)
	{
		s0 = new CoOrdinates(75,0);
		s1 = new CoOrdinates(75+121.4*2,0);
		s2 = new CoOrdinates(75+121.4*4,0);
		s3 = new CoOrdinates(75+121.4*6,0);
		e0 = new CoOrdinates(75,670);
		e1 = new CoOrdinates(75+121.4*2,670);
		e2 = new CoOrdinates(75+121.4*4,670);
		e3 = new CoOrdinates(75+121.4*6,670);
			
		line[0] = new Line(0,1, s0, e0, this);  //Goalkeeper
		line[1] = new Line(1,l1, s1, e1, this); //Defender
		line[2] = new Line(2,l2, s2, e2, this); //Midfielder
		line[3] = new Line(3,l3, s3, e3, this); //Attacker			
		
		for(int i=0 ;i<4 ; i ++)
		{
			line[i].addPlayers(img);
		}
	}
	
	public void drawPlayer(Graphics g)
	{
		if(this!=null)
		{
			for(int i = 0 ; i<4 ; i++)
			{
				for(int j= 0; j<line[i].players.size();j++)
				{
				g.drawImage(img,(int)line[i].players.get(j).position.x - 10,(int)line[i].players.get(j).position.y - 20,MainFrame.panel);
				}
			}
			
		}
	}

	@Override
	public void run() {
		 
		// TODO Auto-generated method stub
		MainFrame.panel.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int key = e.getKeyCode();

			    if (key == KeyEvent.VK_UP) {
			        moveUp();
			        MainFrame.panel.repaint();
			    }

			    if (key == KeyEvent.VK_DOWN) {
			        moveDown();
			        MainFrame.panel.repaint();
			    }
			    
			}
			

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}
			
		});
		
		
	}
}
