package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class UserDefinedTeam extends Team
{

	public UserDefinedTeam(String name, String coin, Ball ball)
	{
		super(name, coin, ball);
	}
	
	public void moveUp() {
			
		for(int i=0; i<4; i++){
			for(int j=0; j<line[i].players.size();j++){
				line[i].players.get(j).position.y -= 10;
			}
		}
	}

	public void moveDown() {
		
		for(int i=0; i<4; i++){
			for(int j=0; j<line[i].players.size();j++){
				line[i].players.get(j).position.y += 10;
			}
		}
	}

}
