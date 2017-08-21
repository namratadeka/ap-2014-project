package entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Panel panel;
	private TeamA teamA;
	private TeamB teamB;
	private Field field;
	private TeamComputer computer;
	private Thread t1,t2, t3;
	private static JLabel score1;
	private static JLabel score2;
	
	public MainFrame(String s)
	{
		setTitle(s);
		setSize(1000,670);
		setLocation(100,100);
		
		panel = new Panel();
		panel.setFocusable(true);
		panel.requestFocus();		
		add(panel);
		
		score1 = new JLabel("Team Red :"+" "+0);
		score2 = new JLabel("Team Blue :"+" " +0);
		
		teamA = new TeamA("TeamA", "heads", panel.ball);
		//teamB = new TeamB("TeamB","tails", panel.ball);
		computer = new TeamComputer("tails", panel.ball);
		teamA.setFormation(3,4,3); 
		//teamB.setFormation(3,4,3);
		computer.setFormation(3, 4, 3);
		computer.startPlaying();
		
		field = new Field(teamA,computer,panel.ball);
		field.startMatch(panel.ball);
				
		t1 = new Thread(teamA);
		t2 = new Thread(teamB);
		t3 = new Thread(computer);
		t1.start();
		//t2.start();
		t3.start();
		
		panel.add(score1,BorderLayout.NORTH);
		panel.add(score2,BorderLayout.NORTH);
	}
	public static void setScore(int scoreA, int scoreB)
	{
		score1.setText("Team Red :"+" "+scoreA);
		score2.setText("Team Blue :"+" " +scoreB);
	}
	
	public static void main(String[] args)
	{
		MainFrame frame = new MainFrame("Foosball");
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	class Panel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Ball ball;
		BufferedImage img ;
		public Panel(){
			ball  = Ball.getInstance();
			try
			{
				
				img = ImageIO.read(new File("Resources/Foosball.png"));				
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			ball.start();
		}
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(img, 0, 0, 1000, 670, this);
			Graphics2D g2d = (Graphics2D)g;
			g2d.setStroke(new BasicStroke(5));
			ball.draw(g2d);
			for(int i=0 ;i <4 ;i++)
			{
				teamA.line[i].drawLine(g2d); 
				//teamB.line[i].drawLine(g2d);
				computer.line[i].drawLine(g2d);
			} 
			teamA.drawPlayer(g);
			//teamB.drawPlayer(g);
			computer.drawPlayer(g);
			Color color = new Color(255,255,255);
			g2d.setColor(color);
			g2d.drawLine(5, 255, 5, 420);
			g2d.drawLine(995, 255, 995, 420);
		}
	}
}


