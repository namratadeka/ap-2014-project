package entities;
import interfaces.*;
import events.*;
public class Field implements Observer{

	TeamA teamA;
	Team team;
	Ball ball;
	
	public Field(TeamA teamA, Team team, Ball ball){
		this.teamA = teamA;
		this.team = team;
		this.ball = ball;
		MainFrame.setScore(teamA.score, team.score);
	}
	
	public void startMatch(Ball ball){
		startObserving(ball);
	}
	@Override
	public void startObserving(Observable observable) {
		observable.addObserver((Observer) this, GoalEvent.class);
	}

	@Override
	public void handleEvent(Event event) {
		if(event instanceof GoalEvent){
			updateScore((GoalEvent)event);
		}
	}
	
	public void updateScore(GoalEvent event){
		if(ball.position.x<=20){
			team.score++;
			System.out.println(team.name + " " + team.score);
			MainFrame.setScore(teamA.score, team.score);
			replace();
			return;
		}
		else if(ball.position.x>=980){
			teamA.score++;
			System.out.println(teamA.name + " " + teamA.score);
			MainFrame.setScore(teamA.score, team.score);
			replace();
			return;
		}
	}
	public void replace(){
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ball.reInitialize();
		teamA.reInitialize();
		team.reInitialize();
	}
}
